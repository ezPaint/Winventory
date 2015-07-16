package com.simoncomputing.app.winventory.bo;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestHardwareToSoftwareBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        HardwareToSoftwareBo hardwareToSoftwareBo = HardwareToSoftwareBo.getInstance();

        HardwareToSoftware hardwareToSoftware = TestHardwareToSoftwareDao.createHardwareToSoftware();
        int count = hardwareToSoftwareBo.create( hardwareToSoftware );
        assertEquals( 1, count );

        HardwareToSoftware readRecord = hardwareToSoftwareBo.read( hardwareToSoftware.getHardwareId(), hardwareToSoftware.getSoftwareId() );
        assertNotNull( readRecord.getHardwareId() );

        TestHardwareToSoftwareDao.compareRecords( hardwareToSoftware, readRecord );

        List<HardwareToSoftware> list1= hardwareToSoftwareBo.getListByHardwareId( hardwareToSoftware.getHardwareId() ) ; 
        assertEquals( 1 , list1.size() );

        List<HardwareToSoftware> list2= hardwareToSoftwareBo.getListBySoftwareId( hardwareToSoftware.getSoftwareId() ) ; 
        assertEquals( 1 , list2.size() );

        TestHardwareToSoftwareDao.modifyRecord( hardwareToSoftware );
        count = hardwareToSoftwareBo.update( hardwareToSoftware );
        assertEquals( 1, count );

        count = hardwareToSoftwareBo.delete( hardwareToSoftware.getHardwareId(), hardwareToSoftware.getSoftwareId());
        assertEquals( 1, count );

        readRecord = hardwareToSoftwareBo.read( hardwareToSoftware.getHardwareId(), hardwareToSoftware.getSoftwareId());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}