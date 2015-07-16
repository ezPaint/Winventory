package com.simoncomputing.app.winventory.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.GoogleClientBo;
import com.simoncomputing.app.winventory.domain.GoogleClient;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * The "permission denied" page.
 */
@WebServlet("/permissionDenied")
public class PermissionDeniedController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(PermissionDeniedController.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PermissionDeniedController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        GoogleClient gc;
		try {
			gc = GoogleClientBo.getInstance().read(1L);
	    	if (gc.getClientId().length() > 10) {
	    		request.setAttribute("GC", true);
	    	}
		} catch (BoException e) {
			logger.error("DataBase Error: Google Client");
		}
		request.getRequestDispatcher("/permissionDenied.jsp").forward(request, response);
	}

}
