package com.simoncomputing.app.winventory.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

@WebServlet("/barcodes/barcode")
public class BarcodeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(BarcodeController.class);
	private static UserBo ub = UserBo.getInstance();			// User business object
	private static HardwareBo hb = HardwareBo.getInstance();	// Hardware business object
	private static LocationBo lb = LocationBo.getInstance();	// Location business object
	
	/*
	 * simply load the jsp on a GET
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("hardware");
		request.getSession().removeAttribute("location");
    	request.getRequestDispatcher("/WEB-INF/flows/barcodes/barcode.jsp").forward(request,
                response);
    }

    /*
     * handle barcode/username input
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	logger.debug("parameter map is " + request.getParameterMap()); // log parameters passed in
    	Enumeration<String> attrName = request.getAttributeNames();
    	while(attrName.hasMoreElements()){
    		logger.debug(attrName.nextElement());
    	}
    	request.getSession().setAttribute("ub", ub);		// give userbo to session
    	request.getSession().setAttribute("lb", lb);		// give locationbo to session
    	request.getSession().setAttribute("hardware", request.getSession().getAttribute("hardware"));
    	request.getSession().setAttribute("user", request.getSession().getAttribute("user"));
    	request.getSession().setAttribute("location", request.getSession().getAttribute("location"));
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
			ArrayList<Hardware> hwList = (ArrayList<Hardware>) request.getSession().getAttribute("hardware");
				// the list of hardware currently being worked with
			String modifyCode;	// where the barcode for what must be modified will be stored
			User owner = (User)request.getSession().getAttribute("user");
				// the user stored on the Session, can be null
			Location loc = (Location)request.getSession().getAttribute("location");
				// the location stored on the Session, can be null
			if(owner==null){	// if working with Location
				modifyCode="004"+padZeroes(loc.getKey().toString());	// make barcode for Location
			} else {			// if working with User
				modifyCode="001"+padZeroes(owner.getKey().toString());	// make barcode for User
			}
			modifyCode+="3";	//TODO make valid checksum; random number right now
			logger.trace("pkList is " + Arrays.asList(pkList));
    		updateDatabase(pkList, hwList, modifyCode);	// update the database
    		logger.trace("updating database");
		} else if (clear){	// if want to clear previous input (start a new search)
			// clear User, Location, and Hardware list from the Session
			request.removeAttribute("user");
			request.removeAttribute("hardware");
			request.removeAttribute("location");
		} else if (!valid){	// if input is not valid
			// determine and set correct error message; store barcode
			request.setAttribute("error", barcodeErrorMessage(barcode));
			request.getSession().setAttribute("barcode", barcode);
		} else if(barcode != null){
			long pk = -1;	// store invalid pk before initialized
			String tableIdentifier = "-1";	// store invalid  table identifier before initialized
			try{
				tableIdentifier = parseTableIdentifier(barcode);	// get actual table identifier
				pk = parsePk(barcode);	// get actual pk
			}
			/*
			 * catches here as formality.  Valid input tests for these errors
			 */
			catch (NumberFormatException nfe) {
				logger.error("Could not get a pk from " + barcode);
			} catch (IllegalArgumentException ie) {
				logger.error("Could not get a table identifier from " + barcode);
			}
			List<Hardware> hardware;	// the list of hardware to pass back to view
			try{
    			switch(tableIdentifier){	// switch based on table identifier
    			case "1":
    				logger.trace("preloading based on user " + pk);
    				request.getSession().setAttribute("user", ub.read(pk));
    				request.getSession().setAttribute("location", null);
    				hardware = hb.getListByUserId((int)pk);
    				request.getSession().setAttribute("hardware", hardware);
    				break;
    			case "2":
    				@SuppressWarnings("unchecked")
					ArrayList<Hardware> hwList = (ArrayList<Hardware>) request.getSession().getAttribute("hardware");
    				logger.debug("hwList is " + hwList);
    				if (hwList==null){
    					hwList = new ArrayList<Hardware>();
    				}
    				Hardware h = hb.read(pk);
    				if (!hwList.contains(h))
    					hwList.add(h);
    				else {
    					request.setAttribute("error", "That Hardware is already in the list");
    				}
    				request.getSession().setAttribute("hardware", hwList);
    				break;
    			case "4":
    				logger.trace("preloading based on location " + pk);
    				request.getSession().setAttribute("user", null);
    				request.getSession().setAttribute("location", lb.read(pk));
    				hardware = hb.getListByLocationId((int)pk);
    				request.getSession().setAttribute("hardware", hardware);
    				logger.debug(hb.getListByLocationId((int)pk));
    				break;
    			default:
    				logger.trace("preloading based on username " + barcode);
    				User user = ub.getUserByUsername(barcode);
    				request.getSession().setAttribute("location", null);
    				request.getSession().setAttribute("user", user);
    				request.getSession().setAttribute("hardware", hb.getListByUserId(user.getKey().intValue()));
    				break;
    			}
			} catch (BoException be) {
				logger.error("Could not get an object from table id:" + tableIdentifier + " with pk " + pk);
			}
		}
		
		if (request.getAttribute("error")==null && !clear && request.getSession().getAttribute("user")==null && request.getSession().getAttribute("location")==null){
    		request.getSession().setAttribute("hardware",null);
    		request.setAttribute("error","You must enter an Owner or Location first");
    	}
		
    	request.getRequestDispatcher("/WEB-INF/flows/barcodes/barcode.jsp").forward(request,
                response);
    }
    
    private void updateDatabase(String[] pkList, ArrayList<Hardware> hwList, String barcode) {
		String tableId = parseTableIdentifier(barcode);
		Long updatePk = parsePk(barcode);
    	for (String pk: pkList) {
			try {
				Hardware rem = hb.read(Long.parseLong(pk));
				logger.debug("*before* hardware userid to remove is " + rem.getUserId());
				logger.debug("*before* hardware locationid to remove is " + rem.getLocationId());
				hwList.remove(rem);
				if (tableId.equals("1")) {
					rem.setUserId(null);
					logger.trace("removing a user");
				} else {
					rem.setLocationId(null);
					logger.trace("removing a location");
				}
				logger.debug("*after* hardware userid to remove is " + rem.getUserId());
				logger.debug("*before* hardware locationid to remove is " + rem.getLocationId());
				hb.update(rem);
				Hardware please = hb.read(Long.parseLong(pk));
				logger.debug("*after update* hardware pk is " + please.getKey());
				logger.debug("*after update* hardware userid is " + please.getUserId());
				logger.debug("*after update* hardware locationid is " + please.getLocationId());
				logger.debug("size of getListByUserId(1) after update is " + hb.getListByUserId(1).size());
			} catch (NumberFormatException e) {
				logger.error("Cannot dissociate hardware with pk " + pk + " because pk is not a number");
			} catch (BoException e) {
				logger.error("Cannot get Hardware with pk to remove " + pk);
			}
		}
		for (Hardware hw: hwList) {
			if(tableId.equals("1")){
				hw.setUserId(updatePk.intValue());
			}else{
				hw.setLocationId(updatePk.intValue());
			}
			try {
				hb.update(hw);
			} catch (BoException e) {
				logger.error("Could not update Hardware with pk " + hw.getKey());
			}
		}
	}
    
    private static boolean isValidInput(String str) {
    	return isBarcode(str) || isUsername(str);
    }
    
    private static String barcodeErrorMessage(String str){
    	if (isUsername(str))
    		return null;
    	if (!(str.length()==12 || str.length()==13)){
    		return "Barcode is wrong length; must be 12 or 13 characters.";
    	}
    	String tabId = parseTableIdentifier(str);
    	logger.trace("tabId parsed when checking errors is" + tabId);
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
    
    private static boolean isBarcode(String str){
    	return barcodeErrorMessage(str)==null;
    }
    
    private static boolean isUsername(String str){
    	return !(ub.getUserByUsername(str)==null);
    }
    
    private static boolean isNumeric(String str)
    {
    	try
    	{
    		@SuppressWarnings("unused")
			int d = Integer.parseInt(str);
    	}
    	catch(NumberFormatException nfe)
    	{
    		return false;
    	}
    	return true;
    }
    
    private static String padZeroes(String str) {
    	if (str.length() > 9 || !isNumeric(str)){
    		logger.error("Cannot pad zeroes onto string " + str);
    		throw new IllegalArgumentException(str + " cannot be validly padded");
    	}
    	String appRet = "";
    	for (int x = 0; x < 9-str.length();x++){
    		appRet+="0";
    	}
    	return appRet+str;
    }
    
    private static String parseTableIdentifier(String str){
    	if (str.length() < 12 || str.length() > 13){
    		logger.debug("Attempted to parse table identifier from " + str);
    		throw new IllegalArgumentException(str + " is not a recognized barcode");
    	}
    	else if(str.length()==12){
    		str="0"+str;
    	}
    	String ret = str.substring(0,3);
    	ret = removePaddingZeroes(ret);
    	logger.trace("Parsed table identifier for " + str + " is " + ret);
    	return ret;
    }
    
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
