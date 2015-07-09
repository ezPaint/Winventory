package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Single software item view controller.
 * @author megan.rigsbee
 *
 */
@WebServlet("/software/view")
public class ViewController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ViewController.class);
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Retrieve the key of the software object selected by the user to view and retrieve the
        //corresponding software object.
        String key = request.getParameter("key");

        Software software = null;
        if (key != null) {
            try {
                Long long_key = Long.parseLong(key);
                software = SoftwareBo.getInstance().read(long_key); //get software obj from database
            } catch (BoException e) {
                log.error(e.getMessage());
            }
        }
        request.setAttribute("key", key);
        request.setAttribute("software", software); 
        forward(request, response, "/WEB-INF/flows/software/view.jsp");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}