package com.simoncomputing.app.winventory.domain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang.NumberUtils;

import com.lowagie.text.Image;
import com.lowagie.text.pdf.Barcode;
import com.lowagie.text.pdf.BarcodeEAN;

/**
 * Adding this as an implementation to other classes wound up being too difficult to combine with 
 * batgen stuff. Instead this final class is going to be used to generate barcodes amongst other 
 * things and will be designed to be used in a static manner. 
 * @author chris.hess
 *
 */
public final class Barcoder {
	
	public static String getBarcode(User user, boolean checkSum) {
		String result = "001";
		result = result + padZeroes(user.getKey().toString());
		if(checkSum) {
			result = result + buildCheckSum(result);
		}
		return result;
		
	}
	
	public static String getBarcode(Hardware hardware, boolean checkSum) {
		String result = "002";
		result = result + padZeroes(hardware.getKey().toString());
		if(checkSum) {
			result = result + buildCheckSum(result);
		}
		return result;
		
	}
	
	public static String getBarcode(Software software, boolean checkSum) {
		String result = "003";
		result = result + padZeroes(software.getKey().toString());
		if(checkSum) {
			result = result + buildCheckSum(result);
		}
		return result;
	}
	

	
	public static String getBarcode(Location location, boolean checkSum) {
		String result = "004";
		result = result + padZeroes(location.getKey().toString());
		if(checkSum) {
			result = result + buildCheckSum(result);
		}
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
	
	public static java.awt.Image buildBarcodeImage(String str) throws IOException {
		if(str.length() == 13 && NumberUtils.isDigits(str))
		{
			BarcodeEAN ean = new BarcodeEAN();
			ean.setCodeType(Barcode.EAN13);
			ean.setCode(str);
			java.awt.Image bi =  ean.createAwtImage(Color.black, Color.white);
			File output = new File("saved.png");
			ImageIO.write((RenderedImage) bi, "png", output);
		}
		return null;
	}
	
}
