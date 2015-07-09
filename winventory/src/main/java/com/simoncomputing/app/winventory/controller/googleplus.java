package com.simoncomputing.app.winventory.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

import com.simoncomputing.app.winventory.authentication.Google2Api;

/**
 * Servlet implementation class googleplus
 */
@WebServlet("/login/googleLogin")
public class googleplus extends BaseController {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(googleplus.class);
       
	private static final String CLIENT_ID = "1077593823261-ckjeh5eoq78j9r24mc3nc96u5l4lmcnt.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "8CSNUvP_TC-lAdzsQDHop67g";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public googleplus() {
        super();
        // TODO Auto-generated constructor stub
    }

    //"openid profile email " + 
    //
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    	      throws IOException, ServletException {
    	      ServiceBuilder builder= new ServiceBuilder(); 
    	      OAuthService service = (OAuthService) builder.provider(Google2Api.class) 
    	         .apiKey(CLIENT_ID) 
    	         .apiSecret(CLIENT_SECRET) 
    	         .callback("http://localhost:8181/winventory/login/googleCallback") 
    	         .scope("email")
    	         .debugStream(System.out)
    	         .build(); //Now build the call
    	      request.getSession().setAttribute("service", service);
    	      response.sendRedirect(service.getAuthorizationUrl(null)); 
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
