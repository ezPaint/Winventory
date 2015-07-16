package com.simoncomputing.app.winventory.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.RefPermissionBo;
import com.simoncomputing.app.winventory.bo.RefPermissionToRoleBo;
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.RefPermission;
import com.simoncomputing.app.winventory.domain.RefPermissionToRole;
import com.simoncomputing.app.winventory.domain.Role;
import com.simoncomputing.app.winventory.util.BoException;

@WebServlet("/admin/role/view-permissions")
public class ViewPermissionsController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static final String PermissionsJsp = "/WEB-INF/flows/admin/viewPermissions.jsp";
    private static Logger logger = Logger.getLogger(ViewPermissionsController.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (getUserInfo(request).getRoleId() == 1) {
            // Get the key for the role the user wants to view permissions of
            String key = request.getParameter("key");

            // Will hold information to be placed on request
            Role role = null;
            List<RefPermission> permissionList = new ArrayList<RefPermission>();

            try {
                // Get role from the db
                Long long_key = (long) Integer.parseInt(key);
                role = RoleBo.getInstance().read(long_key);
            } catch (BoException e) {
                logError(logger, e);
                logger.error("No role associated with key: " + key, e);
            }

            if (role != null) {
                try {
                    // Get references to all of the permissions associated with
                    // the role key
                    List<RefPermissionToRole> refs = RefPermissionToRoleBo.getInstance()
                            .getListByRoleId(Integer.parseInt(key));

                    // Get all of the permissions specified by the references
                    // about
                    for (RefPermissionToRole r : refs) {
                        RefPermission refPerm = RefPermissionBo.getInstance().read(
                                (long) r.getPermissionId());
                        permissionList.add(refPerm);
                    }
                } catch (NumberFormatException e) {
                    logError(logger, e);
                    logger.error("Invalid key specified.", e);
                } catch (BoException e) {
                    logError(logger, e);
                    logger.error("No role associated with key: " + key, e);
                }

            }

            request.setAttribute("role", role);
            request.setAttribute("permissions", permissionList);
            this.forward(request, response, PermissionsJsp);
        } else {
            this.denyPermission(request, response);
            return;
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    }
}
