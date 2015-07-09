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
 * Controller Servlet for adding Software
 * @author Megan Rigsbee
 *
 */
@WebServlet("/software/insert")
public class InsertController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(InsertController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        forward(request, response, "/WEB-INF/flows/software/insert.jsp");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get info that the user entered
        String serialNo = (String) request.getParameter("serialNo");
        String name = (String) request.getParameter("name");
        String version = (String) request.getParameter("version");
        String licenseKey = (String) request.getParameter("licenseKey");
        String description = (String) request.getParameter("description");
               
        //For non-string values, attempt to convert to desired type. If not possible, default values
        //are assigned.
        Double cost = null;
        try{
            cost = (Double) Double.parseDouble((String) request.getParameter("cost"));
        } catch (Exception e){
            cost = 0d;
            log.error(e.getMessage());
        }
        
        Date datePurchased = null;
        try{
            datePurchased = Date.valueOf((String) request.getParameter("purchasedDate"));
        } catch (Exception e){
            datePurchased = Date.valueOf("2000-01-01");
            log.error(e.getMessage());
        }
        
        Date expirationDate = null;
        try{
            expirationDate = Date.valueOf((String) request.getParameter("expirationDate"));
        } catch (Exception e){
            expirationDate = Date.valueOf("2000-01-01");
            log.error(e.getMessage());
        }
        
       //Create Software object
       Software software = new Software();
       software.setSerialNo(serialNo);
       software.setName(name);
       software.setVersion(version);        
       software.setLicenseKey(licenseKey);
       software.setCost(cost);
       software.setPurchasedDate(datePurchased);
       software.setExpirationDate(expirationDate);
       software.setDescription(description);
        
       //Add Software Object to database and inform user of success/failure
       SoftwareBo softwareBo = SoftwareBo.getInstance();
       try {
           softwareBo.create(software);
       } catch (BoException e) {
            log.error(e.getMessage());
       }
       
       //Reload results page with new software object added
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
