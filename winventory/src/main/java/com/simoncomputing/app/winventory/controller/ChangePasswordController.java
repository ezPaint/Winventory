package com.simoncomputing.app.winventory.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.authentication.PasswordHasher;
import com.simoncomputing.app.winventory.bo.AccessTokenBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.AccessToken;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

import java.util.Date;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/changepassword")
public class ChangePasswordController extends BaseController {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ChangePasswordController.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordController() {
    	super(); 
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String token = request.getParameter("token");
    	int userId = Integer.parseInt(request.getParameter("user"));
    	AccessTokenBo accessTokenBo = AccessTokenBo.getInstance();
    	
		try {
			AccessToken accessToken = accessTokenBo.read(userId);
			//user does not have an associated access token
			if (accessToken == null) {
				request.getSession().setAttribute("changePasswordError", "The token is not valid. Please request a new token");
				request.getRequestDispatcher("/WEB-INF/flows/authentication/resetPassword.jsp").forward(request,
				        response);
			}
			//user has an associated access token
			else {
				//token not yet expired
				if (accessToken.getExpiration().before(new Date())) {
					String hashedToken = accessToken.getToken();
					
					//hashing the token matches the hashed token stored in the DB
					if (PasswordHasher.checkPassword(token, hashedToken)) {
						request.getSession().removeAttribute("changePasswordError");
						request.getSession().setAttribute("resetPasswordUser", userId);
						request.getRequestDispatcher("/WEB-INF/flows/authentication/changePassword.jsp").forward(request,
						        response);
					}
					//token does not match
					else {
						request.getSession().setAttribute("changePasswordError", "The token is not valid. Please request a new token");
						request.getRequestDispatcher("/WEB-INF/flows/authentication/resetPassword.jsp").forward(request,
						        response);
					}
				}
				//token already expired
				else {
					request.getSession().setAttribute("changePasswordError", "The token has expired. Please request a new token");
					request.getRequestDispatcher("/WEB-INF/flows/authentication/resetPassword.jsp").forward(request,
					        response);
				}
				
			}
		} catch (BoException e) {
			logger.error("BoException in ChangePasswordController when reading the access token");
		}
		   
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String newPassword = request.getParameter("newPassword");
    	Integer userId = (int) request.getSession().getAttribute("resetPasswordUser");
		
    	/* This should not be true in normal use cases since 
    	 * the change password form does not allow the user to
    	 * submit unless if the input is 8 characters or longer
    	 */
    	if (newPassword.length() < 8) {
    		request.getSession().setAttribute("changePasswordError", "Password was invalid");
			request.getRequestDispatcher("/WEB-INF/flows/authentication/resetPassword.jsp").forward(request,
			        response);
    	}
    	
		UserBo userBo = UserBo.getInstance();
		AccessTokenBo accessTokenBo = AccessTokenBo.getInstance();
		try {
			//get the current user and update the password
			User currentUser = userBo.read(userId.longValue());
			currentUser.setPassword(PasswordHasher.encodePassword(newPassword));
			userBo.update(currentUser);
			//delete the token from ACCESS_TOKEN so the URL cannot be used again
			accessTokenBo.delete(userId);
			request.getRequestDispatcher("/WEB-INF/flows/authentication/resetSucess.jsp").forward(request,
			        response);
		} catch (BoException e) {
			logger.error("BoException in ChangePasswordController when updating the user's password");
		}
      
    }

}
