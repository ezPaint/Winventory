package com.simoncomputing.app.winventory.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.GoogleClientBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.GoogleClient;
import com.simoncomputing.app.winventory.formbean.SetGoogleClientBean;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Servlet implementation class SetSmtpController
 * 
 * Handles the "SetGoogleClient.jsp" page which allows user to make changes to the 
 * Google Client to allow Google Login. 
 * 
 * Google Client requires that the DB start with a GoogleClient of key "1L" be 
 * pre-loaded in the DB. 
 * 
 * @author nicholas.phillpott
 */
@WebServlet("/admin/setGoogleClient")
public class SetGoogleClientController extends BaseController {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(SetGoogleClientController.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetGoogleClientController() {
        super();
    }

	/**
	 * Makes sure that the GoogleClient is initialized in the DB. The GoogleClient settings 
	 * require that an object be pre-loaded in the DB with the key of "1L". 
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Check the DB for an GoogleClient at key "1L", catch any errors.
		GoogleClient gc = null;
		try {
			gc = GoogleClientBo.getInstance().read(1L);
		} catch (BoException e) {
			logger.fatal("REALLY BAD STUFF");
			this.forward(request, response, "/admin");
			return;
		}
		if (gc == null) {
			logger.fatal("Your Database doesn't have smtp at correct key");
			this.forward(request, response, "/admin");
			return;
		}
		
		// Add the bean to the session and go to the JSP.
		SetGoogleClientBean clientBean = new SetGoogleClientBean(); 
		clientBean.bindGoogleClient(gc);
		request.setAttribute("clientInfo", clientBean);
		this.forward(request, response, "/WEB-INF/flows/admin/SetGoogleClient.jsp");
		return;
	}

	/**
	 * Take the updated settings and make them to the DB. 
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Update the DB GoogleClient record. 
		SetGoogleClientBean clientBean = new SetGoogleClientBean(request); 
		try {
			clientBean.updateSmtp();
		} 
		catch (BoException e) {
			logger.error("Bo Excpetion");
		}

		// Give the user confirmation that the settings have been updated. 
		logger.info("Google Client Updated By User: " + this.getUserInfo(request).getUsername());
		request.setAttribute("note", "Google CLient Has Been Updated.");
		request.setAttribute("clientInfo", clientBean);
		this.forward(request, response, "/WEB-INF/flows/admin/SetGoogleClient.jsp");
		return;		
	}
}
