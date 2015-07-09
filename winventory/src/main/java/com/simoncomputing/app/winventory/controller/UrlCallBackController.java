package com.simoncomputing.app.winventory.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.apache.log4j.Logger;
import org.json.*;

import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.formbean.UserInfoBean;

/**
 * Servlet implementation class UrlCallBackController
 */
@WebServlet(urlPatterns = "/googleCallback")
public class UrlCallBackController extends BaseController {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(UrlCallBackController.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UrlCallBackController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		// Check if the user have rejected and get session.
		String error = request.getParameter("error");
		HttpSession session = request.getSession();
		
		if ((null != error) && ("access_denied".equals(error.trim()))) {
			logger.error("Google Gave JSON error");
			session.invalidate();
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		// OK the user have consented so lets find out about the user
		OAuthService service = (OAuthService) session.getAttribute("service");
		
		// Get the all important authorization code
		String code = request.getParameter("code");
		
		// Construct the access token
		Token token = service.getAccessToken(null, new Verifier(code));
		if (token == null) {
			this.bootAttempt(request, response, "Google didn't give a Token");
			return;
		}

		// Now do something with it - get the user's G+ profile
		OAuthRequest authRequest = new OAuthRequest(Verb.GET,
				"https://www.googleapis.com/userinfo/v2/me");
		service.signRequest(token, authRequest);
		Response authResponse = authRequest.send();
		
		// Get the JSON Object from the Response
		BufferedReader read = new BufferedReader(new InputStreamReader(authResponse.getStream()));
		StringBuilder str = new StringBuilder();

		String inputStr;
	    while ((inputStr = read.readLine()) != null)
	        str.append(inputStr);
	    
	    JSONObject jsonResponse;
		try {
			jsonResponse = new JSONObject(str.toString());
		} catch (JSONException e) {
			logger.fatal("Incorrect JSON from Google");
			jsonResponse = null;
		}

		UserInfoBean userBean = new UserInfoBean(); 

		try {
			userBean.setEmail(jsonResponse.getString("email"));
		} 
		catch (JSONException j) {
			session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
            return;
		}
		
		UserBo userBo = null;; 
		User user = null; 
		
		// Check if Google Email is in the DB
		try {
			userBo = UserBo.getInstance();
			user = userBo.getUserByEmail(userBean.getEmail());
		}
		catch(Exception e) {
			this.bootAttempt(request, response, "An Error has Occured with UserBo");
			return;
		}
	    if (user == null) {
	    	// Email isn't in the DB
	    	this.bootAttempt(request, response, "Email: " + userBean.getEmail() + " not in DB");
	    	return;
	    }
		
		// Check if password isn't googleUser
		if (!user.getPassword().equals("googleUser")) {
	     	this.bootAttempt(request, response, "Email: " + userBean.getEmail() + " password is not 'googleUser'");
	     	return;
		}
		
		// Email is valid User
		userBean.bindUser(user);
		request.getSession().invalidate();
    	logger.info("Logging in user " + userBean.getUsername() + " now.");
		this.setUserInfo(request, userBean);
		response.sendRedirect(request.getContextPath() + "/hardware/results");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void bootAttempt(HttpServletRequest request, HttpServletResponse response, String msg) 
			throws ServletException, IOException {
     	logger.info(msg);
     	request.getSession().invalidate();
        request.getSession().setAttribute("error", "Google User Not Recognized");
		response.sendRedirect(request.getContextPath() + "/login");

	}
}
