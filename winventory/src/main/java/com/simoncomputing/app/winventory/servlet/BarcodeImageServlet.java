package com.simoncomputing.app.winventory.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
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
import com.simoncomputing.app.winventory.util.Barcoder;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * a Servlet used to return a response containing a barcode image for any
 * Hardware (2), User (1), Location (4), or Software (3) in the database based on a table
 * identifier and key that must be passed in through the URL
 * 
 * the format of the url is as follows
 * <baseUrl>?key=<key>&table=<table>
 * @author seamus.lowry
 */

@WebServlet("/getBarcodeImage")
public class BarcodeImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(BarcodeImageServlet.class);
	static HardwareBo hb = HardwareBo.getInstance();
	static UserBo ub = UserBo.getInstance();
	static LocationBo lb = LocationBo.getInstance();
	static SoftwareBo sb = SoftwareBo.getInstance();
	

	/**
	 * return the barcode for that key
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	/*
    	 * initialize all possibly necessary objects to null
    	 */
    	
    	Hardware hw = null;
    	User user = null;
    	Location location = null;
    	Software sw = null;
    	
    	/*
    	 * get the key and table identifiers that must be passed into
    	 * the servlet from the URL
    	 */
    	
    	String key = request.getParameter("key");
    	String table = request.getParameter("table");
    	String barcode = "";
    	
    	/*
    	 * get the appropriate barcode, in text, based on the key and table
    	 * identifiers
    	 */
    	
    	try{
	    	switch(table){
	    	case "1":
		    	logger.trace("fetching barcode image for user with pk " + key);
					user = ub.read(Long.parseLong(request.getParameter("key")));
					barcode = Barcoder.getBarcode(user);
				break;
	    	case "2":
		    	logger.trace("fetching barcode image for hardware with pk " + key);
					hw = hb.read(Long.parseLong(request.getParameter("key")));
					barcode = Barcoder.getBarcode(hw);
				break;
	    	case "3":
		    	logger.trace("fetching barcode image for software with pk " + key);
					sw = sb.read(Long.parseLong(request.getParameter("key")));
					barcode = Barcoder.getBarcode(sw);
				break;
	    	case "4":
		    	logger.trace("fetching barcode image for location with pk " + key);
					location = lb.read(Long.parseLong(request.getParameter("key")));
					barcode = Barcoder.getBarcode(location);
				break;
	    	}
    	} catch (NumberFormatException e) {
			logger.error("Invalid key passed to barcode image servlet");
			return;
		} catch (BoException e) {
			logger.error("Error retreiving object from table with id "+table+" and pk " + key);
			return;
		}
    	
    	/*
    	 * create the image of the barcode
    	 * set that image as the response of the servlet
    	 */
    	
    	BufferedImage img = Barcoder.buildBarcodeImage(barcode);
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	ImageIO.write(img, "png", baos);
    	baos.flush();
    	byte[] imageBytes = baos.toByteArray();
    	baos.close();
    	response.setContentType("image/png");
    	response.setContentLength(imageBytes.length);
    	response.getOutputStream().write(imageBytes);
    }

    /**
     * not currently used
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	logger.error("in doPost for BarcodeImageServlet");
    }
}
