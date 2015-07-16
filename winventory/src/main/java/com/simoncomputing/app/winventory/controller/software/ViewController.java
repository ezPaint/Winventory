package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.HardwareToSoftwareBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.HardwareToSoftware;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.Barcoder;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Single software item view controller.
 * 
 * @author megan.rigsbee
 *
 */
@WebServlet("/software/view")
public class ViewController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ViewController.class);
    private String key;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (userHasPermission(request, "readSoftware")) {
            // Use the key the user clicked on to retrieve the corresponding
            // software object from
            // the database
            String key = request.getParameter("key");

            // The events associated with software;
            // initialized to a new list to avoid null pointer exception
            List<Event> events = new ArrayList<Event>();

            Software software = null;
            if (key != null) {
                // get and assign barcode using Barcoder class
                try {
                    Long long_key = Long.parseLong(key);
                    software = SoftwareBo.getInstance().read(long_key); // get
                                                                        // software
                                                                        // obj
                                                                        // from
                                                                        // database
                } catch (BoException e) {
                    logError(log, e);
                }

                // if there was no software found
                if (software == null) {
                    log.info("Invalid key for HTTP GET /software/view. Redirecting to software/results.");
                    String error = "No software exists with key " + key
                            + ". The software may have been deleted or "
                            + "that may be the wrong key.";
                    response.sendRedirect(request.getContextPath() + "/software/results?error="
                            + error);
                    return;
                }

                request.setAttribute("barcode", Barcoder.getBarcode(software));

                EventBo eBo = EventBo.getInstance();
                try {
                    if (software.getKey() != null)
                    {
                        events = eBo.getListBySoftwareId(software.getKey());
                    }
                } catch (BoException e) {
                    logError(log, e);
                }
            }

            request.setAttribute("key", key);
            request.setAttribute("software", software);
            
            
            request.setAttribute("events", events); 
            this.key = key;

            request.setAttribute("delete", request.getParameter("delete")); // delete
                                                                            // was
                                                                            // requested
            request.setAttribute("success", request.getParameter("success")); // software
                                                                              // was
                                                                              // updated
            
            //Find associated hardware
            ArrayList<Hardware> hardware = null;
            try {
                 hardware = new ArrayList<Hardware>(
                        HardwareToSoftwareBo.getInstance().getHardwareBySoftwareId(software.getKey()));
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
            
            request.setAttribute("hardware", hardware);

            forward(request, response, "/WEB-INF/flows/software/view.jsp");
        } else {
            denyPermission(request, response);
            return;
        }

    }

    /**
     * This method is called when the user clicks the 'delete' button on an
     * item's page.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}