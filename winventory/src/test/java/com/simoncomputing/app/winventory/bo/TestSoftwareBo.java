package com.simoncomputing.app.winventory.bo;

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

}