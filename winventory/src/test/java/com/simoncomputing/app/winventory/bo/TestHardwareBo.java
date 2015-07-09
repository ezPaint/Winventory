package com.simoncomputing.app.winventory.bo;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestHardwareBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        HardwareBo hardwareBo = HardwareBo.getInstance();

        Hardware hardware = TestHardwareDao.createHardware();
        int count = hardwareBo.create( hardware );
        assertEquals( 1, count );

        Hardware readRecord = hardwareBo.read( hardware.getKey() );
        assertNotNull( readRecord.getKey() );

        TestHardwareDao.compareRecords( hardware, readRecord );

        List<Hardware> list1= hardwareBo.getListByLocationId( hardware.getLocationId() ) ; 
        assertEquals( 1 , list1.size() );

        List<Hardware> list2= hardwareBo.getListByUserId( hardware.getUserId() ) ; 
        assertEquals( 1 , list2.size() );

        TestHardwareDao.modifyRecord( hardware );
        count = hardwareBo.update( hardware );
        assertEquals( 1, count );

        count = hardwareBo.delete( hardware.getKey());
        assertEquals( 1, count );

        readRecord = hardwareBo.read( hardware.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}