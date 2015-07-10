package com.simoncomputing.app.winventory.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.SmtpBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Smtp;
import com.simoncomputing.app.winventory.formbean.SetSmtpBean;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Servlet implementation class SetSmtpController
 * 
 * Handles the "SetSmtp.jsp" page which allows user to make changes to the 
 * SMTP settings to allow email notifications. 
 * 
 * SMTP Features require that the DB start with a SMTP of key "1L" be 
 * pre-loaded in the DB. 
 * 
 * @author nicholas.phillpott
 */
@WebServlet("/admin/setSmtp")
public class SetSmtpController extends BaseController {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(SetSmtpController.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetSmtpController() {
        super();
    }

	/**
	 * Makes sure that the SMTP is initialized in the DB. The SMTP settings require that 
	 * a SMTP object be preloaded in the DB with the key of "1L". 
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Check the DB for an SMTP at key "1L", catch any errors.
		Smtp smtp = null;
		try {
			smtp = SmtpBo.getInstance().read(1L);
		} catch (BoException e) {
			logger.fatal("REALLY BAD STUFF");
			this.forward(request, response, "/login");
			return;
		}
		if (smtp == null) {
			logger.fatal("Your Database doesn't have smtp at correct key");
			this.forward(request, response, "/login");
			return;
		}
		
		// Add the bean to the session and go to the JSP.
		SetSmtpBean smtpBean = new SetSmtpBean();
		smtpBean.bindSmtp(smtp);
		request.setAttribute("smtpInfo", smtpBean);
		this.forward(request, response, "/WEB-INF/flows/admin/SetSmtp.jsp");
		return;
	}

	/**
	 * Take the updated settings and make them to the DB. 
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Update the DB SMTP record. 
		SetSmtpBean smtpBean = new SetSmtpBean(request);
		try {
			smtpBean.updateSmtp();
		} 
		catch (BoException e) {
			logger.error("Bo Excpetion");
		}
		
		// Give the user confirmation that the settings have been updated. 
		logger.info("SMTP Settings Updated By User: " + this.getUserInfo(request).getUsername() + " New HostName: " + smtpBean.getHostName());
		request.setAttribute("note", "SMTP Settings Have Been Updated.");
		request.setAttribute("smtpInfo", smtpBean);
		this.forward(request, response, "/WEB-INF/flows/admin/SetSmtp.jsp");
		return;		
	}

}
