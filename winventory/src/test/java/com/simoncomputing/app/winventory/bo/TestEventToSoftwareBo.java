/*package com.simoncomputing.app.winventory.bo;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestEventToSoftwareBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        EventToSoftwareBo eventToSoftwareBo = EventToSoftwareBo.getInstance();

        EventToSoftware eventToSoftware = TestEventToSoftwareDao.createEventToSoftware();
        int count = eventToSoftwareBo.create( eventToSoftware );
        assertEquals( 1, count );

        EventToSoftware readRecord = eventToSoftwareBo.read( eventToSoftware.getKey() );
        assertNotNull( readRecord.getKey() );

        TestEventToSoftwareDao.compareRecords( eventToSoftware, readRecord );

        List<EventToSoftware> list1= eventToSoftwareBo.getListByEventId( eventToSoftware.getEventId() ) ; 
        assertEquals( 1 , list1.size() );

        List<EventToSoftware> list2= eventToSoftwareBo.getListBySoftwareId( eventToSoftware.getSoftwareId() ) ; 
        assertEquals( 1 , list2.size() );

        TestEventToSoftwareDao.modifyRecord( eventToSoftware );
        count = eventToSoftwareBo.update( eventToSoftware );
        assertEquals( 1, count );

        count = eventToSoftwareBo.delete( eventToSoftware.getKey());
        assertEquals( 1, count );

        readRecord = eventToSoftwareBo.read( eventToSoftware.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}*/