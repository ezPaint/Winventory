package com.simoncomputing.app.winventory.util;
import java.awt.image.BufferedImage;
import java.io.IOException;


import org.apache.commons.lang.NumberUtils;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.simoncomputing.app.winventory.domain.*;

/**
 * Adding this as an implementation to other classes wound up being too difficult to combine with 
 * batgen stuff. Instead this final class is going to be used to generate barcodes amongst other 
 * things and will be designed to be used in a static manner. 
 * 
 * This class is meant to be used as a static utility for generating barcodes for objects that 
 * require them. It takes the object that needs a barcode and handles everything else internally 
 * it can also be used for getting a image of the barcode to be printed off and stuck on the 
 * object once complete. 
 * @author chris.hess
 *
 */
public final class Barcoder {
	
	/**
	 * Get barcode is an overloaded method that takes 1 of 4 different objects that require 
	 * barcodes. Then due to the overloading, it will carry out the proper steps to build 
	 * the correct barcode.  
	 * @param user
	 * @return
	 */
	public static String getBarcode(User user) {
		String result = "001";
		result = result + padZeroes(user.getKey().toString());
		result = result + buildCheckSum(result);
		return result;
		
	}
	
	public static String getBarcode(Hardware hardware) {
		String result = "002";
		result = result + padZeroes(hardware.getKey().toString());
		result = result + buildCheckSum(result);
		return result;
		
	}
	
	public static String getBarcode(Software software) {
		String result = "003";
		result = result + padZeroes(software.getKey().toString());
		result = result + buildCheckSum(result);
		return result;
	}
	

	
	public static String getBarcode(Location location) {
		String result = "004";
		result = result + padZeroes(location.getKey().toString());
		result = result + buildCheckSum(result);
		return result;
		
	}
	
	private static int buildCheckSum(String code) {
		
		int result = 0;
		char[] chars = code.toCharArray();
		int evenSum = 0; 
		int oddSum = 0;
		for(int i = 0; i < code.length()-1; i = i + 2) {
			evenSum += Character.getNumericValue(chars[i]); 
			oddSum += Character.getNumericValue(chars[i+1]);
			
		}
		result = 10-((3*oddSum)+evenSum)%10;
		result = result%10;
		
		return result;
		
	}
	
	private static String padZeroes(String str) {
    	if (str.length() > 9){
    		return "Error Padding, pk too long";
    	}
    	String appRet = "";
    	for (int x = 0; x < 9-str.length();x++){
    		appRet+="0";
    	}
    	return appRet+str;
    }
	
	public static BufferedImage buildBarcodeImage(String str) throws IOException {
		final int dpi = 150;
		EAN13Bean bean = new EAN13Bean();
		//Set up the canvas provider for monochrome PNG output 
	    BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
		if(str.length() == 13 && NumberUtils.isDigits(str))
		{
			
			

			//Configure the barcode generator
			bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar 
			                                                 //width exactly one pixel
			bean.setHeight(15);
			bean.setModuleWidth(.35);
			bean.doQuietZone(true);
			bean.setQuietZone(10);
		
			    //Generate the barcode
			    bean.generateBarcode(canvas, str);

			    //Signal end of generation
			    canvas.finish();
		}
		return canvas.getBufferedImage();
	}
	
}
