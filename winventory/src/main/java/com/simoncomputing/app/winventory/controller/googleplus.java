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
import com.simoncomputing.app.winventory.bo.GoogleClientBo;
import com.simoncomputing.app.winventory.domain.GoogleClient;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Servlet implementation class googleplus
 */
@WebServlet("/login/googleLogin")
public class googleplus extends BaseController {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(googleplus.class);
	
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		GoogleClient gc = null; 
		try {
			gc = GoogleClientBo.getInstance().read(1L);
		} catch (BoException e) {
			logger.error("GoogleClient DB Error");
		}
		String id = gc.getClientId();
		String client = gc.getClientSecret();
		
		ServiceBuilder builder = new ServiceBuilder();
		OAuthService service = (OAuthService) builder
			.provider(Google2Api.class)
			.apiKey(id)
			.apiSecret(client)
			.callback(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/login/googleCallback")
			.scope("email").debugStream(System.out).build(); // Now build
																	// the call
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
