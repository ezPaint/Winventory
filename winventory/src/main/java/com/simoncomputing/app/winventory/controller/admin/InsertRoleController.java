package com.simoncomputing.app.winventory.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.bo.RefPermissionBo;
import com.simoncomputing.app.winventory.bo.RefPermissionToRoleBo;
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.domain.RefPermission;
import com.simoncomputing.app.winventory.domain.RefPermissionToRole;
import com.simoncomputing.app.winventory.domain.Role;
import com.simoncomputing.app.winventory.util.BoException;

@WebServlet("/admin/role")
public class InsertRoleController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static final String insertRoleJsp = "/WEB-INF/flows/admin/role.jsp";
    private static Logger logger = Logger.getLogger(InsertRoleController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Role> roles = null;
        try {
            roles = (ArrayList<Role>) RoleBo.getInstance().getAll();
        } catch (BoException e) {
            logger.error("Bo exception for Roles Table");
            request.setAttribute("fail", "An Error Has Occured: 1010193");
        }
        request.setAttribute("roles", roles);
        
        this.forward(request, response, insertRoleJsp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // create new role
        Role newRole = new Role();

        try {
            // generate role key
            int key = RoleBo.getInstance().getAll().size() + 1;
            newRole.setKey((long) key);

            // get role title
            String title = request.getParameter("roleTitle");
            newRole.setTitle(title);

            logger.info("creating new role with title " + title + " now.");

            // insert into role table
            RoleBo.getInstance().create(newRole);

            // check if admin radio button is selected
           if(request.getParameterValues("permType")[0].equals("adminPerms")){

                // if it is get all permissions from ref_permission
                List<RefPermission> permsToAdd = RefPermissionBo.getInstance().getAll();

                // associate role and permissions
                List<RefPermission> permissions = associate(newRole, permsToAdd);

                request.setAttribute("Title", newRole.getTitle());
                request.setAttribute("permissions", permissions);

            } else {
                logger.info("creating a custom, non-admin role");

                // otherwise get each form group and filter out unchecked
                // permissions
                List<String> labels = getLabels(request);

                // create a list of permissions to associate
                List<RefPermission> permsToAdd = new ArrayList<RefPermission>();

                // get associated permission ids from ref_permissions
                for (String name : labels) {
                    permsToAdd.add(RefPermissionBo.getInstance().getRefPermissionByName(name));
                }

                // associate role and permissions
                List<RefPermission> permissions = associate(newRole, permsToAdd);

                request.setAttribute("Title", newRole.getTitle());
                request.setAttribute("permissions", permissions);
            }

        } catch (BoException e) {
            // if an exception occurs, results page should display error message
            List<RefPermission> permissions = new ArrayList<RefPermission>();
            RefPermission ref = new RefPermission();
            ref.setCode("none");
            permissions.add(ref);

            request.setAttribute("Title", "failed");
            request.setAttribute("permissions", permissions);

            logger.error("Error occurred.", e);
        }

        this.forward(request, response, "/WEB-INF/flows/admin/insertSuccess.jsp");

    }

    /**
     * Private helper method that associates a new role with given permissions
     * 
     * @param role
     *            the role to associate with permissions
     * @param permsToAdd
     *            the list of permissions to associate with the role
     * @return the list of permissions that were successfully given to the role
     *         parameter
     */
    private List<RefPermission> associate(Role role, List<RefPermission> permsToAdd) {
        try {
            // generate key for ref_permission_to_role table
            long listKey = (long) (RefPermissionToRoleBo.getInstance().getAll().size() + 1);

            // get role id to associate permissions with
            String roleId = role.getKey() + "";

            // iterate over list of permissions and add each to role
            for (RefPermission permission : permsToAdd) {

                RefPermissionToRole permToRole = new RefPermissionToRole();
                permToRole.setKey(listKey);

                // get permission id to associate
                String permId = (permission.getKey()) + "";
                permToRole.setPermissionId(Integer.parseInt(permId));

                permToRole.setRoleId(Integer.parseInt(roleId));

                logger.info("binding permission: #" + permission.getKey() + ", "
                        + permission.getCode() + " to role " + roleId);

                // insert new reference into ref_permission_to_role table
                RefPermissionToRoleBo.getInstance().insert(listKey, Integer.parseInt(permId),
                        Integer.parseInt(roleId));

                listKey++;
            }

        } catch (BoException e) {
            logger.error("Error occurred", e);
        }

        return permsToAdd;
    }

    /**
     * Private helper method that creates a list of Strings that represents the
     * permissions that were checked on the JSP (desired permissions for role).
     * 
     * @param request
     *            the HttpServletRequest that contains desired permissions as
     *            parameters
     * @return the list of desired permission names
     */
    private List<String> getLabels(HttpServletRequest request) {
        List<String> labels = new ArrayList<String>();

        // get user permissions
        if (request.getParameter("createUser") != null) {
            labels.add("createUser");
        }

        if (request.getParameter("editUser") != null) {
            labels.add("updateUser");
        }

        if (request.getParameter("deleteUser") != null) {
            labels.add("deleteUser");
        }

        // get hardware permissions
        if (request.getParameter("createHardware") != null) {
            labels.add("createHardware");
        }

        if (request.getParameter("editHardware") != null) {
            labels.add("updateHardware");
        }

        if (request.getParameter("deleteHardware") != null) {
            labels.add("deleteHardware");
        }

        // get software permissions
        if (request.getParameter("createSoftware") != null) {
            labels.add("createSoftware");
        }

        if (request.getParameter("editSoftware") != null) {
            labels.add("updateSoftware");
        }

        if (request.getParameter("deleteSoftware") != null) {
            labels.add("deleteSoftware");
        }

        // add "read" to each category that has checked permissions

        if (labels.contains("createUser") || labels.contains("editUser")
                || labels.contains("deleteUser")) {
            labels.add("readUser");
        }

        if (labels.contains("createHardware") || labels.contains("editHardware")
                || labels.contains("deleteHardware")) {
            labels.add("readHardware");
        }

        if (labels.contains("createSoftware") || labels.contains("editSoftware")
                || labels.contains("deleteSoftware")) {
            labels.add("readSoftware");
        }

        return labels;
    }
}
