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
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.util.Barcoder;
import com.simoncomputing.app.winventory.util.BoException;


@WebServlet("/getBarcodeImage")
public class BarcodeImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(BarcodeImageServlet.class);
	HardwareBo hb = HardwareBo.getInstance();

	/**
	 * return the barcode for that key
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	Hardware hw = null;
    	String key = request.getParameter("key");
    	logger.trace("fetching barcode image for hardware with pk " + key);
		try {
			hw = hb.read(Long.parseLong(request.getParameter("key")));
		} catch (NumberFormatException e) {
			logger.error("Invalid key passed to barcode image servlet");
			return;
		} catch (BoException e) {
			logger.error("Error retreiving hardware with pk " + key);
			return;
		}
    	String barcode = Barcoder.getBarcode(hw);
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
    	
    	System.out.println("in post servlet");
    }
}
