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
import com.simoncomputing.app.winventory.formbean.SetSmtpBean;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Servlet implementation class SetSmtpController
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		SetGoogleClientBean clientBean = new SetGoogleClientBean(); 
		clientBean.bindSmtp(gc);
		request.setAttribute("clientInfo", clientBean);
		this.forward(request, response, "/WEB-INF/flows/admin/SetGoogleClient.jsp");
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SetGoogleClientBean clientBean = new SetGoogleClientBean(request); 
		
		try {
			clientBean.updateSmtp();
		} 
		catch (BoException e) {
			logger.error("Bo Excpetion");
		}


		logger.info("Google Client Updated By User: " + this.getUserInfo(request).getUsername());
		request.setAttribute("note", "Google CLient Has Been Updated.");
		request.setAttribute("clientInfo", clientBean);
		this.forward(request, response, "/WEB-INF/flows/admin/SetGoogleClient.jsp");
		return;		
	}
}
