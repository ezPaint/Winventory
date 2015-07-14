package com.simoncomputing.app.winventory.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.controller.BaseController;

@WebServlet("/admin/role/view-permissions")
public class ViewPermissionsController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static final String PermissionsJsp = "/WEB-INF/flows/admin/view.jsp";
    private static Logger logger = Logger.getLogger(InsertRoleController.class);
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.forward(request, response, PermissionsJsp);
    }
}
