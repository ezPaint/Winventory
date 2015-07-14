package com.simoncomputing.app.winventory.util;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.commons.lang.NumberUtils;
import org.apache.log4j.Logger;
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
	
	static Logger logger = Logger.getLogger(Barcoder.class);
	/**
	 * Getbarcode is an overloaded method that takes 1 of 4 different objects that require 
	 * barcodes. Then due to the overloading, it will carry out the proper steps to build 
	 * the correct barcode for that object. 
	 * The barcodes are comprised with the first 3 digits representing what type of object 
	 * is being barcoded. Then the following 9 correspond to the key that object has. 
	 * The last digit is a checksum that is generated based on a certain formula.
	 * Each of these methods implements the exact same logic, just with a different 
	 * prefix based on the type of object. The overloading of the methods avoids extra switch
	 * cases and checks.  
	 * @param user object for getting a barcode 
	 * @return a 13 digit string representing the barcode with the right checksum 
	 */
	public static String getBarcode(User user) {
		// prefix representing users added to start the string 
		String result = "001";	
		// take the key and pad it with 0s to fill the 9 digits needed 
		result = result + padZeroes(user.getKey().toString());	
		// append the check sum
		result = result + buildCheckSum(result);
		// return the complete barcode. 
		return result;
		
	}
	
	/**
	 * see the first getBarcode method for full description
	 * @param hardware the hardware object that needs a barcode 
	 * @return a string result of the correct barcode 
	 */
	public static String getBarcode(Hardware hardware) {
		String result = "002";
		result = result + padZeroes(hardware.getKey().toString());
		result = result + buildCheckSum(result);
		return result;
		
	}
	
	/**
	 * see the first getBarcode method for full description
	 * @param software the software object that needs a barcode 
	 * @return a string result of the correct barcode 
	 */
	public static String getBarcode(Software software) {
		String result = "003";
		result = result + padZeroes(software.getKey().toString());
		result = result + buildCheckSum(result);
		return result;
	}
	

	/**
	 * see the first getBarcode method for full description
	 * @param location the location object that needs a barcode 
	 * @return a string result of the correct barcode 
	 */
	public static String getBarcode(Location location) {
		String result = "004";
		result = result + padZeroes(location.getKey().toString());
		result = result + buildCheckSum(result);
		return result;
		
	}
	
	/**
	 * this method generates a checksum based upon the first 12 digits of the barcode string 
	 * that is passed to it. It is a private method only called after its parameter has been
	 * generated in a very specific way. This is why there are no additional checks on how 
	 * its input parameter is formatted. 
	 * @param code the first 12 digits of a barcode
	 * @return a single digit representing the checksum. 
	 */
	private static int buildCheckSum(String code) {
		
		int result = 0; // the final digit we are working toward
		char[] chars = code.toCharArray(); // an array of the input code 
		int evenSum = 0; // a sum of all even indexed numbers 
		int oddSum = 0; // a sum of all odd index numbers 
		// this for loop iterates through the array of numbers and grabs each digit and adds it 
		// to the proper sum variable 
		for(int i = 0; i < code.length()-1; i = i + 2) { 
			evenSum += Character.getNumericValue(chars[i]); 
			oddSum += Character.getNumericValue(chars[i+1]);
			
		}
		result = 10-((3*oddSum)+evenSum)%10; // this is just how the checksum is calculated
		result = result%10;	// this is just how the checksum is calculated 
		
		return result;	// this is the final result 
		
	}
	
	/**
	 * This is a simple method that takes a string and adds zeroes to it until it has a length 
	 * of 9 to be added to the object identifying preffix 
	 * @param str the string to be padded 
	 * @return the new string with length 9 
	 * @throws IllegalArgumentException 
	 */
	private static String padZeroes(String str) throws IllegalArgumentException {
    	
		// if string is already 9 don't do anything you're done 
		if(str.length() == 9) {
			return str;
		}
		// if its less than 9, add the necessary 0s and then return the result 
		if(str.length() < 9) {
			
    	String appRet = "";
    	for (int x = 0; x < 9-str.length();x++){
    		appRet+="0";
    	}
		
    	return appRet+str;
		}
		// the only other case is that the number passed is bigger than 9 digits, in which case 
		// the barcoding format doesn't allow for that big a number, and it just won't work. 
		else {
			logger.fatal("You have managed to go beyond a billion items in a table, our system can't "
					+ "handle that, contact developers");
			throw new IllegalArgumentException("You have managed to go beyond a billion items in a"
					+ " table, our system can't handle that, contact developers");
		}
    }
	
	/**
	 * this takes a string representation of the barcode and then builds an image to be displayed
	 * to users amongst other things. The passed value must be 13 digits and numeric for the 
	 * method to execute. 
	 * @param str a 13 digit string representing the barcode of an object 
	 * @return a buffered image of the barcode in human and scanner readable format 
	 * @throws IOException
	 */
	public static BufferedImage buildBarcodeImage(String str) throws IOException {
		final int dpi = 150;
		EAN13Bean bean = new EAN13Bean();
		//Set up the canvas provider for monochrome PNG output 
	    BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
		
	    // make sure that our string is a valid barcode 
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
