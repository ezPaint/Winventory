package com.simoncomputing.app.winventory.controller.admin;

import java.io.IOException;
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
    // private static Logger logger = Logger.getLogger(AdminController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.forward(request, response, insertRoleJsp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get role title
        String title = request.getParameter("roleTitle");

        // generate role key?

        // create new role
        Role newRole = new Role();
        newRole.setTitle(title);

        // insert into role table
        try {
            RoleBo.getInstance().create(newRole);

            // check if admin is selected -- how do you get the value from a radio button?
            if (request.getParameter("adminPerms").equals("adminPerms")) {

                // if it is get all permissions from ref_permission
                List<RefPermission> permissions = RefPermissionBo.getInstance().getAll();

                // associate permissions with new role id in ref_permission_to_role
                for(RefPermission permission : permissions){
                    RefPermissionToRole permToRole = new RefPermissionToRole();
                    
                    String permId = permission.getKey() + "";
                    permToRole.setPermissionId(Integer.parseInt(permId));
                    
                    String roleId = newRole.getKey() + "";
                    permToRole.setPermissionId(Integer.parseInt(roleId));
                    
                    RefPermissionToRoleBo.getInstance().create(permToRole);
                    
                }
                
                request.setAttribute("Title", newRole.getTitle());
                request.setAttribute("permissions", permissions);
                
            }
            
            request.setAttribute("Title", "failed");
            request.setAttribute("permissions", "none");
            
            // otherwise
            // get each form group
            // filter out unchecked permissions
            // add "read" to each category that has checked permissions
            // get associated permission ids from ref_permissions
            // associate permissions with new role id in ref_perm_to_role

        } catch (BoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        this.forward(request, response, "/WEB-INF/flows/admin/insertSuccess.jsp");

    }
}
