package com.simoncomputing.app.winventory.bo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.dao.SessionFactory;
import com.simoncomputing.app.winventory.dao.TestSoftwareDao;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.BoException;
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

    /**
     * Tests that negatives costs are searches correctly
     * @throws BoException
     */
    @Test
    public void testNegativeCost() throws BoException {
    	// Create sample software to test with
    	Software sw = TestSoftwareDao.createSoftware();
    	sw.setCost(-2.0);
    	sw.setName("NegativeCostTest");
    	
    	Software sw2 = TestSoftwareDao.createSoftware();
    	sw2.setCost(-1.0);
    	sw.setName("NegativeCostTest2");
    	
    	SoftwareBo.getInstance().create(sw);
    	
    	// Create Software arraylist to search
    	ArrayList<Software> searchIt = new ArrayList<Software>();
    	searchIt.add(sw);
    	searchIt.add(sw2);
    	
    	String minCost = "-2.0";
    	String maxCost = "-1.0";
    	
    	List<Software> results = SoftwareBo.getInstance().getListByCostRange(minCost, maxCost);
    	assertNotNull( results );
    	assertTrue( results.size() == 0);
    	
    }
    /**
     * Tests that searching by a full date range works correctly
     * 
     * @throws BoException
     */
    @Test
    public void testFullDateRange() throws BoException {
        // Create sample Software objects to test with
        Software s1 = TestSoftwareDao.createSoftware();
        s1.setName("s1");
        s1.setPurchasedDate(Date.valueOf("2015-07-10"));
        s1.setExpirationDate(Date.valueOf("2016-07-10"));

        Software s2 = TestSoftwareDao.createSoftware();
        s2.setName("s2");
        s2.setPurchasedDate(Date.valueOf("2015-07-01"));
        s2.setExpirationDate(Date.valueOf("2016-08-01"));

        SoftwareBo.getInstance().create(s1);
        SoftwareBo.getInstance().create(s2);

        // Create list of Software objects to search from
        ArrayList<Software> searches = new ArrayList<Software>();
        searches.add(s1);
        searches.add(s2);

        // Create Date range to search by
        ArrayList<String> dates = new ArrayList<String>();
        dates.add("2015-07-01");
        dates.add("2015-07-31");
        dates.add("2016-07-01");
        dates.add("2016-07-31");

        List<Software> results = SoftwareBo.getInstance().searchDateRange(searches, dates);
        
        assertNotNull(results);
        assertTrue(results.size() == 1);

        assertEquals("s1", results.get(0).getName());
        assertEquals(Date.valueOf("2015-07-10"), results.get(0).getPurchasedDate());
        assertEquals(Date.valueOf("2016-07-10"), results.get(0).getExpirationDate());

    }

    /**
     * Tests that searching by just a purchased range works correctly
     * 
     * @throws BoException
     */
    @Test
    public void testPurchasedRange() throws BoException {
        // Create sample Software objects to test with
        Software s1 = TestSoftwareDao.createSoftware();
        s1.setName("s1");
        s1.setPurchasedDate(Date.valueOf("2015-07-10"));
        s1.setExpirationDate(Date.valueOf("2016-07-10"));

        Software s2 = TestSoftwareDao.createSoftware();
        s2.setName("s2");
        s2.setPurchasedDate(Date.valueOf("2015-07-01"));
        s2.setExpirationDate(Date.valueOf("2016-08-01"));

        SoftwareBo.getInstance().create(s1);
        SoftwareBo.getInstance().create(s2);

        // Create list of Software objects to search from
        ArrayList<Software> searches = new ArrayList<Software>();
        searches.add(s1);
        searches.add(s2);

        // Create Date range to search by
        ArrayList<String> dates = new ArrayList<String>();
        dates.add("2015-06-01");
        dates.add("2015-07-31");
        dates.add("");
        dates.add("");

        List<Software> results = SoftwareBo.getInstance().searchDateRange(searches, dates);
        
        assertNotNull(results);
        assertTrue(results.size() == 2);

        assertEquals("s1", results.get(0).getName());
        assertEquals(Date.valueOf("2015-07-10"), results.get(0).getPurchasedDate());
        assertEquals(Date.valueOf("2016-07-10"), results.get(0).getExpirationDate());

        assertEquals("s2", results.get(1).getName());
        assertEquals(Date.valueOf("2015-07-01"), results.get(1).getPurchasedDate());
        assertEquals(Date.valueOf("2016-08-01"), results.get(1).getExpirationDate());
    }

    /**
     * Tests that searching by just an expiration range works correctly
     * 
     * @throws BoException
     */
    @Test
    public void testExpirationRange() throws BoException {
        // Create sample Software objects to test with
        Software s1 = TestSoftwareDao.createSoftware();
        s1.setName("s1");
        s1.setPurchasedDate(Date.valueOf("2015-07-10"));
        s1.setExpirationDate(Date.valueOf("2016-07-10"));

        Software s2 = TestSoftwareDao.createSoftware();
        s2.setName("s2");
        s2.setPurchasedDate(Date.valueOf("2015-07-01"));
        s2.setExpirationDate(Date.valueOf("2016-08-01"));

        SoftwareBo.getInstance().create(s1);
        SoftwareBo.getInstance().create(s2);

        // Create list of Software objects to search from
        ArrayList<Software> searches = new ArrayList<Software>();
        searches.add(s1);
        searches.add(s2);

        // Create Date range to search by
        ArrayList<String> dates = new ArrayList<String>();
        dates.add("");
        dates.add("");
        dates.add("2016-07-01");
        dates.add("2016-08-31");

        List<Software> results = SoftwareBo.getInstance().searchDateRange(searches, dates);

        assertNotNull(results);
        assertTrue(results.size() == 2);

        assertEquals("s1", results.get(0).getName());
        assertEquals(Date.valueOf("2015-07-10"), results.get(0).getPurchasedDate());
        assertEquals(Date.valueOf("2016-07-10"), results.get(0).getExpirationDate());

        assertEquals("s2", results.get(1).getName());
        assertEquals(Date.valueOf("2015-07-01"), results.get(1).getPurchasedDate());
        assertEquals(Date.valueOf("2016-08-01"), results.get(1).getExpirationDate());
    }

    /**
     * Tests that providing no date range works correctly
     * @throws BoException 
     */
    @Test
    public void testEmptyRange() throws BoException {
        // Create sample Software objects to test with
        Software s1 = TestSoftwareDao.createSoftware();
        s1.setName("s1");
        s1.setPurchasedDate(Date.valueOf("2015-07-10"));
        s1.setExpirationDate(Date.valueOf("2016-07-10"));

        Software s2 = TestSoftwareDao.createSoftware();
        s2.setName("s2");
        s2.setPurchasedDate(Date.valueOf("2015-07-01"));
        s2.setExpirationDate(Date.valueOf("2016-08-01"));

        SoftwareBo.getInstance().create(s1);
        SoftwareBo.getInstance().create(s2);

        // Create list of Software objects to search from
        ArrayList<Software> searches = new ArrayList<Software>();
        searches.add(s1);
        searches.add(s2);

        // Create Date range to search by
        ArrayList<String> dates = new ArrayList<String>();
        dates.add("");
        dates.add("");
        dates.add("");
        dates.add("");

        List<Software> results = SoftwareBo.getInstance().searchDateRange(searches, dates);

        assertNotNull(results);
        assertTrue(results.size() == 0);
    }
    
    /**
     * Tests that providing a cost range where the max is less than the min.
     * @throws BoException 
     */
    @Test
    public void testSwappedCost() throws BoException {
        // Create sample Software objects to test with
        Software s1 = TestSoftwareDao.createSoftware();
        s1.setName("s1");
        s1.setCost(89.99);

        Software s2 = TestSoftwareDao.createSoftware();
        s2.setName("s2");
        s2.setCost(59.99);
        
        SoftwareBo.getInstance().create(s1);
        SoftwareBo.getInstance().create(s2);

        // Create list of Software objects to search from
        ArrayList<Software> searches = new ArrayList<Software>();
        searches.add(s1);
        searches.add(s2);

        // Create Cost range to search by
        ArrayList<String> costs = new ArrayList<String>();
        costs.add("90");
        costs.add("50");

        List<Software> results = SoftwareBo.getInstance().searchCostRange(searches, "90", "50");

        assertNotNull(results);
        assertTrue(results.size() == 0);
    }
    
    /**
     * Tests that providing a cost range returns the right number of entries.
     * @throws BoException 
     */
    @Test
    public void testCostRange() throws BoException {
        
        // Create sample Software objects to test with
        Software s1 = TestSoftwareDao.createSoftware();
        s1.setName("s1");
        s1.setCost(89.99);

        Software s2 = TestSoftwareDao.createSoftware();
        s2.setName("s2");
        s2.setCost(59.99);
        
        Software s3 = TestSoftwareDao.createSoftware();
        s3.setName("s3");
        s3.setCost(109.99);
        
        SoftwareBo.getInstance().create(s1);
        SoftwareBo.getInstance().create(s2);
        SoftwareBo.getInstance().create(s3);

        // Create list of Software objects to search from
        ArrayList<Software> searches = new ArrayList<Software>();
        searches.add(s1);
        searches.add(s2);
        searches.add(s3);

        List<Software> results = SoftwareBo.getInstance().searchCostRange(searches, "50", "90");

        assertNotNull(results);
        assertEquals(results.size(), 2);
        
        assertEquals("s1", results.get(0).getName());
        assertEquals(89.99, results.get(0).getCost(), 0.01);
        
        assertEquals("s2", results.get(1).getName());
        assertEquals(59.99, results.get(1).getCost(), 0.01);
    }
}