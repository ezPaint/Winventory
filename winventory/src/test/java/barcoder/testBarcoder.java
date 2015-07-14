package barcoder;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.*;

import com.simoncomputing.app.winventory.bo.AccessTokenBo;
import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.util.*;
import com.simoncomputing.app.winventory.dao.*;




public class testBarcoder {
	private User user;
	private Hardware hardware;
	private Software software;
    @Before
    public void setup() {
    	user = new User();
    	user.setKey((long) 11);
        hardware = new Hardware();
        hardware.setKey((long) 22);
        software = new Software();
        software.setKey((long) 33);
    }

    @Test
    public void testSoftwareBarcoding()  {
    	
    	String fullBarcode = Barcoder.getBarcode(software);
    	assertSame(13, fullBarcode.length());
    	assertTrue("0030000000335".equals(fullBarcode));
    	try {
    		
			Barcoder.buildBarcodeImage("asasa");
			
		} catch (IOException e) {
			e.printStackTrace();
		}


    }
    
    @Test
    public void testUserBarcoding()  {
    	
    	String fullBarcode = Barcoder.getBarcode(user);
    	assertSame(13, fullBarcode.length());
    	assertTrue("0010000000115".equals(fullBarcode));
    	try {
    		
			Barcoder.buildBarcodeImage(fullBarcode);
			
		} catch (IOException e) {
			e.printStackTrace();
		}


    }
    
    @Test
    public void testHardwareBarcoding()  {
    	String fullBarcode = Barcoder.getBarcode(hardware);
    	assertSame(13, fullBarcode.length());
    	assertTrue("0020000000220".equals(fullBarcode));
    	
    	try {
    		
			Barcoder.buildBarcodeImage(fullBarcode);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }
    

}