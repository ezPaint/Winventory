package com.simoncomputing.app.winventory.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.authentication.EmailService;
import com.simoncomputing.app.winventory.authentication.PasswordHasher;
import com.simoncomputing.app.winventory.bo.AccessTokenBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.AccessToken;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

import java.util.Date;
import java.util.UUID;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/resetpassword")
public class ResetPasswordController extends BaseController {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ResetPasswordController.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPasswordController() {
    	super(); 
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//forward the user to the reset password page
    	request.getRequestDispatcher("/WEB-INF/flows/authentication/resetPassword.jsp").forward(request,
                  response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//email which to send the reset password link to
    	String email = request.getParameter("email");
    	
    	UserBo userBo = UserBo.getInstance();
		User resetUser = userBo.getUserByEmail(email);
		
		//user does not exist
		if (resetUser == null || resetUser.getPassword().equals("googleUser")) {
			//user does not have an account or is a Google user
			request.getSession().setAttribute("changePasswordError", "Cannot reset password for this email address");
			request.getRequestDispatcher("/WEB-INF/flows/authentication/resetPassword.jsp").forward(request,
			        response);
		}
		//user was found
		else {

			//use UUID to generate a random token that is essentially 
			//guaranteed to be free from collisions
	    	UUID uuid = UUID.randomUUID();
	    	String token = uuid.toString().replaceAll("-", "");
	    	
	    	//should be changed to https
	        String urlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	        String message = "Please reset your password at: " +
	        		urlPath + "/changepassword" + "?token=" + token + "&user=" + resetUser.getKey().intValue();
	          
	        //insert into database hashed version of token
			AccessTokenBo accessTokenBo = AccessTokenBo.getInstance();
			AccessToken accessToken = new AccessToken();
			
			//TODO: change access token domain object to match type
			accessToken.setUserKey(resetUser.getKey().intValue());
			//store the hashed version of the token into the table
			accessToken.setToken(PasswordHasher.encodePassword(token));
			
			//set in how many minutes the token will expire
			int minutesToExpire = 30;
			Date date = new Date(System.currentTimeMillis()+minutesToExpire*60*1000);
			accessToken.setExpiration(date);
			
			try {
				//check if an access token for the user currently exists
				AccessToken readToken = accessTokenBo.read(resetUser.getKey().intValue());
				if (readToken != null) {
					// User already has a token in the AccessToken table
					// delete the old entry in preparation for the new entry
					accessTokenBo.delete(readToken.getUserKey());
				}
				
			} catch (BoException e1) {
				 logger.error("BoException in ResetPasswordController when checking if user already requested access token");
			}
			
			// Insert the new access token and email the user a link
			// with the token embedded in the url
			try {
				accessTokenBo.create(accessToken);
				this.sendPasswordResetEmail(resetUser.getEmail(), message);
				
			} catch (BoException e) {
				 logger.error("BoException in ResetPasswordController when creating a new access token");
			}
			request.getSession().removeAttribute("changePasswordError");
			request.getRequestDispatcher("/WEB-INF/flows/authentication/login.jsp").forward(request,
			        response);
			   
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
			sendResetEmail.addTo(toEmailAddress);
			sendResetEmail.setSubject("Winventory Password Reset");
			sendResetEmail.setMessage(message);
			sendResetEmail.sendEmail();
		} catch (EmailException | BoException e) {
			 logger.error("BoException in ResetPasswordController when sending password reset email");	
		}
	}
  

}
