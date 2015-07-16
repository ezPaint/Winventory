package com.simoncomputing.app.winventory.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Servlet implementation class AdminController.
 */
@WebServlet("/admin")
public class AdminController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static final String adminJsp = "/WEB-INF/flows/admin/index.jsp";
    private static Logger logger = Logger.getLogger(AdminController.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // require admin role
        if (getUserInfo(request).getRoleId() == 1) {
            try {
                getCurrentInfo(request);
            } catch (BoException e) {
                logError(logger, e);
            }
            // if role is admin, forward to admin jsp
            request.getRequestDispatcher(adminJsp).forward(request, response);
        } else {
            // permission denied
            this.denyPermission(request, response);
            return;
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Private helper method to populate the dashboard with information about
     * the current state of the database
     * 
     * @param request
     *            the HttpServletRequest
     * @throws BoException
     */
    private void getCurrentInfo(HttpServletRequest request) throws BoException {
        double totHw = (HardwareBo.getInstance().getAll().size());
        int hwTot = (int) totHw;
        request.setAttribute("totHw", hwTot);

        if (totHw != 0) {
            double hwInUse = (HardwareBo.getInstance().getInUse().size());
            double hwUse = (hwInUse / totHw) * 100;
            String usage = String.format("%.0f", hwUse);
            usage += "%";
            request.setAttribute("usage", usage);

            double hwInStorage = totHw - hwInUse;
            double hwStore = (hwInStorage / totHw) * 100;
            String storage = String.format("%.0f", hwStore);
            storage += "%";
            request.setAttribute("storage", storage);
        } else {
            request.setAttribute("usage", "0%");
            request.setAttribute("storage", "0%");
        }

        int numSw = (SoftwareBo.getInstance().getAll().size());
        request.setAttribute("numSw", numSw);

        int numUsers = (UserBo.getInstance().getAll().size());
        request.setAttribute("numUsers", numUsers);

    }

}
