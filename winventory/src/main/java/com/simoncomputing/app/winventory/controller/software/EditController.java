package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import java.sql.Date;
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
 * Controller Servlet for editing a Software item
 * 
 * @author Megan Rigsbee
 *
 */
@WebServlet("/software/edit")
public class EditController extends BaseController {
    
    private static final long serialVersionUID = 1L;
    private String key = "";
    private static Logger log = Logger.getLogger(EditController.class);
    
    /**
     * Retrieves the software object that the user wishes to edit.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        // Use software key to access software object to be edited
        String key = request.getParameter("key");
        this.key = key;
        
        //Retrieve software object from database
        Software software = null;
        if (key != null) {
            try {
                Long long_key = Long.parseLong(key);
                software = SoftwareBo.getInstance().read(long_key);
            } catch (BoException e) {
                logError(log, e);
            }
        }
        request.setAttribute("key", key);
        request.setAttribute("software", software);
        forward(request, response, "/WEB-INF/flows/software/edit.jsp");
    }
    
    /**
     * Alters the software object based on user input and updates it in the database.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        // Get software object from database
        SoftwareBo bo = SoftwareBo.getInstance();
        Long key = Long.valueOf(this.key);
        Software software;
        
        //Attempt to update software object
        try {
            software = bo.read(key);
            
            // Get updated values (user input)
            String serialNo = (String) request.getParameter("serialNo");
            String name = (String) request.getParameter("name");
            String version = (String) request.getParameter("version");
            String licenseKey = (String) request.getParameter("licenseKey");
            String description = (String) request.getParameter("description");
            
            // For non-string values, attempt to convert to desired type (Date or double).
            Double cost = null;
            try {
                cost = (Double) Double.parseDouble((String) request.getParameter("cost"));
            }
            catch (Exception e) {
                logError(log, e);
            }
            
            Date datePurchased = null;
            try {
                datePurchased = Date.valueOf((String) request.getParameter("purchasedDate"));
            } catch (Exception e) {
                logError(log, e);
            }
            
            Date expirationDate = null;
            try {
                expirationDate = Date.valueOf((String) request.getParameter("expirationDate"));
            } catch (Exception e) {
                logError(log, e);
            }
            
            // Alter Software object's data
            software.setSerialNo(serialNo);
            software.setName(name);
            software.setVersion(version);
            software.setLicenseKey(licenseKey);
            software.setCost(cost);
            software.setPurchasedDate(datePurchased);
            software.setExpirationDate(expirationDate);
            software.setDescription(description);
            
            //Update software object in database
            bo.update(software);
        } catch (BoException e) {
            logError(log, e);
        }
        
        // Reload results page with new software object added
        ArrayList<Software> results = null;
        try {
            results = new ArrayList<Software>(SoftwareBo.getInstance().getAll());
        } catch (BoException e) {
            logError(log, e);
        }
        
        if (results != null) {
            request.setAttribute("results", results);
        }
        
        forward(request, response, "/WEB-INF/flows/software/results.jsp");
    }
}
