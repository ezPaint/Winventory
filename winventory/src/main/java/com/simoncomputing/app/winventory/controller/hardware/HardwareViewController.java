package com.simoncomputing.app.winventory.controller.hardware;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

@WebServlet("/hardware/view")
public class HardwareViewController extends BaseController {
    private static final long serialVersionUID = 1L;
    
    private Logger log = Logger.getLogger(HardwareViewController.class); 

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String key = request.getParameter("key");

        // gets hardware for key
        Hardware hardware = null;
        if (key != null) {
            try {
                Long long_key = Long.parseLong(key);
                hardware = HardwareBo.getInstance().read(long_key);

            } catch (BoException e) {
                request.setAttribute("error", e.getMessage());
                log.error(e.getMessage());
            }
        }

        // db didn't work out
        if (hardware == null) {
            request.setAttribute("error",
                    "The key is not valid or there is no hardware with that key");
            log.error("The key is not valid or there is no hardware with that key");
        } else {
            // get owner
            if (hardware.getUserId() != null) {
                try {
                    User owner = UserBo.getInstance().read((long) hardware.getUserId());
                    request.setAttribute("owner", owner);
                } catch (BoException e) {
                    request.setAttribute("error", e.getMessage());
                    log.error(e.getMessage());
                }
            // get location
            } else if (hardware.getLocationId() != null) {
                try {
                    Location location = LocationBo.getInstance().read((long) hardware.getLocationId());
                            request.setAttribute("locaiton", location);
                } catch (BoException e) {
                    request.setAttribute("error", e.getMessage());
                    log.error(e.getMessage());
                }
            }
        }

        // forward to jsp
        request.setAttribute("hardware", hardware);
        request.getRequestDispatcher("/WEB-INF/flows/hardware/view.jsp").forward(request, response);

    }

    // handles delete functionality, everytime a post is sent to this page and the 
    // user has permission the hardware is deleted
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (this.requirePermission(request, response, "deleteHardware")) {
            return;
        }
        
        String key = request.getParameter("key");
        HardwareBo bo = HardwareBo.getInstance();
        Long long_key = null;
        if (key != null) {
            long_key = Long.parseLong(key);
        }
        
        try {
            bo.delete(long_key);
        } catch (BoException e) {
            e.printStackTrace();
        }
        
        this.sendRedirect(request, response, "results");
                
    }

}