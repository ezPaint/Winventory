package barcoder;


import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.*;

import com.simoncomputing.app.winventory.bo.AccessTokenBo;
import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.util.*;
import com.simoncomputing.app.winventory.dao.*;

/**
 * This a simple test of all the barcoder methods.
 * @author chris.hess
 *
 */


public class testBarcoder {
	private User user;	// a simple user object for testing
	private Hardware hardware; // a simple hardware object for testing 
	private Software software; // a simple software object for testing 
	private Location location; // a simple location object for testing 
    
	/**
	 * all this class does is initialize the key values in each of the objects because that is 
	 * all that is needed to generate barcodes. Each key was picked randomly 
	 */
	@Before
    public void setup() {
    	user = new User();
    	user.setKey((long) 11);
        hardware = new Hardware();
        hardware.setKey((long) 22);
        software = new Software();
        software.setKey((long) 33);
        location = new Location();
        location.setKey((long)123314);
    }

	
	/**
	 * this tests that passing a software object through the barcode generator yields the 
	 * expected results
	 */
    @Test
    public void testSoftwareBarcoding()  {
    	
    	String fullBarcode = Barcoder.getBarcode(software);	// build barcode
    	assertSame(13, fullBarcode.length()); // check for proper length
    	assertTrue("0030000000335".equals(fullBarcode)); // check for correct digits 
    	try {
    		
			BufferedImage temp = Barcoder.buildBarcodeImage(fullBarcode);// generate barcode image
			assertNotNull(temp);// assert that for valid input the returned image is not null 
			
		} catch (IOException e) {
			e.printStackTrace();
		}


    }
    
    /**
     * this tests passing a user object to the barcode generator will yield expected results
     */
    @Test
    public void testUserBarcoding()  {
    	
    	String fullBarcode = Barcoder.getBarcode(user); // generate barcode for user object 
    	assertSame(13, fullBarcode.length()); // make sure length is correct
    	assertTrue("0010000000115".equals(fullBarcode)); // check that all digits are correct 
    	try {
    		
			BufferedImage temp = Barcoder.buildBarcodeImage(fullBarcode); // generate image file
			assertNotNull(temp); // ensure that valid input yields an image file 
			
		} catch (IOException e) {
			e.printStackTrace();
		}


    }
    
    /**
     * this tests passing a hardware object to the barcoder will yield expected results 
     */
    @Test
    public void testHardwareBarcoding()  {
    	String fullBarcode = Barcoder.getBarcode(hardware); // generate barcode for hardware object
    	assertSame(13, fullBarcode.length()); // ensure length is correct
    	assertTrue("0020000000220".equals(fullBarcode)); // ensure barcode digits are right 
    	
    	try {
    		
			BufferedImage temp = Barcoder.buildBarcodeImage(fullBarcode); // generate image 
			assertNotNull(temp); // make sure that valid input yields a file 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }
    
    /**
     * this tests that passing a location object to the barcoder yields the expected results 
     */
    @Test
    public void testLocationBarcoding() {
    	String fullBarcode = Barcoder.getBarcode(location); // generate location barcode 
    	assertSame(13, fullBarcode.length());
    	assertTrue("0040001233144".equals(fullBarcode));
    	
    	try {
    		
			BufferedImage temp = Barcoder.buildBarcodeImage(fullBarcode); // generate image 
			assertNotNull(temp); // make sure that valid input yields a file 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    /**
     * Users are never able to directly provide the input for the image generator however this
     * method tests lots of different input that is invalid for the image builder to ensure that 
     * when this happens, nothing breaks or throws exceptions, but instead the builder just 
     * returns null. 
     */
    @Test
    public void testInvalidImageInput() {

    	try {
    		
    		//test too short alphabetic input 
			BufferedImage temp = Barcoder.buildBarcodeImage("asdf");  
			assertNull(temp); // make sure result is null 
			// test correct length input but all alphabetic 
			temp = Barcoder.buildBarcodeImage("asdfasdfasdfa");
			assertNull(temp);
			// test all alphabetic too long input 
			temp = Barcoder.buildBarcodeImage("asdfasdfasdfasdfasdf");
			assertNull(temp);
			// test all numeric but too short 
			temp = Barcoder.buildBarcodeImage("1234"); 
			assertNull(temp);
			// test all numeric but too long 
			temp = Barcoder.buildBarcodeImage("122354452345345534"); 
			assertNull(temp);
			// test a mix but too short 
			temp = Barcoder.buildBarcodeImage("1A2d3#4"); 
			assertNull(temp);
			// test a mix and right length 
			temp = Barcoder.buildBarcodeImage("asdf!@#$1234f"); 
			assertNull(temp);
			// test a mix but too long  
			temp = Barcoder.buildBarcodeImage("as141df!@#$ADadf1234f"); 
			assertNull(temp);
			// test an empty string
			temp = Barcoder.buildBarcodeImage(""); 
			assertNull(temp);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
    
    /**
     * This tests what happens when any of the objet that could be passed to the barcode generator
     * are null and ensures that all that happens is null gets return back. 
     */
    @Test
    public void testNullObjects() {
    	// create the null objects 
    	Software nullSoft = null; 
    	Hardware nullHard = null;
    	User nullUser = null;
    	Location nullLoc = null;
    	// run the getter on each object and check that all string results are null
    	String temp = Barcoder.getBarcode(nullSoft);
    	assertNull(temp);
    	temp = Barcoder.getBarcode(nullHard);
    	assertNull(temp);
    	temp = Barcoder.getBarcode(nullUser);
    	assertNull(temp);
    	temp = Barcoder.getBarcode(nullLoc);
    	assertNull(temp);
    	
    }
    
    /**
     * This tests that if any object is passed without a key instead of getting any errors 
     * it simply returns a null value and that is that. 
     */
    @Test
    public void testEmptyObjects() {
    	// create empty objects 
    	Software emptySoft = new Software();
    	Hardware emptyHard = new Hardware();
    	User emptyUser = new User();
    	Location emptyLoc = new Location();
    	// run the getter on each and check the string result.
    	String temp = Barcoder.getBarcode(emptySoft);
    	assertNull(temp);
    	temp = Barcoder.getBarcode(emptyHard);
    	assertNull(temp);
    	temp = Barcoder.getBarcode(emptyUser);
    	assertNull(temp);
    	temp = Barcoder.getBarcode(emptyLoc);
    	assertNull(temp);
    	
    }
    

}