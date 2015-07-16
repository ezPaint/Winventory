package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.HardwareToSoftwareBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.EventType;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.HardwareToSoftware;
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
	private String name = ""; // for
								// redirecting

	/**
	 * Retrieves the software object that the user wishes to edit.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (userHasPermission(request, "updateSoftware")) {
			// Use software key to access software object to be edited
			String key = request.getParameter("key");
			this.key = key;

			// Retrieve software object from database
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
		} else {
			denyPermission(request, response);
			return;
		}
	}

	/**
	 * Alters the software object based on user input and updates it in the
	 * database.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get software object from database
		SoftwareBo bo = SoftwareBo.getInstance();
		Long key = Long.valueOf(this.key);
		Software software;

		// Attempt to update software object in database
		try {
			software = bo.read(key);

			// Save old info (for event log)
			String oldName = software.getName();
			String oldSerial = software.getSerialNo();
			String oldVersion = software.getVersion();
			String oldLicense = software.getLicenseKey();
			String oldDescription = software.getDescription();
			Double oldCost = software.getCost();
			Date oldPurchase = software.getPurchasedDate();
			Date oldExpiration = software.getExpirationDate();
			boolean oldIsActive = software.getIsActive();
			String oldAssociated = software.associatedHardware();

			// Get updated values (user input)
			String serialNo = (String) request.getParameter("serialNo");
			String name = (String) request.getParameter("name");
			this.name = name; // for redirecting
			String version = (String) request.getParameter("version");
			String licenseKey = (String) request.getParameter("licenseKey");
			String description = (String) request.getParameter("description");
			boolean isActive = request.getParameter("isActive") != null;

			// Get Associated Hardware
			// -------------------------------------------------------------
			// Gather user input
			String hardwareString = request.getParameter("associate");
			if (hardwareString != null) {
				ArrayList<Hardware> hardwareList = new ArrayList<Hardware>();
				if (hardwareString.length() > 0) {
					String[] hwStr = hardwareString.split(",");

					// Find hardware based on key entered by user and add it to
					// 'hardwareList'
					for (String str : hwStr) {
						try {
							long key1 = Long.parseLong(str.trim());
							try {
								Hardware hw = HardwareBo.getInstance().read(key1);
								if (hw != null) {
									hardwareList.add(hw);
								}
							} catch (BoException e) {
								logError(log, e);
							}

						} catch (NumberFormatException e) {
							logError(log, e);
						}
					}
				}

				// Get all hardware currently associated with this software
				ArrayList<Hardware> hardware = new ArrayList<Hardware>();
				try {
					hardware = new ArrayList<Hardware>(HardwareToSoftwareBo.getInstance()
							.getHardwareBySoftwareId(software.getKey()));
				} catch (BoException e) {
					logError(log, e);
				}

				// Delete old list
				for (Hardware hw : hardware) {
					try {
						HardwareToSoftwareBo.getInstance().delete(hw.getKey(), software.getKey());
					} catch (BoException e) {
						logError(log, e);
					}
				}

				// Associate new list of hardware to database
				for (Hardware hw : hardwareList) {
					HardwareToSoftware hts = new HardwareToSoftware();

					hts.setHardwareId(hw.getKey());
					hts.setSoftwareId(software.getKey());
					try {
						HardwareToSoftwareBo.getInstance().create(hw.getKey(), software.getKey());
					} catch (BoException e) {
						logError(log, e);
					}
				}
			}

			// For non-string values, attempt to convert to desired type
			// (Date or double).
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

			// Alter Software object's data
			software.setSerialNo(serialNo);
			software.setName(name);
			software.setVersion(version);
			software.setLicenseKey(licenseKey);
			software.setCost(cost);
			software.setPurchasedDate(datePurchased);
			software.setExpirationDate(expirationDate);
			software.setDescription(description);
			software.setIsActive(isActive);

			// Update software object in database
			bo.update(software);

			// Get changes for event log
			ArrayList<String> changes = new ArrayList<String>();
			if (!oldName.equals(name)) {
				changes.add("Name");
				changes.add(oldName);
				changes.add(name);
			}
			if (!oldSerial.equals(serialNo)) {
				changes.add("Serial Number");
				changes.add(oldSerial);
				changes.add(serialNo);
			}
			if (!oldLicense.equals(licenseKey)) {
				changes.add("License Key");
				changes.add(oldLicense);
				changes.add(licenseKey);
			}
			if (!oldVersion.equals(version)) {
				changes.add("Version");
				changes.add(oldVersion);
				changes.add(version);
			}
			if (!oldDescription.equals(description)) {
				changes.add("Description");
				changes.add(oldDescription);
				changes.add(description);
			}
			if (!oldCost.equals(cost)) {
				changes.add("cost");
				changes.add(oldCost.toString());
				changes.add(cost.toString());
			}
			if (!oldPurchase.equals(datePurchased)) {
				changes.add("Purchased Date");
				changes.add(oldPurchase.toString());
				changes.add(datePurchased.toString());
			}
			if (!oldExpiration.equals(expirationDate)) {
				changes.add("Expiration Date");
				changes.add(oldExpiration.toString());
				changes.add(expirationDate.toString());
			}
			if (oldIsActive != isActive) {
				changes.add("Is Active");
				changes.add("" + oldIsActive);
				changes.add("" + isActive);
			}
			String hwUpdate = "";
			if (!oldAssociated.equals(hardwareString)) {
				if (hardwareString.equals("")) {
					hwUpdate = "This software no longer has any associated hardwares.";
				} else {
					hwUpdate = "Associated hardware was updated to " + hardwareString + ".";
				}
			}

			// Get changes into a string for event log
			String message = "";
			for (int i = 0; i < changes.size(); i += 3) {
				message += "<br>" + changes.get(i) + " was changed from " + changes.get(i + 1)
						+ " to " + changes.get(i + 2) + ".";
			}

			// Record event
			EventBo.getInstance().createSystemEvent(
					name + " was updated. <br>" + message + hwUpdate, getUserInfo(request),
					EventType.SYSTEM, null, null, software, null);
		} catch (BoException e) {
			logError(log, e);
		}

		sendRedirect(request, response, "/winventory/software/view?key=" + key + "&success=true");
	}
}