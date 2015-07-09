package com.simoncomputing.app.winventory.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

@WebServlet("/barcodes/barcode")
public class BarcodeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(BarcodeController.class);
	private static UserBo ub = UserBo.getInstance();
	private static HardwareBo hb = HardwareBo.getInstance();
	private static LocationBo lb = LocationBo.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.getRequestDispatcher("/WEB-INF/flows/barcodes/barcode.jsp").forward(request,
                response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	logger.trace("parameter map is " + request.getParameterMap());
    	request.getSession().setAttribute("ub", ub);
    	request.getSession().setAttribute("lb", lb);
		String barcode = request.getParameter("barcode");
		boolean update = Boolean.parseBoolean(request.getParameter("toSubmit"));
		if (update) {
			String[] pkList = request.getParameterValues("removeHw");
			if (pkList==null){
				pkList=new String[0];
			}
			ArrayList<Hardware> hwList = (ArrayList<Hardware>) request.getSession().getAttribute("hardware");
			String modifyCode;
			User owner = (User)request.getSession().getAttribute("user");
			Location loc = (Location)request.getSession().getAttribute("location");
			if(owner==null){
				modifyCode="004"+padZeroes(loc.getKey().toString());
			} else {
				modifyCode="001"+padZeroes(owner.getKey().toString());
			}
			modifyCode+="3";	//TODO make valid checksum; random number right now
			logger.trace("pkList is " + Arrays.asList(pkList));
    		updateDatabase(pkList, hwList, modifyCode);
    		logger.trace("updating database");
		}
		else if(barcode != null){
			long pk = -1;
			String first = parseTableIdentifier(barcode);
			try{
				pk = parsePk(barcode);
			} catch (NumberFormatException nfe) {
				logger.error("Could not get a pk from " + barcode);
			}
			try{
    			switch(first){
    			case "1":
    				logger.trace("preloading based on user " + pk);
    				request.getSession().setAttribute("user", ub.read(pk));
    				request.getSession().setAttribute("location", null);
    				request.getSession().setAttribute("hardware", hb.getListByUserId((int)pk));
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
    					hwList.add(hb.read(pk));
    				else {
    					request.setAttribute("error", "That Hardware is already in the list");
    				}
    				request.getSession().setAttribute("hardware", hwList);
    				break;
    			case "4":
    				logger.trace("preloading based on location " + pk);
    				request.getSession().setAttribute("user", null);
    				request.getSession().setAttribute("location", lb.read(pk));
    				List<Hardware> hardware = hb.getListByLocationId((int)pk);
    				request.getSession().setAttribute("hardware", hardware);
    				logger.debug(hb.getListByLocationId((int)pk));
    				break;
    			default:
    				break;
    			}
			} catch (BoException be) {
				logger.error("Could not get an object from table id:" + first + " with pk " + pk);
			}
		}

    	if (request.getSession().getAttribute("user")==null && request.getSession().getAttribute("location")==null){
    		request.getSession().setAttribute("hardware",null);
    		request.setAttribute("error","You must enter an Owner or Location first");
    	} else {
    		logger.trace("altering error");
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
    
    private static boolean isNumeric(String str)
    {
    	try
    	{
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
