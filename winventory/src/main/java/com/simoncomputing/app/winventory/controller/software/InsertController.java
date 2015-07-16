package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.EventType;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.BoException;

import org.apache.log4j.Logger;

/**
 * Controller Servlet for adding Software
 * 
 * @author Megan Rigsbee
 *
 */
@WebServlet("/software/insert")
public class InsertController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(InsertController.class);
    private String name = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (userHasPermission(request, "createSoftware")) {
            forward(request, response, "/WEB-INF/flows/software/insert.jsp");
        } else {
            denyPermission(request, response);
            return;
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve software's info that the user entered
        String serialNo = (String) request.getParameter("serialNo");
        String name = (String) request.getParameter("name");
        this.name = name;
        String version = (String) request.getParameter("version");
        String licenseKey = (String) request.getParameter("licenseKey");
        boolean isActive = request.getParameter("isActive") != null;

        // empty string if user doesn't enter anything
        String description = (String) request.getParameter("description");

        // For non-string values, attempt to convert to desired type (Date or
        // double).
        Double cost = null;
        try {
            cost = (Double) Double.parseDouble((String) request.getParameter("cost"));
        } catch (Exception e) {
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
        
        

        // Create Software object
        Software software = new Software();
        software.setSerialNo(serialNo);
        software.setName(name);
        software.setVersion(version);
        software.setLicenseKey(licenseKey);
        software.setCost(cost);
        software.setPurchasedDate(datePurchased);
        software.setExpirationDate(expirationDate);
        software.setDescription(description);
        software.setIsActive(isActive);

        // Add Software Object to database
        SoftwareBo softwareBo = SoftwareBo.getInstance();
        try {
            softwareBo.create(software);
            Event event = EventBo.getInstance().createSystemEvent(
                    software.getName() + " was created.", getUserInfo(request), EventType.SYSTEM,
                    null, null, software, null);

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

        request.setAttribute("success", true);
        forward(request, response, "/WEB-INF/flows/software/results.jsp");

    }
}
