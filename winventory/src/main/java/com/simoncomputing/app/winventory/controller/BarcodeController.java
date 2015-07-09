package com.simoncomputing.app.winventory.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.BoException;

@WebServlet("/barcodes/barcode")
public class BarcodeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(BarcodeController.class);
	private static UserBo ub = UserBo.getInstance();
	private static HardwareBo hb = HardwareBo.getInstance();
	private static SoftwareBo sb = SoftwareBo.getInstance();
	private static LocationBo lb = LocationBo.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.getRequestDispatcher("/WEB-INF/flows/barcodes/barcode.jsp").forward(request,
                response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	if (request.getParameter("toSubmit").equals("true")){
    		updateDatabase(request.getParameterMap());
    		logger.trace("updating database");
    	}
    		
    	request.getSession().setAttribute("ub", ub);
    	request.getSession().setAttribute("lb", lb);
		String barcode = request.getParameter("barcode");
		logger.trace("barcode is " + barcode);
		if(barcode != null){
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
    				request.getSession().setAttribute("user", ub.read(pk));
    				request.getSession().setAttribute("hardware", hb.getListByUserId((int)pk));
    				break;
    			case "2":
    				@SuppressWarnings("unchecked")
					ArrayList<Hardware> hwList = (ArrayList<Hardware>) request.getSession().getAttribute("hardware");
    				logger.debug("hwList is " + hwList);
    				if (hwList==null){
    					hwList = new ArrayList<Hardware>();
    				}
    				hwList.add(hb.read(pk));
    				request.getSession().setAttribute("hardware", hwList);
    				break;
    			case "4":
    				request.getSession().setAttribute("location", lb.read(pk));
    				request.getSession().setAttribute("hardware", hb.getListByLocationId((int)pk));
    				logger.debug(hb.getListByLocationId((int)pk));
    				break;
    			default:
    				break;
    			}
			} catch (BoException be) {
				logger.error("Could not get an object from table id:" + first + " with pk " + pk);
			}
		}
//    		if (s.contains("hardware")) {
//    			request.setAttribute(s, getNames(request.getParameterValues(s)));
//    		} else {
//    			request.setAttribute(s, getName(request.getParameter(s)));
//    			try {
//    				String param = request.getParameter(s);
//    				int pk = Integer.parseInt(parsePk(param));
//    				List<Hardware> hwList;
//    				hwList = hb.getListByUserId(pk);
//    				logger.trace("list of hardware:" + hwList);
//					request.setAttribute("hardware", hb.getListByUserId(pk));
//				} catch (NumberFormatException e) {
//					logger.error("Error parsing pk from " + s + ":" + request.getParameter(s));
//				} catch (BoException e) {
//					logger.error("Error trying to get Hardware using " + s);
//				}
//    		}
    	if (request.getSession().getAttribute("user")==null && request.getSession().getAttribute("location")==null){
    		request.getSession().setAttribute("hardware",null);
    		request.setAttribute("error","You must enter an Owner or Location first");
    	} else {
    		logger.trace("altering error");
    	}
    	request.getRequestDispatcher("/WEB-INF/flows/barcodes/barcode.jsp").forward(request,
                response);
    }
    
//    private HashMap<String,String[]> preloadPage(String barcode){
//		if(barcode.startsWith("1"))
//			return preloadPage(ub);
//		else
//			return preloadPage(lb);
//    }
//    
//    private HashMap<String,String[]> preloadPage(UserBo userBo){
//    	HashMap<String,String[]> ret = new HashMap<String,String[]>();
//    	hb.getListByUserId(key);
//    	return ret;
//    }
//    
//    private HashMap<String,String[]> preloadPage(LocationBo locationBo){
//    	HashMap<String,String[]> ret = new HashMap<String,String[]>();
//    	
//    	return ret;
//    }
    
    private void updateDatabase(Map<String, String[]> parameterMap) {
		
	}
    
//    private String[] getNames(String[] barcodes) throws NumberFormatException {
//    	String[] ret = new String[barcodes.length];
//    	for (int x = 0; x < barcodes.length;x++){
//    		ret[x]=getName(barcodes[x]);
//    	}
//    	return ret;
//    }
    
//    private String getName(String barcode) throws NumberFormatException {
//    	String ret;
//    	char first;
//    	if (barcode.length() < 3){
//    		return "";
//    	}
//    	if (!isNumeric(barcode)){
//    		return barcode;
//    	}
//    	first = barcode.charAt(0);
//    	int code = parsePk(barcode);
//		try{
//			switch(first){
//			case '1':
//				ret = ub.read(code).getFirstName();
//				break;
//			case '2':
//				ret=hb.read(Long.parseLong(code)).getDescription();
//				break;
//			case '3':
//				ret="Not a valid barcode";
//				break;
//			case '4':
//				ret = lb.read(Long.parseLong(code)).getDescription();
//				break;
//			default:
//				ret = "Not a valid barcode";
//			}
//		} catch(BoException be){
//			ret = "Not a valid selection";
//		}
//		if (ret.length() > 22) {
//			ret = ret.substring(0,20)+"...";
//		}
//    	return ret;
//    }
    
    private static boolean isNumeric(String str)
    {
    	try
    	{
    		double d = Double.parseDouble(str);
    	}
    	catch(NumberFormatException nfe)
    	{
    		return false;
    	}
    	return true;
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
