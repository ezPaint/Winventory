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
			if (accessToken == null) {
				//user does not have an associated access token
				request.getSession().setAttribute("changePasswordError", "The token is not valid. Please request a new token");
				request.getRequestDispatcher("/WEB-INF/flows/authentication/resetPassword.jsp").forward(request,
				        response);
			}
			else {
				if (accessToken.getExpiration().before(new Date())) {
					String hashedToken = accessToken.getToken();
					//user and token are correct
					if (PasswordHasher.checkPassword(token, hashedToken)) {
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
				else {
					//token already expired
					request.getSession().setAttribute("changePasswordError", "The token has expired. Please request a new token");
					request.getRequestDispatcher("/WEB-INF/flows/authentication/resetPassword.jsp").forward(request,
					        response);
				}
				
			}
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String newPassword = request.getParameter("newPassword");
    	Integer userId = (int) request.getSession().getAttribute("resetPasswordUser");
		
		UserBo userBo = UserBo.getInstance();
		AccessTokenBo accessTokenBo = AccessTokenBo.getInstance();
		try {
			User currentUser = userBo.read(userId.longValue());
			currentUser.setPassword(PasswordHasher.encodePassword(newPassword));
			userBo.update(currentUser);
			accessTokenBo.delete(userId);
			request.getRequestDispatcher("/WEB-INF/flows/authentication/resetSucess.jsp").forward(request,
			        response);
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
    }

}
