package com.simoncomputing.app.winventory.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.util.BoException;

@WebServlet("/admin")
public class AdminController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static final String adminJsp = "/WEB-INF/flows/admin/index.jsp";
    private static Logger logger = Logger.getLogger(AdminController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (getUserInfo(request).getRoleId() == 1) {
            try {
                getCurrentInfo(request);
            } catch (BoException e) {
                logger.error("Business Object exception");
            }

            request.getRequestDispatcher(adminJsp).forward(request, response);
        }
        else {
            requirePermission(request, response, "view admin page");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private void getCurrentInfo(HttpServletRequest request) throws BoException {
        int totHw = (HardwareBo.getInstance().getAll().size());
        request.setAttribute("totHw", totHw);

        // Eventually will get number of pieces of hardware in storage
        // and use that value to calculate the percentage of hardware
        // currently in use
        // int hwInUse = 87;
        // request.setAttribute("hwInUse", hwInUse);

        int numSw = (SoftwareBo.getInstance().getAll().size());
        request.setAttribute("numSw", numSw);

        int numUsers = (UserBo.getInstance().getAll().size());
        request.setAttribute("numUsers", numUsers);
    }

}
