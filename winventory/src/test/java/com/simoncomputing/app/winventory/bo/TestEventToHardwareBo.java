/*package com.simoncomputing.app.winventory.bo;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestEventToHardwareBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        EventToHardwareBo eventToHardwareBo = EventToHardwareBo.getInstance();

        EventToHardware eventToHardware = TestEventToHardwareDao.createEventToHardware();
        int count = eventToHardwareBo.create( eventToHardware );
        assertEquals( 1, count );

        EventToHardware readRecord = eventToHardwareBo.read( eventToHardware.getKey() );
        assertNotNull( readRecord.getKey() );

        TestEventToHardwareDao.compareRecords( eventToHardware, readRecord );

        List<EventToHardware> list1= eventToHardwareBo.getListByEventId( eventToHardware.getEventId() ) ; 
        assertEquals( 1 , list1.size() );

        List<EventToHardware> list2= eventToHardwareBo.getListByHardwareId( eventToHardware.getHardwareId() ) ; 
        assertEquals( 1 , list2.size() );

        TestEventToHardwareDao.modifyRecord( eventToHardware );
        count = eventToHardwareBo.update( eventToHardware );
        assertEquals( 1, count );

        count = eventToHardwareBo.delete( eventToHardware.getKey());
        assertEquals( 1, count );

        readRecord = eventToHardwareBo.read( eventToHardware.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}*/