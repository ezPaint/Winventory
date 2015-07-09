package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.BoException;
import org.apache.log4j.Logger;

/**
 * Controller Servlet for the Software page
 * 
 * @author Megan Rigsbee
 *
 */
@WebServlet("/software")
public class ResultsController extends BaseController {
    
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ResultsController.class);
    
    /**
     * Loads the software page.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        // Retrieve all software objects from the database table 'Software'
        ArrayList<Software> results = null;
        try {
            results = new ArrayList<Software>(SoftwareBo.getInstance().getAll());
        } catch (BoException e) {
            // TODO
            log.error(e.getMessage());
        }
        
        if (results != null) {
            request.setAttribute("results", results);
        }
        
        forward(request, response, "/WEB-INF/flows/software/results.jsp");
    }
    
    /**
     * Occurs when the form ADD form is submitted on the software page.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        forward(request, response, "/WEB-INF/flows/software/results.jsp");
    }
}
