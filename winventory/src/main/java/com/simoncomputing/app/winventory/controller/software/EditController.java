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
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        // Use key to access software object to be edited
        String key = request.getParameter("key");
        this.key = key;
        
        Software software = null;
        if (key != null) {
            try {
                Long long_key = Long.parseLong(key);
                software = SoftwareBo.getInstance().read(long_key);
            } catch (BoException e) {
                log.error(e.getMessage());
            }
        }
        request.setAttribute("key", key);
        request.setAttribute("software", software);
        forward(request, response, "/WEB-INF/flows/software/edit.jsp");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        // Get software object from database
        SoftwareBo bo = SoftwareBo.getInstance();
        Long key = Long.valueOf(this.key);
        Software software;
        try {
            software = bo.read(key);
            
            // Get updated values (user input)
            String serialNo = (String) request.getParameter("serialNo");
            String name = (String) request.getParameter("name");
            String version = (String) request.getParameter("version");
            String licenseKey = (String) request.getParameter("licenseKey");
            String description = (String) request.getParameter("description");
            
            // For non-string values, attempt to convert to desired type. If not possible, default
            // values
            // are assigned.
            Double cost = null;
            try {
                cost = (Double) Double.parseDouble((String) request.getParameter("cost"));
            }
            // No exception should occur due to validation using javascript ('type mismatch' will
            // occur)
            catch (Exception e) {
                cost = software.getCost();
                log.error(e.getMessage());
            }
            
            Date datePurchased = null;
            try {
                datePurchased = Date.valueOf((String) request.getParameter("purchasedDate"));
            } catch (Exception e) {
                // //datePurchased = Date.valueOf("2000-01-01");
                // request.setAttribute("pDateError", "Please enter in the format: YYYY-MM-DD.");
                // forward(request, response, "/WEB-INF/flows/software/edit.jsp");
                datePurchased = software.getPurchasedDate(); // leave unchanged
                log.error(e.getMessage());
            }
            
            Date expirationDate = null;
            try {
                expirationDate = Date.valueOf((String) request.getParameter("expirationDate"));
            } catch (Exception e) {
                // //expirationDate = Date.valueOf("2000-01-01");
                // request.setAttribute("eDateError", "Please enter in the format: YYYY-MM-DD.");
                // forward(request, response, "/WEB-INF/flows/software/edit.jsp");
                expirationDate = software.getExpirationDate(); // leave unchanged
                log.error(e.getMessage());
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
            log.error(e.getMessage());
        }
        
        // Reload results page with new software object added
        ArrayList<Software> results = null;
        try {
            results = new ArrayList<Software>(SoftwareBo.getInstance().getAll());
        } catch (BoException e) {
            log.error(e.getMessage());
        }
        
        if (results != null) {
            request.setAttribute("results", results);
        }
        
        forward(request, response, "/WEB-INF/flows/software/results.jsp");
    }
}
