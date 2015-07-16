package com.simoncomputing.app.winventory.controller.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Servlet implementation class ConditionsController
 */
@WebServlet("/admin/condition")
public class ConditionsController extends BaseController {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(ConditionsController.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConditionsController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (getUserInfo(request).getRoleId() == 1) {
            ArrayList<RefCondition> conditions = null;
            try {
                conditions = (ArrayList<RefCondition>) RefConditionBo.getInstance().getAll();
            } catch (BoException e) {
                logError(logger, e);
                logger.error("Bo exception for RefCondition Table");
                request.setAttribute("fail", "An Error Has Occured: 1010193");
            }
            request.setAttribute("conditions", conditions);
            this.forward(request, response, "/WEB-INF/flows/admin/condition.jsp");
        } else {
            denyPermission(request, response);
            return;
        }
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //
        if (request.getParameter("add") != null) {
            RefCondition ref = new RefCondition();
            ref.setCode(request.getParameter("name"));
            ref.setDescription(request.getParameter("description"));

            try {
                RefConditionBo.getInstance().create(ref);
                request.setAttribute("pass", "Condition Added: " + ref.getCode());
            } catch (BoException e) {
                logError(logger, e);
                logger.error("Bo exception for RefCondition Table");
                request.setAttribute("fail", "An Error Has Occured: 1010193");
            }
        }

        //
        if (request.getParameter("delete") != null) {
            String code = request.getParameter("delete");

            try {
                RefConditionBo.getInstance().delete(code);
                request.setAttribute("pass", "Condition Deleted: " + request.getParameter("delete"));
            } catch (BoException e) {
                logError(logger, e);
                logger.error("User tried to delete Condition that is in Use");
                request.setAttribute("fail",
                        "Cannot Delete Condition: " + request.getParameter("delete") + " (In Use)");
            }
        }

        //
        this.doGet(request, response);
        return;
    }

}
