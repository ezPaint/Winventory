package com.simoncomputing.app.winventory.bo;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestSoftwareBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        SoftwareBo softwareBo = SoftwareBo.getInstance();

        Software software = TestSoftwareDao.createSoftware();
        int count = softwareBo.create( software );
        assertEquals( 1, count );

        Software readRecord = softwareBo.read( software.getKey() );
        assertNotNull( readRecord.getKey() );

        TestSoftwareDao.compareRecords( software, readRecord );

        List<Software> list1= softwareBo.getListByName( software.getName() ) ; 
        assertEquals( 1 , list1.size() );

        List<Software> list2= softwareBo.getListBySerialNo( software.getSerialNo() ) ; 
        assertEquals( 1 , list2.size() );

        List<Software> list3= softwareBo.getListByLicenseKey( software.getLicenseKey() ) ; 
        assertEquals( 1 , list3.size() );

        List<Software> list4= softwareBo.getListByPurchasedDate( software.getPurchasedDate() ) ; 
        assertEquals( 1 , list4.size() );

        List<Software> list5= softwareBo.getListByExpirationDate( software.getExpirationDate() ) ; 
        assertEquals( 1 , list5.size() );

        TestSoftwareDao.modifyRecord( software );
        count = softwareBo.update( software );
        assertEquals( 1, count );

        count = softwareBo.delete( software.getKey());
        assertEquals( 1, count );

        readRecord = softwareBo.read( software.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->
    private static long KEY = 25L;
    
    /**
     * Test for ensuring a software object is added to the database.
     * Software object being added has all fields filled.
     */
    @Test
    public void testAddFull(){
        //Software object to test with
        Software microsoft = new Software();
        microsoft.setCost(49.99);
        microsoft.setDescription("Home and Student version");
        microsoft.setExpirationDate(Date.valueOf("2016-12-31"));
        microsoft.setLicenseKey("JSDN1203948209");
        microsoft.setName("Microsoft Office");
        microsoft.setPurchasedDate(Date.valueOf("2015-03-12"));
        microsoft.setSerialNo("2393-2342-5464-2634-6349");
        microsoft.setVersion("2013");
        
        //Attempt to access the database
        SoftwareBo softwareBo = SoftwareBo.getInstance();
        try {
            softwareBo.create(microsoft);
        } catch (BoException e) {
            fail();
        }
        
        //Attempt to retrieve software object.
        //Make sure to update KEY so that you're accessing the correct token!
        Software retrieved = null;
        try {
            retrieved = softwareBo.read(KEY);
            KEY++;
            System.out.println(KEY);
        } catch (BoException e) {
            fail();
        }
        assertNotNull(retrieved);
    }
    
    /**
     * Test for ensuring a software object is added to the database.
     * Software object being added has all fields are empty.
     */
    @Test
    public void testAddEmpty(){
        //Software object to test with
        Software microsoft = new Software();
        
        //Attempt to access the database
        SoftwareBo softwareBo = SoftwareBo.getInstance();
        try {
            softwareBo.create(microsoft);
        } catch (BoException e) {
            fail();
        }
        
        //Attempt to retrieve software object.
        //Make sure to update KEY so that you're accessing the correct token!
        Software retrieved = null;
        try {
            retrieved = softwareBo.read(KEY);
            KEY++;
            System.out.println(KEY);
        } catch (BoException e) {
            fail();
        }
        assertNotNull(retrieved);
    }
    
    /**
     * Test for ensuring a software object is added to the database.
     * Software object being added has some fields filled.
     */
    @Test
    public void testAddHalf(){
        //Software object to test with
        Software microsoft = new Software();
        microsoft.setCost(49.99);
        microsoft.setLicenseKey("JSDN1203948209");
        microsoft.setName("Microsoft Office");
        microsoft.setPurchasedDate(Date.valueOf("2015-03-12"));
        
        //Attempt to access the database
        SoftwareBo softwareBo = SoftwareBo.getInstance();
        try {
            softwareBo.create(microsoft);
        } catch (BoException e) {
            fail();
        }
        
        //Attempt to retrieve software object.
        //Make sure to update KEY so that you're accessing the correct token!
        Software retrieved = null;
        try {
            retrieved = softwareBo.read(KEY);
            KEY++;
            System.out.println(KEY);
        } catch (BoException e) {
            fail();
        }
        assertNotNull(retrieved);
    }
}