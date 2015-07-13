package com.simoncomputing.app.winventory.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simoncomputing.app.winventory.controller.BaseController;

@WebServlet("/admin/insert-success")
public class InsertSuccessController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static final String insertSuccessJsp = "/WEB-INF/flows/admin/insertSuccess.jsp";
    // private static Logger logger = Logger.getLogger(AdminController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.forward(request, response, insertSuccessJsp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

}
