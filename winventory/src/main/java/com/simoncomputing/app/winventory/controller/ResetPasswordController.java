package com.simoncomputing.app.winventory.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.engine.Session;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.authentication.EmailService;
import com.simoncomputing.app.winventory.authentication.PasswordHasher;
import com.simoncomputing.app.winventory.bo.AccessTokenBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.AccessToken;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/resetpassword")
public class ResetPasswordController extends BaseController {
	
	private static final long serialVersionUID = 1L;
	
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
		if (resetUser.getKey() == null) {
			
		}
		//user was found
		else {

	    	UUID uuid = UUID.randomUUID();
	    	String token = uuid.toString().replaceAll("-", "");
	    	
	    	//should be changed to https
	        String urlPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	        String message = urlPath + "/changepassword" + "?token=" + token + "&user=" + resetUser.getKey().intValue();
	          
	        //insert into database hashed version of token
			AccessTokenBo accessTokenBo = AccessTokenBo.getInstance();
			AccessToken accessToken = new AccessToken();
			
			//TODO: change access token domain object to match type
			accessToken.setUserKey(resetUser.getKey().intValue());
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Insert the new access token and email the user a link
			// with the token embedded in the url
			try {
				accessTokenBo.create(accessToken);
				EmailService sendResetEmail = new EmailService();
				try {
					sendResetEmail.setSmtp();
					sendResetEmail.setFrom("fivewetmice@gmail.com");
					//currently send here for testing purposes
					sendResetEmail.addTo("jspemailtest@gmail.com");
					sendResetEmail.setSubject("Winventory Password Reset");
					sendResetEmail.setMessage(message);
					sendResetEmail.sendEmail();
				} catch (EmailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (BoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.getRequestDispatcher("/WEB-INF/flows/authentication/resetSucess.jsp").forward(request,
			        response);
			   
		}
      
    }

}
