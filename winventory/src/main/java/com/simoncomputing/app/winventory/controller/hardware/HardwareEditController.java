package com.simoncomputing.app.winventory.controller.hardware;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.HardwareToSoftwareBo;
import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.EventType;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.HardwareToSoftware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller for the Edit Hardware page
 */
@WebServlet("/hardware/edit")
public class HardwareEditController extends BaseController {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(HardwareEditController.class);

    /**
     * Runs when the "hardware/edit" page is accessed by a link or through the
     * url
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!userHasPermission(request, "readHardware")) {
            denyPermission(request, response);
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");

        // Create a Hardware and attempt to retrieve the Hardware associated
        // with the key value (assuming there is a key parameter in the request)
        Hardware hardware = null;
        Long long_key = null;
        String errer = null;
        
        if (key != null) {
            // Cast the key to the correct type

            try {
                long_key = Long.parseLong(key);
            } catch (Exception e) {
                log.info("Invalid key for HTTP GET /hardware/view. The page will show no results."
                        + logError(log, e));
                errer = "An invalid key was entered";
                request.setAttribute("error", errer);
            }
        }
        // This will fail if there is no key (i.e. the url ends at
        // "hardware/edit")
        try {
            // Retrieve the Hardware associated with it using a BO instance
            hardware = HardwareBo.getInstance().read(long_key);
        } catch (BoException e) {
            log.info("Invalid key for HTTP GET /hardware/view. The page will show no results."
                    + logError(log, e));
            errer = "No hardware key was entered";
            request.setAttribute("error", errer);
        }

        // If the hardware is not found, this will throw an error (i.e. the url
        // contains a key that does not exist at "hardware/edit?key=").
        // Otherwise, continue
        if (hardware == null && errer == null) {
            log.info("Invalid key for HTTP GET /hardware/view. The page will show no results."
                    + log, new NullPointerException());
            errer = "No hardware exists with key " + key
                    + ". The item may have been deleted or that may be the wrong key.";
            request.setAttribute("error", errer);
        } else if (hardware != null) {

            try {
                if (hardware.getUserId() != null) {
                    User u = UserBo.getInstance().read(hardware.getUserId());

                    if (u != null) {
                        request.setAttribute("username", u.getUsername());
                    } else {
                        String error = logError(log, new NullPointerException());
                        request.setAttribute("error", "Error code: " + error);
                    }
                } else {
                    request.setAttribute("locationID", hardware.getLocationId());
                }
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }

        try {
            ArrayList<Software> softwhere = new ArrayList<Software>(HardwareToSoftwareBo
                    .getInstance().getSoftwareByHardwareId(long_key));
            String commas = "";
            for (Software sw : softwhere) {
                commas += sw.getKey() + ",";
            }

            request.setAttribute("commas", commas);
        } catch (BoException e1) {
            // do somethin crayz
        }

        // Create an ArrayList of all the valid Ref_Conditions, which will be
        // used to prevent the user from entering or choosing a non-valid
        // condition
        ArrayList<RefCondition> conditions = null;
        try {
            // Use a BO to attempt to grab all valid conditions from the
            // database
            conditions = new ArrayList<RefCondition>(RefConditionBo.getInstance().getAll());
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
        }

        // Set the conditions as an attribute for the request
        if (conditions != null) {
            request.setAttribute("conditions", conditions);
        }

        // Set the hardware as an attribute for the request
        request.setAttribute("hardware", hardware);

        // Forward the request to the "hardware/edit" page
        request.getRequestDispatcher("/WEB-INF/flows/hardware/edit.jsp").forward(request, response);

    }

    /**
     * Runs when a form is submitted from the "hardware/edit" page (occurs when
     * the changes are submitted)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // permission check
        if (!this.userHasPermission(request, "updateHardware")) {
            this.denyPermission(request, response);
            return;
        }

        // Retrieve the key parameter from the request
        String key = request.getParameter("key");
        Long long_key = null;
        if (key != null && !key.equals("")) {
            // Cast the key to the correct type
            try {
                long_key = Long.parseLong(key);
            } catch (Exception e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }

        Hardware h = new Hardware();
        ArrayList<String> errors = h.bind(request);
        h.setKey(long_key);

        Hardware original = null;
        try {
            original = HardwareBo.getInstance().read(long_key);
        } catch (BoException e2) {
            // TODO
        }

        if (errors.size() == 0) {
            request.setAttribute("success", true);

            // Use a BO to attempt to create this hardware item and place it in
            // the database
            HardwareBo bo = HardwareBo.getInstance();
            try {
                bo.update(h);
                h = bo.read(h.getKey());
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
                request.setAttribute("success", false);
            }

            if (h != null) {
                String softwareString = request.getParameter("software");
                if (softwareString != null) {
                    ArrayList<Software> softwareList = new ArrayList<Software>();
                    if (softwareString.length() > 0) {
                        String[] software = softwareString.split(",");
                        for (String str : software) {
                            try {
                                long key1 = Long.parseLong(str.trim());
                                try {
                                    Software sw = SoftwareBo.getInstance().read(key1);
                                    if (sw != null) {
                                        softwareList.add(sw);
                                    } else {
                                        errors.add("Software with ID " + key1 + " does not exist");
                                    }
                                } catch (BoException e) {
                                    errors.add("Software with ID " + key1 + " does not exist");
                                }

                            } catch (NumberFormatException e) {
                                errors.add("Error parsing software key, must be an number");

                                fail(request, response, long_key, errors);
                                return;
                            }
                        }
                    }

                    ArrayList<Software> softwhere = new ArrayList<Software>();

                    try {
                        softwhere = new ArrayList<Software>(HardwareToSoftwareBo.getInstance()
                                .getSoftwareByHardwareId(h.getKey()));
                    } catch (BoException e1) {
                        softwhere = new ArrayList<Software>();
                    }

                    for (Software sw : softwhere) {
                        try {
                            HardwareToSoftwareBo.getInstance().delete(h.getKey(), sw.getKey());
                        } catch (BoException e) {
                            // Do somethin crazy
                        }
                    }

                    for (Software sw : softwareList) {
                        HardwareToSoftware hts = new HardwareToSoftware();

                        try {
                            HardwareToSoftwareBo.getInstance().create(h.getKey(), sw.getKey());
                        } catch (BoException e) {
                            // TODO
                        }
                    }
                }

                logEvents(original, h, request);

            }

            // Create an ArrayList of all the valid Ref_Conditions, which will
            // be used to prevent the user from entering
            // or choosing a non-valid condition
            ArrayList<RefCondition> conditions = null;
            try {
                // Use a BO to attempt to grab all valid conditions from the
                // database
                conditions = new ArrayList<RefCondition>(RefConditionBo.getInstance().getAll());
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
                request.setAttribute("success", false);
            }

            // Reset the conditions as an attribute for the request
            if (conditions != null) {
                request.setAttribute("conditions", conditions);
            }

            // Redirect to the "hardware/view" page for this specific hardware
            if ((boolean) request.getAttribute("success")) {
                this.sendRedirect(request, response, "view?key=" + key + "&success=true");
            } else {
                this.sendRedirect(request, response, "view?key=" + key);
            }

        } else {
            fail(request, response, long_key, errors);
        }
    }

    private void logEvents(Hardware original, Hardware update, HttpServletRequest request) {

        if (!original.getType().equals(update.getType())) {
            String strang = "Type was changed from " + original.getType() + " to "
                    + update.getType();
            try {
                EventBo.getInstance().createSystemEvent(strang, getUserInfo(request),
                        EventType.ADMIN, update, null, null, null);
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }

        if (!original.getDescription().equals(update.getDescription())) {
            String strang = "Description was changed from " + original.getDescription() + " to "
                    + update.getDescription();
            try {
                EventBo.getInstance().createSystemEvent(strang, getUserInfo(request),
                        EventType.ADMIN, update, null, null, null);
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }

        if (!original.getCost().equals(update.getCost())) {
            String strang = "Cost was changed from " + original.getCost() + " to "
                    + update.getCost();
            try {
                EventBo.getInstance().createSystemEvent(strang, getUserInfo(request),
                        EventType.ADMIN, update, null, null, null);
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }

        if (!original.getPurchaseDate().equals(update.getPurchaseDate())) {
            String strang = "Purchased date was changed from " + original.getPurchaseDate()
                    + " to " + update.getPurchaseDate();
            try {
                EventBo.getInstance().createSystemEvent(strang, getUserInfo(request),
                        EventType.ADMIN, update, null, null, null);
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }

        if (!original.getCondition().equals(update.getCondition())) {
            String strang = "Condition was changed from " + original.getCondition() + " to "
                    + update.getCondition();
            try {
                EventBo.getInstance().createSystemEvent(strang, getUserInfo(request),
                        EventType.ADMIN, update, null, null, null);
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }

        if (!original.getSerialNo().equals(update.getSerialNo())) {
            String strang = "Serial number was changed from " + original.getSerialNo() + " to "
                    + update.getSerialNo();
            try {
                EventBo.getInstance().createSystemEvent(strang, getUserInfo(request),
                        EventType.ADMIN, update, null, null, null);
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }

        if (!original.getIsActive().equals(update.getIsActive())) {

            String strang;
            if (update.getIsActive()) {
                strang = "Hardware was activated";
            } else {
                strang = "Hardware was deactivated";
            }

            try {
                EventBo.getInstance().createSystemEvent(strang, getUserInfo(request),
                        EventType.ADMIN, update, null, null, null);
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }
        }

        User originalOwner = original.getUser();
        User updatedOwner = update.getUser();

        Location originalLoc = original.getLocation();
        Location updatedLoc = update.getLocation();

        // went from user to storage
        if (originalOwner != null && updatedOwner == null && originalLoc == null
                && updatedLoc != null) {

            String strang = "Hardware went from user " + originalOwner.getUsername() + "("
                    + originalOwner.getKey() + ") to location " + updatedLoc.getDescription() + "("
                    + updatedLoc.getKey() + ")";
            try {
                EventBo.getInstance().createSystemEvent(strang, getUserInfo(request),
                        EventType.ADMIN, update, updatedLoc, null, originalOwner);
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }

        // went from storage to user
        } else if (originalOwner == null && updatedOwner != null && originalLoc != null
                && updatedLoc == null) {

            String strang = "Hardware went from location " + originalLoc.getDescription() + "("
                    + originalLoc.getKey() + ") to user " + updatedOwner.getUsername() + "("
                    + updatedOwner.getKey() + ")";
            try {
                EventBo.getInstance().createSystemEvent(strang, getUserInfo(request),
                        EventType.ADMIN, update, originalLoc, null, updatedOwner);
            } catch (BoException e) {
                String error = logError(log, e);
                request.setAttribute("error", "Error code: " + error);
            }

        }

    }

    private void fail(HttpServletRequest request, HttpServletResponse response, Long key,
            ArrayList<String> errors) throws ServletException, IOException {
        ArrayList<RefCondition> conditions = null;
        try {
            // Use a BO to attempt to grab all valid conditions from the
            // database
            conditions = new ArrayList<RefCondition>(RefConditionBo.getInstance().getAll());
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
            request.setAttribute("success", false);
        }

        // Reset the conditions as an attribute for the request
        if (conditions != null) {
            request.setAttribute("conditions", conditions);
        }

        Hardware hardware = null;
        try {
            hardware = HardwareBo.getInstance().read(key);
        } catch (BoException e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
            request.setAttribute("success", false);
        }

        try {
            if (hardware.getUserId() != null) {
                User u = UserBo.getInstance().read(hardware.getUserId());

                if (u != null) {
                    request.setAttribute("username", u.getUsername());
                } else {
                    String error = logError(log, new NullPointerException());
                    request.setAttribute("error", "Error code: " + error);
                }
            } else {
                request.setAttribute("locationID", hardware.getLocationId());
            }
        } catch (Exception e) {
            String error = logError(log, e);
            request.setAttribute("error", "Error code: " + error);
            request.setAttribute("success", false);
        }

        try {
            ArrayList<Software> softwhere = new ArrayList<Software>(HardwareToSoftwareBo
                    .getInstance().getSoftwareByHardwareId(key));
            String commas = "";
            for (Software sw : softwhere) {
                commas += sw.getKey() + ", ";
            }

            request.setAttribute("commas", commas);
        } catch (BoException e1) {
            // do somethin crayz
        }

        request.setAttribute("hardware", hardware);
        request.setAttribute("success", false);
        request.setAttribute("errors", errors);
        // Forward the request to the "hardware/insert" page to allow the
        // user
        // to enter more items
        request.getRequestDispatcher("/WEB-INF/flows/hardware/edit.jsp").forward(request, response);

    }

}