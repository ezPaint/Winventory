package com.simoncomputing.app.winventory.controller.user;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.authentication.EmailService;
import com.simoncomputing.app.winventory.authentication.PasswordHasher;
import com.simoncomputing.app.winventory.bo.AccessTokenBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.AccessToken;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.domain.Role;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;
import com.simoncomputing.app.winventory.controller.BaseController;

/**
 * Servlet to handle the add user functionality, or insert user page.
 * 
 */


@WebServlet("/users/insert")
public class UserInsertController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(UserInsertController.class);


    protected void doGet( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
        // if the user is rejected, the redirect is sent in the require permission method.
        if (this.requirePermission(request, response, "createUser")) {
            return;
        }
        
        
        // the preset role ids to display in dropdown
        ArrayList<Role> roles = new ArrayList<Role>();
        try {
            roles = (ArrayList<Role>) RoleBo.getInstance().getAll();
        } catch (BoException e) {
            logger.error("BoException in UserInsertController when trying to get roles");
        }
        
        if (roles != null) {
            request.setAttribute("roles", roles);
        }

        // forward to jsp
        request.getRequestDispatcher("/WEB-INF/flows/users/insert.jsp").forward(request, response);  
    }


    protected void doPost( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
        // check for the create user permission
        if (this.requirePermission(request, response, "createUser")) {
            return;
        }
        

        // the new user to be added
        User user = new User();
        
        // bind the form values to the user
        user.setUsername(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));
        // Google User
        if (request.getParameter("isGoogle") != null) {
            user.setPassword("googleUser");
            logger.info("Sending Email to: " + user.getEmail());
            this.sendInviteGoogle(request, user);
        }
        // Login User
        else {
            user.setPassword("UnsetPassword");
        }
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setCellPhone(request.getParameter("cellPhone"));
        user.setWorkPhone(request.getParameter("workPhone"));
        user.setIsActive(request.getParameter("isActive") != null);
        
        // the preset roles
        ArrayList<Role> roles = new ArrayList<Role>();
        try {
            roles = (ArrayList<Role>) RoleBo.getInstance().getAll();
        } catch (BoException e) {
            logger.error("BoException in UserInsertController when trying to get roles");
        }
        
        // find the role id for the specified role title
        String roleTitle = request.getParameter("roleTitle");
        for (Role r : roles) {
            if (r.getTitle().equals(roleTitle)) {
                user.setRoleId( r.getKey().intValue() );
            }
        }
        
        // Create the new user in the db
        UserBo bo = UserBo.getInstance();
        try {
            bo.create(user);
        } catch (BoException e) { 
            logger.error("BoException when inserting user in UserInsertController");
        }
        
        // Send email to non-Google users to change their password
        if (request.getParameter("isGoogle") == null) {
            this.sendInviteRegular(request, user);
        }
        
        // redirect to results 
        response.sendRedirect(request.getContextPath() + "/users/results");
    }
    
    private void sendInviteGoogle(HttpServletRequest request, User user) {
    	EmailService emailer = new EmailService();
    	try {
    		emailer.setSmtp();
			emailer.addTo(user.getEmail());
			emailer.setFrom(this.getUserInfo(request).getEmail());
			emailer.setSubject("Welcome To Winventory");
			emailer.setMessage(
					"You have been added to the Winventory!\n"
					+ "Your Username is: " + user.getUsername());
			emailer.sendEmail();
		} catch (Exception e) {
			logger.info("Email Failed to Send to: " + user.getEmail());
		}
    	
    }
    
    private void sendInviteRegular(HttpServletRequest request, User user) {
    	UUID uuid = UUID.randomUUID();
    	String token = uuid.toString().replaceAll("-", "");
    	
    	//should be changed to https
        String urlPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String message = "You have been added to the Winventory! \n"
        		+ "Your Username is: " + user.getUsername() + "\n"
        		+ "Please reset your password at: "
        		+ urlPath + "/changepassword" + "?token=" + token + "&user=" + user.getKey().intValue();
          
        //insert into database hashed version of token
		AccessTokenBo accessTokenBo = AccessTokenBo.getInstance();
		AccessToken accessToken = new AccessToken();
		
		//TODO: change access token domain object to match type
		accessToken.setUserKey(user.getKey().intValue());
		accessToken.setToken(PasswordHasher.encodePassword(token));
		
		//set in how many minutes the token will expire
		int minutesToExpire = 30;
		Date date = new Date(System.currentTimeMillis()+minutesToExpire*60*1000);
		accessToken.setExpiration(date);
		
		try {
			//check if an access token for the user currently exists
			AccessToken readToken = accessTokenBo.read(user.getKey().intValue());
			if (readToken != null) {
				// User already has a token in the AccessToken table
				// delete the old entry in preparation for the new entry
				accessTokenBo.delete(readToken.getUserKey());
			}
			
		} catch (BoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Insert the new access token and email the user a link
		// with the token embedded in the url
		try {
			accessTokenBo.create(accessToken);
			this.sendPasswordResetEmail(user.getEmail(), message);
			
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    private void sendPasswordResetEmail(String toEmailAddress, String message) {
		EmailService sendResetEmail = new EmailService();
		try {
			sendResetEmail.setSmtp();
			/* setFrom does not actually matter since it uses the email 
			 * stored in the smtp table
			 */
			sendResetEmail.setFrom("-@gmail.com");
			//currently send here for testing purposes
			sendResetEmail.addTo(toEmailAddress);
			sendResetEmail.setSubject("Your Winventory account has been created");
			sendResetEmail.setMessage(message);
			sendResetEmail.sendEmail();
		} catch (EmailException | BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
