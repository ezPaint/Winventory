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
	    
        // if url looks like typeahead?data=hardwareTypes
		if (request.getParameter("data").equals("hardwareTypes")) {
		    
		    HardwareBo hardwareBo = HardwareBo.getInstance();
		    ArrayList<String> hardwareTypes = new ArrayList<String>();
		    
		    try {
		        // get the top 100 most common hardware types
		        hardwareTypes = (ArrayList<String>) hardwareBo.getTopTypes(100);
		        
		    } catch (BoException e) {
		        
		        return;
		        
		    }
		    
		    // write the json array to the response body
		    JSONArray typesJson = new JSONArray(hardwareTypes);
		    writer.println(typesJson.toString());
		    
		}
		return;
	}

}
