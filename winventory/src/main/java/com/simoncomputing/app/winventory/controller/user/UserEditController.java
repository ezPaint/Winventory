package com.simoncomputing.app.winventory.controller.user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.domain.Role;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Servlet implementation class UserEditController
 */
@WebServlet("/users/edit")
public class UserEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UserEditController.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserEditController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * displays the edit user page/form
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    // the key in the url that specifies the user
	    String key = request.getParameter("key");
	    
	    // try to get the user by key
        User user = null;
        if (key != null) {
            try {
                Long long_key = Long.parseLong(key);
                user = UserBo.getInstance().read(long_key);
            } catch (BoException e) {
                e.printStackTrace();
            }
        }
        
        // user not found or invalid key
        if (user == null) {
            request.setAttribute("error", "User not found.");
            logger.error("The key is not valid or there is no hardware with that key: " + key);
        }
        
        // try to get the list of all roles
        ArrayList<Role> roles = null;
        try {
            roles = new ArrayList<Role>(RoleBo.getInstance().getAll());
        } catch (BoException e) {
            e.printStackTrace();
        }
        
        // add roles attribute to the request
        if (roles != null) {
            request.setAttribute("roles", roles);
        }
        
        //add the user to the attribute
        request.setAttribute("user", user);
        
        // forward to the edit jsp
        request.getRequestDispatcher("/WEB-INF/flows/users/edit.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * handle the edit-user form
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
