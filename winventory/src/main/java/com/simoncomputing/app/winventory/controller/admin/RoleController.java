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

import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.bo.RefPermissionBo;
import com.simoncomputing.app.winventory.bo.RefPermissionToRoleBo;
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.domain.RefPermission;
import com.simoncomputing.app.winventory.domain.RefPermissionToRole;
import com.simoncomputing.app.winventory.domain.Role;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Servlet implementation class RoleController
 */
@WebServlet("/admin/role")
public class RoleController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static final String roleJsp = "/WEB-INF/flows/admin/role.jsp";
    private static Logger logger = Logger.getLogger(RoleController.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // require role to be admin
        if (getUserInfo(request).getRoleId() != 1) {
            this.denyPermission(request, response);
            return;
        } else {
            ArrayList<Role> roles = null;
            ArrayList<RefPermission> refPermissions = null;

            try {
                // Populate table with all roles currently in the database
                roles = (ArrayList<Role>) RoleBo.getInstance().getAll();
            } catch (BoException e) {
                logError(logger, e);
                logger.error("Bo exception for Roles Table");
                request.setAttribute("fail", "An Error Has Occured: 1010193");
            }
            request.setAttribute("roles", roles);

            try {
                // Populate permissions options with all permissions currently
                // in database
                refPermissions = (ArrayList<RefPermission>) RefPermissionBo.getInstance().getAll();
            } catch (BoException e) {
                logError(logger, e);
                logger.error("Bo exception for RefPermissions Table");
                request.setAttribute("fail", "An Error Has Occured: 1010193");
            }
            request.setAttribute("refPerms", refPermissions);

            this.forward(request, response, roleJsp);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user wants to delete role
        if (request.getParameter("delete") != null) {
            tryDelete(request);
        }

        // Check if user wants to create a new role
        if (request.getParameter("create") != null) {
            tryCreate(request);
        }

        // Reload the page
        this.doGet(request, response);
    }

    /**
     * Private helper method that creates a new role with user-specified
     * permissions
     * 
     * @param request
     *            the HttpServletRequest
     */
    private void tryCreate(HttpServletRequest request) {
        // create new role
        Role newRole = new Role();

        try {
            // generate role key
            int key = RoleBo.getInstance().getAll().size() + 1;
            newRole.setKey((long) key);

            // get role title
            String title = request.getParameter("roleTitle");
            newRole.setTitle(title);

            // check title name
            boolean takenTitle = checkTitle(title);

            // if the title is already being used, don't create new role
            if (takenTitle) {
                logger.error("This title is already being used.");
                request.setAttribute("fail", "Could not create role: " + newRole.getTitle()
                        + " because this title is already being used.");
                return;
            }

            // insert into role table
            RoleBo.getInstance().insert(newRole);

            // check if admin radio button is selected

            logger.info("Creating a custom, non-admin role" + title + " now.");

            // otherwise get each form group and filter out unchecked
            // permissions
            List<String> labels = getLabels(request);

            if (labels.size() < 1) {
                logger.error("Could not create role: " + newRole.getTitle()
                        + ", no permissions specified");
                request.setAttribute("fail", "Could not create role: " + newRole.getTitle()
                        + ", no permissions specified");
            } else {
                // create a list of permissions to associate
                List<RefPermission> permsToAdd = new ArrayList<RefPermission>();

                // get associated permission ids from ref_permissions
                for (String name : labels) {
                    permsToAdd.add(RefPermissionBo.getInstance().getRefPermissionByName(name));
                }

                // associate role and permissions
                List<RefPermission> permissions = associate(newRole, permsToAdd);

                request.setAttribute("pass",
                        "New role added with custom permissions: " + newRole.getTitle());

            }

        } catch (BoException e) {
            logError(logger, e);
            logger.error("Could not create role: " + newRole.getTitle(), e);
            request.setAttribute("fail", "Could not create role: " + newRole.getTitle());
        }

    }

    /**
     * Private helper method to check if a role title is currently in the
     * database.
     * 
     * @param title
     *            the role title to check
     * @return true if the title already exists in the database, false otherwise
     */
    private boolean checkTitle(String title) {
        boolean titleTaken = false;

        try {
            // Get all of the current roles in the database
            List<Role> names = RoleBo.getInstance().getAll();

            // Check if any of the current role titles match the desired role
            // title
            for (Role r : names) {
                String name = r.getTitle();

                // If the desired title is already being used, titleTaken is
                // true, break out of loop
                if (name.equals(title)) {
                    titleTaken = true;
                    break;
                }
            }
        } catch (BoException e) {
            logger.error("Could not get Roles", e);
        }

        return titleTaken;
    }

    /**
     * Private helper method to delete a role.
     * 
     * Roles can be deleted if there are no Users associated with them,
     * otherwise the role cannot be deleted.
     * 
     * @param request
     */
    private void tryDelete(HttpServletRequest request) {
        // Get the key of the role the user wants to delete
        Long key = Long.parseLong(request.getParameter("delete"));

        try {
            // Get all of the users and see if any are attached to the role
            // being deleted
            List<User> users = UserBo.getInstance().getAll();
            String givenRole = key + "";
            String title = RoleBo.getInstance().read(key).getTitle();

            boolean inUse = false;

            for (User u : users) {
                String userRole = u.getRoleId() + "";

                // Check each user role and see if the role id matches the key
                // of the role being deleted.
                if (userRole.equals(givenRole)) {
                    inUse = true;
                    break;
                }
            }

            // If there is a user associated with the role, don't delete
            if (inUse) {
                logger.error("User tried to delete Role that is in Use");
                try {
                    request.setAttribute("fail", "Cannot Delete Role: "
                            + RoleBo.getInstance().read(key).getTitle() + " (In Use)");
                } catch (BoException e1) {
                    logError(logger, e1);
                    logger.error("No role associated with key: " + key, e1);
                }
            } else {
                // No users are associated with the role and the role can be
                // deleted

                // Delete all references to that role from the
                // RefPermissionToRole table
                List<RefPermissionToRole> perms = RefPermissionToRoleBo.getInstance()
                        .getListByRoleId(Integer.parseInt(givenRole));

                for (RefPermissionToRole ref : perms) {
                    RefPermissionToRoleBo.getInstance().delete(ref.getKey());
                }

                // Delete the role itself from the Role table
                RoleBo.getInstance().delete(key);
                logger.info("Deleted role: " + title);
                request.setAttribute("pass", "Successfully deleted role: " + title);
            }
        } catch (BoException e) {
            logError(logger, e);
            logger.error("User tried to delete Role that is in Use", e);
            try {
                request.setAttribute("fail", "Cannot Delete Role: "
                        + RoleBo.getInstance().read(key).getTitle() + " (In Use)");
            } catch (BoException e1) {
                logError(logger, e1);
                logger.error("No role associated with key: " + key, e1);
            }
        }
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
            long listKey = (long) (RefPermissionToRoleBo.getInstance().getAll().size() + 2);

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
            logError(logger, e);
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

        if (request.getParameter("updateUser") != null) {
            labels.add("updateUser");
        }

        if (request.getParameter("deleteUser") != null) {
            labels.add("deleteUser");
        }

        if (request.getParameter("readUser") != null) {
            labels.add("readUser");
        }

        // get hardware permissions
        if (request.getParameter("createHardware") != null) {
            labels.add("createHardware");
        }

        if (request.getParameter("updateHardware") != null) {
            labels.add("updateHardware");
        }

        if (request.getParameter("deleteHardware") != null) {
            labels.add("deleteHardware");
        }

        if (request.getParameter("readHardware") != null) {
            labels.add("readHardware");
        }

        // get software permissions
        if (request.getParameter("createSoftware") != null) {
            labels.add("createSoftware");
        }

        if (request.getParameter("updateSoftware") != null) {
            labels.add("updateSoftware");
        }

        if (request.getParameter("deleteSoftware") != null) {
            labels.add("deleteSoftware");
        }

        if (request.getParameter("readSoftware") != null) {
            labels.add("readSoftware");
        }

        // get self permissions
        if (request.getParameter("createSelf") != null) {
            labels.add("createSelf");
        }

        if (request.getParameter("updateSelf") != null) {
            labels.add("updateSelf");
        }

        if (request.getParameter("deleteSelf") != null) {
            labels.add("deleteSelf");
        }

        if (request.getParameter("readSelf") != null) {
            labels.add("readSelf");
        }

        // get location permissions
        if (request.getParameter("createLocation") != null) {
            labels.add("createLocation");
        }

        if (request.getParameter("updateLocation") != null) {
            labels.add("updateLocation");
        }

        if (request.getParameter("deleteLocation") != null) {
            labels.add("deleteLocation");
        }

        if (request.getParameter("readLocation") != null) {

            labels.add("readLocation");
        }

        // get address permissions
        if (request.getParameter("createAddress") != null) {
            labels.add("createAddress");
        }

        if (request.getParameter("updateAddress") != null) {
            labels.add("updateAddress");
        }

        if (request.getParameter("deleteAddress") != null) {
            labels.add("deleteAddress");
        }

        if (request.getParameter("readAddress") != null) {

            labels.add("readAddress");
        }

        // get barcode permissions
        if (request.getParameter("createBarcode") != null) {
            labels.add("createBarcode");
        }

        if (request.getParameter("updateBarcode") != null) {
            labels.add("updateBarcode");
        }

        if (request.getParameter("deleteBarcode") != null) {
            labels.add("deleteBarcode");
        }

        if (request.getParameter("readBarcode") != null) {

            labels.add("readBarcode");
        }

        return labels;
    }
}
