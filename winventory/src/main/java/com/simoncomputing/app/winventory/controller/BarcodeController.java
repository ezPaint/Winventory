package com.simoncomputing.app.winventory.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.authentication.EmailService;
import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.EventType;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.formbean.BarcodeBean;
import com.simoncomputing.app.winventory.formbean.UserInfoBean;
import com.simoncomputing.app.winventory.util.Barcoder;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * The following is a controller to handle the Barcode page in the
 * Winventory site.  The doGet will simply load the page while the doPost
 * will handle the processing of taking in and parsing the barcode as well as
 * input determining when to begin a new search or update the database
 * @author seamus.lowry
 */

@WebServlet("/barcodes/barcode")
public class BarcodeController extends BaseController {
    private static final long serialVersionUID = 1L;
    private BarcodeBean bean = new BarcodeBean();
    static Logger logger = Logger.getLogger(BarcodeController.class);
    static EmailService emailer;
	private static UserBo ub = UserBo.getInstance();			// User business object
	private static HardwareBo hb = HardwareBo.getInstance();	// Hardware business object
	private static LocationBo lb = LocationBo.getInstance();	// Location business object
	
	/**
	 * simply load the jsp on a GET
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.getRequestDispatcher("/WEB-INF/flows/barcodes/barcode.jsp").forward(request,
                response);
    }

    /**
     * handle barcode/username input
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	bean.bind(request);
    	String barcode = bean.getBarcode();
    	boolean update = bean.shouldUpdate();
    	boolean clear = bean.shouldClear();
    	boolean valid = bean.isValidInput();
		if (update) {	// if you should update the database
			List<Long> pkList = bean.getRemovalIds();	// list of pks to dissociate
			ArrayList<Hardware> hwList = bean.getHardwareList();
				// the list of hardware currently being worked with
			logger.debug("pkList is " + pkList);
			try {
				if (bean.getLocation()==null)
					bean.setHardwareIds(updateDatabase(pkList, hwList, bean.getUser(),getUserInfo(request)));	// update the database
				else
					bean.setHardwareIds(updateDatabase(pkList, hwList, bean.getLocation(),getUserInfo(request)));	// update the database
				logger.trace("updating database");
				request.setAttribute("success", "Successfully updated.");
			} catch (BoException bo) {
				logger.error("Could not update database");
				request.setAttribute("error", "Database not updated.  Errors occurred.");
			}
		} else if (clear){	// if want to clear previous input (start a new search)
	    	// simply do a get
			request.setAttribute("success", "Successfully cleared");
			request.getRequestDispatcher("/WEB-INF/flows/barcodes/barcode.jsp").forward(request,
	                response);
			logger.trace("clearing barcode page to start new search");
			return;
		} else if (!valid){	// if input is not valid
			// determine and set correct error message; store barcode
			request.setAttribute("error", Barcoder.barcodeErrorMessage(barcode));
			request.setAttribute("barcode", barcode);
			logger.trace("Invalid barcode input entered");
		} else{
			/*
			 * a precondition for calling the processBarcode() method is
			 * that the input be valid (as determined by the BarcodeBean class)
			 */
			request.setAttribute("error", bean.processBarcode());
		}
		
		/*
		 * assign user and location attributes
		 * need to be set for checking if submitted without either
		 */
		
		request.setAttribute("user", bean.getUser());
		request.setAttribute("location", bean.getLocation());

		
		/*
		 * if the post was submitted without a user or location, and we're not clearing, return error 
		 */
		
		if (request.getAttribute("error")==null && !clear && request.getAttribute("user")==null && request.getAttribute("location")==null){
    		bean.setHardwareIds(new ArrayList<Long>());
    		request.setAttribute("error","You must enter an Owner or Location first");
    		logger.error("Attempted to add hardware before a user or location.");
    	}
		
		/*
		 * set hardware, hardwareKeys, and removeHw appropriately
		 */
		
		request.setAttribute("hardware", bean.getHardwareList());
		request.setAttribute("hardwareKeys", bean.getHardwareIds());
		request.setAttribute("removalKeys", bean.getRemovalIds());
		
    	request.getRequestDispatcher("/WEB-INF/flows/barcodes/barcode.jsp").forward(request,
                response);
    }
    
    /**
     * update the database to reflect changes made on the site; notify users of hardware changes if applicable
     * @param pkList the list of pks that should be removed from the current user or location
     * @param hwList the list of hw on the session, includes those both added and removed
     * @param user the user who's hardware should be modified
     * @return ids the list of pks for hardware that is still in associated with the location
     * @throws BoException when cannot get valid emails to notify users
     */
    
    private ArrayList<Long> updateDatabase(List<Long> pkList, ArrayList<Hardware> hwList, User user, UserInfoBean need) throws BoException {
    	Map<String,String> recipients = new HashMap<String,String>();	// map of recipients to their messages
		User oldUser = null;							//temporary user object for convenience
		Long oldId = null;
		Long newId = null;
    	for (Long pk: pkList) {
			try {
				Hardware rem = hb.read(pk);		// get the hardware with correct pk
				newId=user.getKey();
				oldId=rem.getUserId();
				hwList.remove(rem);								// remove it from the overall hardware list
				if (!(rem.getUserId()==null)){	// if userid is not null
					oldUser = ub.read(rem.getUserId().longValue());	// get correct user
					String email = oldUser.getEmail();						// get that user's email
					/*
					 * add onto email message apprporiately based on whether one exists currently or not
					 * tell the user what of their hardware is no longer associated with them
					 */
					if (recipients.containsKey(email)){
						String newMessage = recipients.get(email)+"\n"+rem.getKey() + "(no longer yours)\n";
						recipients.put(email, newMessage);
					}
					else{
						recipients.put(email, "Hello " + oldUser.getFirstName() + ",\n\n"
								+ "This email is to update you on the ownership of your Hardware "
								+ "with serial number(s):\n\n" + rem.getKey() + " (no longer yours)\n");
					}
				}
				rem.setUserId(null);	// null the userid of the hardware removed no matter what
				hb.update(rem);	//update the hw in the database
				// log event of hardware dissociation
				if (newId.equals(oldId))
					EventBo.getInstance().createSystemEvent("Hardware with serial no. " + rem.getSerialNo() + " was dissociated from " + oldUser.getUsername(), need,  EventType.ADMIN, rem,null,null,oldUser);
			} catch (NumberFormatException e) {
				logger.error("Cannot dissociate hardware with pk " + pk + " because pk is not a number");
			} catch (BoException e) {
				logger.error("Cannot get Hardware with pk to remove " + pk);
			}
		}
    	/*
    	 * iterate through the list of hw (with removed values removed by above code)
    	 * update user or location id's to represent their values
    	 */
		ArrayList<Long> ids = new ArrayList<Long>();
		for (Hardware hw: hwList) {
			/*
			 * append to email notifying user of change if user exists and is different (i.e. there IS a change to notify)
			 */
			oldId = hw.getUserId();		//id of previous owner,null if none
			newId = user.getKey();			//id of new owner, cannot be null
			String email;						//email address to send to
			ids.add(hw.getKey());
			if(!newId.equals(oldId)){			//if the owner changes
				if (oldId!=null){				//AND there was a previous owner
					/*
					 * get the old user and their email address
					 * and tell the old owner that the hardware's been removed
					 * provide an introductory statement in the email message
					 * if there isn't one already
					 */
					
					oldUser = ub.read(oldId);
					email = oldUser.getEmail();
					if (recipients.containsKey(email)){
						String newMessage = recipients.get(email)+"\n"+hw.getKey() + "(no longer yours)\n";
						recipients.put(email, newMessage);
					}
					else{
						recipients.put(email, "Hello " + oldUser.getFirstName() + ",\n "
								+ "This email is to update you on the ownership of your Hardware"
								+ " with serial number(s):\n\n" + hw.getKey() + "(no longer yours)\n");
					}
					//log event of hardware dissociation
					EventBo.getInstance().createSystemEvent("Hardware with serial number " + hw.getSerialNo() + " was dissociated from " + oldUser.getUsername(), need,  EventType.ADMIN, hw,null,null,oldUser);
				}
				
				/*
				 * get the new user and their email address
				 * and tell them that they've been assigned that hardware
				 * provide and introductory statement in the email message if there
				 * isn't one already 
				 */
				
				oldUser = ub.read(newId);
				email = oldUser.getEmail();
				if (recipients.containsKey(email)){
					String newMessage = recipients.get(email)+"\n"+hw.getKey() + "(newly yours)\n";
					recipients.put(email, newMessage);
				}
				else{
					recipients.put(email, "Hello " + oldUser.getFirstName() + ",\n "
							+ "This email is to update you on the ownership of your Hardware"
							+ " with serial number(s):\n\n" + hw.getKey() + "(newly yours)\n");
				}
			}
			hw.setUserId(newId);	// always update userid
			/*
			 * try updating hardware. log error if unable to 
			 */
			
			try {
				hb.update(hw);
				//log event of hardware association
				if (!newId.equals(oldId))
					EventBo.getInstance().createSystemEvent("Hardware with serial number " + hw.getSerialNo() + " was associated with " + ub.read(oldId).getUsername(), need,  EventType.ADMIN, hw,null,null,user);
			} catch (BoException e) {
				logger.error("Could not update Hardware with pk " + hw.getKey());
			}
		}
		/*
		 * send all the emails
		 */
		for(String s: recipients.keySet()){
			recipients.put(s, recipients.get(s) + "\n\n\n\nSincerely,\nThe Winventory");
			try{
				emailer = new EmailService();
				emailer.setSmtp();
				emailer.addTo(s);
				emailer.setFrom("The Winventory <" + emailer.getSmtp().getAuthUserName() + ">");
				emailer.setSubject("Hardware reassignment");
				emailer.setMessage(recipients.get(s));
				emailer.sendEmail();
				logger.trace("sending email to "+ s + " regarding hardware reassignment");
			} catch (Exception e){
				e.printStackTrace();
				logger.error("failed to send email to " + s +" regarding hardware reassignment");
			}
		}
		return ids;
	}

    /**
     * update the database to reflect changes made on the site; notify users of hardware changes if applicable
     * @param pkList the list of pks that should be removed from the current user or location
     * @param hwList the list of hw on the session, includes those both added and removed
     * @param location the location object on the session
     * @return ids the list of pks for hardware that is still in associated with the location
     * @throws BoException when cannot get valid emails to notify users
     */
    
    private ArrayList<Long> updateDatabase(List<Long> pkList, ArrayList<Hardware> hwList, Location location, UserInfoBean need) throws BoException {
    	Long oldId = null;
    	Long newId = location.getKey();
    	for (Long pk: pkList) {
			try {
				Hardware rem = hb.read(pk);		// get the hardware with correct pk
				oldId = rem.getLocationId();
				hwList.remove(rem);								// remove it from the overall hardware list
				rem.setLocationId(null);	// null the locationId of the hardware removed no matter what
				hb.update(rem);	//update the hw in the database
				//log hardware dissociation event
				if(newId.equals(oldId))
					logger.debug("newId " + newId + " oldId " + oldId);
					EventBo.getInstance().createSystemEvent("Hardware with serial no " + rem.getSerialNo() + "was dissociated from Location with address " + lb.read(oldId).getAddress(), need,  EventType.ADMIN, rem,lb.read(oldId),null,null);
			} catch (NumberFormatException e) {
				logger.error("Cannot dissociate hardware with pk " + pk + " because pk is not a number");
			} catch (BoException e) {
				logger.error("Cannot get Hardware with pk to remove " + pk);
			}
		}
    	/*
    	 * iterate through the list of hw (with removed values removed by above code)
    	 * update user or location id's to represent their values
    	 */
		ArrayList<Long> ids = new ArrayList<Long>();
		for (Hardware hw: hwList) {
			oldId = hw.getLocationId();
			hw.setLocationId(location.getKey());	// always update userid
			ids.add(hw.getKey());
			/*
			 * try updating hardware. log error if unable to 
			 */
			
			try {
				hb.update(hw);
				
				/*
				 * if the ids are different, log a dissociation event if there is an old id
				 * always log an association event
				 */
				
				if(!newId.equals(oldId)){
					if(oldId != null){
						EventBo.getInstance().createSystemEvent("Hardware with serial number " + hw.getSerialNo() + " was dissociated from Location with adress " + lb.read(oldId).getAddress(), need,  EventType.ADMIN, hw,lb.read(oldId),null,null);
					}
					EventBo.getInstance().createSystemEvent("Hardware with serial number " + hw.getSerialNo() + " was associated with Location with adress " + lb.read(oldId).getAddress(), need,  EventType.ADMIN, hw,location,null,null);
				}
			} catch (BoException e) {
				logger.error("Could not update Hardware with pk " + hw.getKey());
			}
		}
		return ids;
	}
}

