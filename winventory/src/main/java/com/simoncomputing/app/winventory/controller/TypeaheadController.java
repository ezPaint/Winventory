package com.simoncomputing.app.winventory.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Servlet which returns json arrays to typeahead searches for suggestions.
 */
@WebServlet("/typeahead")
public class TypeaheadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TypeaheadController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    // prepare for json response
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
	    
        // data == hardware types
		if (request.getParameter("data").equals("hardwareTypes")) {
		    
		    HardwareBo hardwareBo = HardwareBo.getInstance();
		    ArrayList<String> hardwareTypes = new ArrayList<String>();
		    
		    try {
		        // get the top 100 most common hardware types
		        hardwareTypes = (ArrayList<String>) hardwareBo.getTopTypes(100);
		        
		    } catch (BoException e) {
		        // typeahead won't work if this is reached
		        return;
		        
		    }
		    
		    // write the json array to the response body
		    JSONArray typesJson = new JSONArray(hardwareTypes);
		    writer.println(typesJson.toString());
		    
		}
		
		// data == usernames
		if (request.getParameter("data").equals("usernames")) {
		    
		    UserBo userBo = UserBo.getInstance();
		    ArrayList<String> usernames = new ArrayList<String>();
		    
		    try {
		        // get 1000 usernames from the db
		        usernames = (ArrayList<String>) userBo.getAllUsernames(1000);
		    } catch (BoException e) {
		        // if this is the case, then typeahead wont work
		        return;
		    }
		    
		    // write the json array to the response body
            JSONArray usernamesJson = new JSONArray(usernames);
            writer.println(usernamesJson.toString());
		    
		}
		return;
	}

}
