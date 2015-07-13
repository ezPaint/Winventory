package com.simoncomputing.app.winventory.software;

import static org.junit.Assert.*;
import java.sql.Date;
import org.junit.Before;
import org.junit.Test;
import com.simoncomputing.app.winventory.domain.Software;


public class SoftwareTest {
    private Software msoffice;
    private Software msword;
    private Software nosoftware;
    private String strsoftware;
    
    @Before
    public void setUp() throws Exception {
        msoffice = new Software();
        msoffice.setKey(14L);
        msoffice.setName("Microsoft Office");
        msoffice.setCost(89.99);
        msoffice.setDescription("Professional Version");
        msoffice.setExpirationDate(Date.valueOf("2017-01-01"));
        msoffice.setLicenseKey("3KJ3-98NN-234C-23KD-ASLK");
        msoffice.setPurchasedDate(Date.valueOf("2013-02-21"));
        msoffice.setSerialNo("KJKHFAK2498SDF");
        msoffice.setVersion("2013");
        
        msword = new Software();
        msword.setName("Microsoft Word");
        msword.setCost(39.99);
        msword.setDescription("Professional Version");
        msword.setExpirationDate(Date.valueOf("2017-01-01"));
        msword.setLicenseKey("3KJ3-98NN-234C-23KD-ASLK");
        msword.setPurchasedDate(Date.valueOf("2013-02-21"));
        msword.setSerialNo("KJKHFAK2498SDF");
        msword.setVersion("2013");
        
        nosoftware = null;
        strsoftware = "Microsoft";
    }
    
    /**
     * Tests the equals method in the Software class.
     */
    @Test
    public void testEquals() {
    
        //Check for null
        assertFalse(msoffice.equals(nosoftware));
        
        //check for different class
        assertFalse(msoffice.equals(strsoftware));
        
        //Same key, name, and dates
        Software mscopy = new Software();
        mscopy.setKey(msoffice.getKey());
        mscopy.setName(msoffice.getName());
        mscopy.setPurchasedDate(msoffice.getPurchasedDate());
        mscopy.setExpirationDate(msoffice.getExpirationDate());
        mscopy.setVersion("2011"); //different from msoffice
        assertTrue(msoffice.equals(mscopy));
        
        //Change key
        mscopy.setKey(12L);
        assertFalse(msoffice.equals(mscopy));
    }
    
}
