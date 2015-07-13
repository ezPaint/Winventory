package com.simoncomputing.app.winventory.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

@WebServlet("/admin/insert-role")
public class InsertRoleController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static final String insertRoleJsp = "/WEB-INF/flows/admin/insertRole.jsp";
    private static Logger logger = Logger.getLogger(InsertRoleController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

            logger.info("creating new role with title" + title + "now.");

            // insert into role table
            RoleBo.getInstance().create(newRole);


            // generate key for ref_permission_to_role table
            long listKey = (long) (RefPermissionToRoleBo.getInstance().getAll().size() + 1);

            
            // check if admin radio button is selected
            if (request.getParameter("adminPerms") != null) {

                // if it is get all permissions from ref_permission
                List<RefPermission> permsToAdd = RefPermissionBo.getInstance().getAll();

                // get role id to associate permissions with
                String roleId = newRole.getKey() + "";

                // iterate over list of permissions and add each to role
                for (RefPermission permission : permsToAdd) {

                    RefPermissionToRole permToRole = new RefPermissionToRole();
                    permToRole.setKey(listKey);

                    //get permission id to associate 
                    String permId = (permission.getKey()) + "";
                    permToRole.setPermissionId(Integer.parseInt(permId));
                    
                    permToRole.setRoleId(Integer.parseInt(roleId));

                    logger.info("binding permission: #" + permission.getKey() + ", "
                            + permission.getCode() + " to role " + roleId);
                    
                    // insert new reference into ref_permission_to_role table
                    RefPermissionToRoleBo.getInstance().insert(listKey,Integer.parseInt(permId),Integer.parseInt(roleId));

                    listKey++;
                }

                request.setAttribute("Title", newRole.getTitle());
                request.setAttribute("permissions", permsToAdd);

            } else {
                // otherwise
                // get each form group
                // filter out unchecked permissions
                // add "read" to each category that has checked permissions
                // get associated permission ids from ref_permissions
                // associate permissions with new role id in ref_perm_to_role

                List<RefPermission> permissions = new ArrayList<RefPermission>();
                RefPermission ref = new RefPermission();
                ref.setCode("permission");
                permissions.add(ref);

                RefPermissionToRoleBo.getInstance().create(new RefPermissionToRole());

                request.setAttribute("Title", newRole.getTitle());
                request.setAttribute("permissions", permissions);

            }

        } catch (BoException e) {

            List<RefPermission> permissions = new ArrayList<RefPermission>();
            RefPermission ref = new RefPermission();
            ref.setCode("none");
            permissions.add(ref);

            request.setAttribute("Title", "failed");
            request.setAttribute("permissions", permissions);
            e.printStackTrace();
        }

        this.forward(request, response, "/WEB-INF/flows/admin/insertSuccess.jsp");

    }
}
