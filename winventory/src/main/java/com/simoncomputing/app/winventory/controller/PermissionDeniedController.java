package com.simoncomputing.app.winventory.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * The "permission denied" page.
 */
@WebServlet("/permissionDenied")
public class PermissionDeniedController extends BaseController {
	private static final long serialVersionUID = 1L;
	// private static Logger logger = Logger.getLogger(PermissionDeniedController.class);
       
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
		request.getRequestDispatcher("/permissionDenied.jsp").forward(request, response);
	}

}
