package com.simoncomputing.app.winventory.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
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
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * The following is a controller to handle the Barcode page in the
 * Winventory site.  The doGet will simply load the page while the doPost
 * will handle the processing of taking in and parsing the barcode as well as
 * input determining when to begin a new search or update the database
 * @author seamus.lowry
 */

@WebServlet("/barcodes/barcode")
public class BarcodeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
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
    	request.getSession().setAttribute("ub", ub);		// give userbo to session
    	request.getSession().setAttribute("lb", lb);		// give locationbo to session
		String barcode = request.getParameter("barcode");	// get the barcode given
		// check hidden inputs for updating database or clearing previous data
		boolean update = Boolean.parseBoolean(request.getParameter("toSubmit"));
		boolean clear = Boolean.parseBoolean(request.getParameter("clear"));
		boolean valid = isValidInput(barcode);	// check if valid input
		if (update) {	// if you should update the database
			String[] pkList = request.getParameterValues("removeHw");	// list of pks to dissociate
			if (pkList==null){
				pkList=new String[0];	// if no list given, make it an empty list
				// avoids NullPointerExceptions
			}
			@SuppressWarnings("unchecked")
			ArrayList<Hardware> hwList = (ArrayList<Hardware>) request.getSession().getAttribute("barcodeHardware");
				// the list of hardware currently being worked with
			String modifyCode;	// where the barcode for what must be modified will be stored
			User owner = (User)request.getSession().getAttribute("barcodeUser");
				// the user stored on the Session, can be null
			Location loc = (Location)request.getSession().getAttribute("barcodeLocation");
				// the location stored on the Session, can be null
			if(owner==null){	// if working with Location
				modifyCode="004"+padZeroes(loc.getKey().toString());	// make barcode for Location
			} else {			// if working with User
				modifyCode="001"+padZeroes(owner.getKey().toString());	// make barcode for User
			}
			modifyCode+="3";	//TODO make valid checksum; random number right now
			logger.debug("pkList is " + Arrays.asList(pkList));
			try {
				updateDatabase(pkList, hwList, modifyCode);	// update the database
	    		logger.trace("updating database");
			} catch (BoException bo) {
				logger.error("Could not update database");
				request.setAttribute("error", "Database not updated.  Errors occurred.");
			}
		} else if (clear){	// if want to clear previous input (start a new search)
			// clear User, Location, and Hardware list from the Session
			request.removeAttribute("barcodeUser");
			request.removeAttribute("barcodeHardware");
			request.removeAttribute("barcodeLocation");
			logger.trace("clearing barcode page to start new search");
		} else if (!valid){	// if input is not valid
			// determine and set correct error message; store barcode
			request.setAttribute("error", barcodeErrorMessage(barcode));
			request.setAttribute("barcode", barcode);
			logger.trace("Invalid barcode input entered");
		} else if(barcode != null){
			long pk = -1;	// store invalid pk before initialized
			String tableIdentifier = "-1";	// store invalid  table identifier before initialized
			try{
				tableIdentifier = parseTableIdentifier(barcode);	// get actual table identifier
				pk = parsePk(barcode);	// get actual pk
			}
			/*
			 * catch here as formality.  Valid input tests for these errors
			 */
			catch (NumberFormatException nfe) {
				logger.error("Could not get a pk from " + barcode);
			}
			List<Hardware> hardware;	// the list of hardware to pass back to view
			try{
    			switch(tableIdentifier){	// switch based on table identifier
    			case "1":	// a User barcode
    				logger.trace("preloading based on user " + pk);
    				request.getSession().setAttribute("barcodeUser", ub.read(pk));	// assign correct user object to session
    				request.getSession().setAttribute("barcodeLocation", null);	// ensure no location object on session
    				hardware = hb.getListByUserId(pk);					// get correct hardware list based on User
    				System.out.println(hardware);
    				request.getSession().setAttribute("barcodeHardware", (ArrayList<Hardware>)hardware);// put that list on the session
    				break;
    			case "2":	// a Hardware barcode
    				logger.trace("adding to hardware list in barcode page");
    				@SuppressWarnings("unchecked")
					ArrayList<Hardware> hwList = (ArrayList<Hardware>) request.getSession().getAttribute("barcodeHardware");
    					// get the hardware list from the session
    				if (hwList==null){
    					hwList = new ArrayList<Hardware>();	// initialize a list if there is none yet
    				}
    				Hardware h = hb.read(pk);				// get the hardware referenced by the barcode
    				if (!hwList.contains(h))				// check if that reference is already in the list
    					hwList.add(h);						// add the piece of hardware if not already present
    				else {
    					request.setAttribute("error", "That Hardware is already in the list");
    						// if the hardware is already in the list, provide an error message saying so
    					logger.trace("attempted to add duplicate hardware in barcode page");
    				}
    				request.getSession().setAttribute("barcodeHardware", hwList);
    				String logStr = "Hardware keys in hwList (barcode page):";
    				for (Hardware hard: hwList){
    					logStr+= hard.getKey() + ", ";
    				}
    				logger.trace(logStr.substring(0,logStr.length()-2));
    				break;
    			case "4":	// if a Location barcode
    				logger.trace("preloading based on location " + pk);
    				request.getSession().setAttribute("barcodeUser", null);	// ensure no user on the session
    				request.getSession().setAttribute("barcodeLocation", lb.read(pk));	// put correct location on the session
    				hardware = hb.getListByLocationId(pk);			// get the appropriate hardware list
    				request.getSession().setAttribute("barcodeHardware", hardware);	// put that list on the session
    				break;
    			default:	// entered a username
    				logger.trace("preloading based on username " + barcode);
    				User user = ub.getUserByUsername(barcode);		// get user
    				request.getSession().setAttribute("barcodeLocation", null);	// ensure no location on session
    				request.getSession().setAttribute("barcodeUser", user);	// set user attribute appropriately
    				request.getSession().setAttribute("barcodeHardware", hb.getListByUserId(user.getKey()));	// get correct hardware list
    				break;
    			}
			} catch (BoException be) {
				logger.error("Could not get an object from table id:" + tableIdentifier + " with pk " + pk);
			}
		}
		
		/*
		 * if the post was submitted without a user or location, the input was valid, and we're not clearing, return error 
		 */
		
		if (request.getAttribute("error")==null && !clear && request.getSession().getAttribute("barcodeUser")==null && request.getSession().getAttribute("barcodeLocation")==null){
    		request.getSession().setAttribute("barcodeHardware",null);
    		request.setAttribute("error","You must enter an Owner or Location first");
    		logger.error("Attempted to add hardware before a user or location.");
    	}
		
    	request.getRequestDispatcher("/WEB-INF/flows/barcodes/barcode.jsp").forward(request,
                response);
    }
    
    /**
     * update the database to reflect changes made on the site; notify users of hardware changes if applicable
     * @param pkList the list of pks that should be removed from the current user or location
     * @param hwList the list of hw on the session, includes those both added and removed
     * @param barcode the barcode representing the location or user on session (used to determine correct bo)
     * @throws BoException when cannot get valid emails to notify users
     */
    
    private void updateDatabase(String[] pkList, ArrayList<Hardware> hwList, String barcode) throws BoException {
    	Map<String,String> recipients = new HashMap<String,String>();	// map of recipients to their messages
    	String tableId = parseTableIdentifier(barcode);					// get the table identifier
		Long updatePk = parsePk(barcode);								// get the pk of the identifier
    	for (String pk: pkList) {
			try {
				Hardware rem = hb.read(Long.parseLong(pk));		// get the hardware with correct pk
				hwList.remove(rem);								// remove it from the overall hardware list
				if (tableId.equals("1")) {						// if working with a user
					if (!(rem.getUserId()==null)){	// if userid is not null
						User tmp = ub.read(rem.getUserId().longValue());	// get correct user
						String email = tmp.getEmail();						// get that user's email
						/*
						 * add onto email message apprporiately based on whether one exists currently or not
						 * tell the user what of their hardware is no longer associated with them
						 */
						if (recipients.containsKey(email)){
							String newMessage = recipients.get(email)+"\n"+rem.getKey() + "(no longer yours)\n";
							recipients.put(email, newMessage);
						}
						else{
							recipients.put(email, "Hello " + tmp.getFirstName() + ",\n\n"
									+ "This email is to update you on the ownership of your Hardware "
									+ "with serial number(s):\n\n" + rem.getKey() + " (no longer yours)\n");
						}
					}
					rem.setUserId(null);	// null the userid of the hardware removed no matter what
				} else {	// working with location
					rem.setLocationId(null);// null the locationid 
				}
				hb.update(rem);	//update the hw in the database
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
		for (Hardware hw: hwList) {
			if(tableId.equals("1")){	// if working with user
				/*
				 * append to email notifying user of change if user exists and is different (i.e. there IS a change to notify)
				 */
				Long oldId = hw.getUserId();		//id of previous owner,null if none
				Long newId = updatePk;//id of new owner, cannot be null
				User tmp;							//temporary user object for convenience
				String email;						//email address to send to

				if(!newId.equals(oldId)){			//if the owner changes
					if (oldId!=null){				//AND there was a previous owner
						/*
						 * get the old user and their email address
						 * and tell the old owner that the hardware's been removed
						 * provide an introductory statement in the email message
						 * if there isn't one already
						 */
						
						tmp = ub.read(oldId.longValue());
						email = tmp.getEmail();
						if (recipients.containsKey(email)){
							String newMessage = recipients.get(email)+"\n"+hw.getKey() + "(no longer yours)\n";
							recipients.put(email, newMessage);
						}
						else{
							recipients.put(email, "Hello " + tmp.getFirstName() + ",\n "
									+ "This email is to update you on the ownership of your Hardware"
									+ " with serial number(s):\n\n" + hw.getKey() + "(no longer yours)\n");
						}
					}
					
					/*
					 * get the new user and their email address
					 * and tell them that they've been assigned that hardware
					 * provide and introductory statement in the email message if there
					 * isn't one already 
					 */
					
					tmp = ub.read(updatePk);
					email = tmp.getEmail();
					if (recipients.containsKey(email)){
						String newMessage = recipients.get(email)+"\n"+hw.getKey() + "(newly yours)\n";
						recipients.put(email, newMessage);
					}
					else{
						recipients.put(email, "Hello " + tmp.getFirstName() + ",\n "
								+ "This email is to update you on the ownership of your Hardware"
								+ " with serial number(s):\n\n" + hw.getKey() + "(newly yours)\n");
					}
				}
				hw.setUserId(updatePk);	// always update userid
			}else{	// working with location
				hw.setLocationId(updatePk); // always update locationid
			}
			
			/*
			 * try updating hardware. log error if unable to 
			 */
			
			try {
				hb.update(hw);
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
	}
    
    /**
     * check if valid input
     * @param str the input barcode/username
     * @return true if input validly references a table using either a barcode or username
     */
    
    private static boolean isValidInput(String str) {
    	return isBarcode(str) || isUsername(str);
    }
    
    /**
     * return the appropriate error message for input
     * @param str the input
     * @return the appropriate error message for input; null if no error
     */
    
    private static String barcodeErrorMessage(String str){
    	if (isUsername(str))
    		return null;
    	if (!(str.length()==12 || str.length()==13)){
    		return "Barcode is wrong length; must be 12 or 13 characters.";
    	}
    	String tabId = parseTableIdentifier(str);
    	logger.trace("tabId parsed when checking errors is" + tabId);
    	if (tabId.equals("")){
    		return "Barcode does not contain a valid table identifier.";
    	}
    	Long pk = parsePk(str);
    	try{
	    	switch(tabId){
	    	case "1":
	    		if (ub.read(pk)==null)
	    			return "Barcode references an invalid User.";
	    		break;
	    	case "2":
	    		if (hb.read(pk)==null)
	    			return "Barcode references an invalid Hardware.";
	    		break;
	    	case "4":
	    		if (lb.read(pk)==null)
	    			return "Barcode references an invalid Location.";
	    		break;
	    	default:
	    		return "Barcode does not reference a valid table.";
	    	}
    	} catch (BoException be){
    		return "Barcode references an invalid object.";
    	}
    	return null;
    }
    
    /**
     * return if str is a valid barcode
     * @param str the input to be checked
     * @return true if the input is a valid barcode
     */
    
    private static boolean isBarcode(String str){
    	return barcodeErrorMessage(str)==null;
    }
    
    /**
     * return true if the input is the username of someone in the system
     * @param str the input to be tested
     * @return true if a User has that username
     */
    
    private static boolean isUsername(String str){
    	return !(ub.getUserByUsername(str)==null);
    }
    
    /**
     * return true if the input is numeric
     * @param str the input to be tested
     * @return true if the input is numeric
     */
    
    private static boolean isNumeric(String str)
    {
    	try
    	{
    		@SuppressWarnings("unused")
			double d = Double.parseDouble(str);
    	}
    	catch(NumberFormatException nfe)
    	{
    		return false;
    	}
    	return true;
    }
    
    /**
     * pad zeroes onto a key to format it correctly for a barcode
     * @param str the string to be formatted
     * @return the formatted string or empty string if cannot be formatted validly
     */
    
    private static String padZeroes(String str) {
    	if (str.length() > 9 || !isNumeric(str)){
    		logger.error("Cannot pad zeroes onto string " + str);
    		return "";
    	}
    	String appRet = "";
    	for (int x = 0; x < 9-str.length();x++){
    		appRet+="0";
    	}
    	return appRet+str;
    }
    
    /**
     * get the table identifier from a barcode
     * @param str the barcode
     * @return the table identifier references
     */
    
    private static String parseTableIdentifier(String str){
    	if (str.length() < 12 || str.length() > 13 || !isNumeric(str)){
    		System.out.println(str.length() + " " + isNumeric(str));
    		logger.debug("Attempted to parse table identifier from " + str);
    		return "";
    	}
    	else if(str.length()==12){
    		str="0"+str;
    	}
    	String ret = str.substring(0,3);
    	ret = removePaddingZeroes(ret);
    	logger.trace("Parsed table identifier for " + str + " is " + ret);
    	return ret;
    }
    
    /**
     * remove padding zeroes from a String
     * @param str the input to be modified
     * @return the String without padding zeroes
     */
    
    private static String removePaddingZeroes(String str){
    	String ret = str;
    	for (int x = 0; x < ret.length(); x++) {
    		if (ret.charAt(x)!='0'){
    			ret = ret.substring(x);
    			break;
    		}
    	}
    	return ret;
    }
    
    /**
     * parse a pk from a String
     * @param str the String to be parsed
     * @return the pk as a Long
     */
    
    private static Long parsePk(String str){
    	if (str.length() < 3){
    		logger.debug("Attempted to parse pk from " + str);
    		return (long) -1;
    	}
    	String ret = str.substring(3,str.length()-1);
    	ret = removePaddingZeroes(ret);
    	logger.trace("Parsed pk for " + str + " is " + ret);
    	return Long.parseLong(ret);
    }
}
