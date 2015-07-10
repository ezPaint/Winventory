package com.simoncomputing.app.winventory.domain;

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
		String[] chars = code.split("");
		int evenSum = 0; 
		int oddSum = 0;
		for(int i = 0; i < code.length()-1; i = i + 2) {
			evenSum += Integer.parseInt(chars[i]); 
			oddSum += Integer.parseInt(chars[i+1]);
			
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
	
}
