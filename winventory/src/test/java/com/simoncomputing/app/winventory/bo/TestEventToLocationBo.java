/*package com.simoncomputing.app.winventory.bo;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestEventToLocationBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        EventToLocationBo eventToLocationBo = EventToLocationBo.getInstance();

        EventToLocation eventToLocation = TestEventToLocationDao.createEventToLocation();
        int count = eventToLocationBo.create( eventToLocation );
        assertEquals( 1, count );

        EventToLocation readRecord = eventToLocationBo.read( eventToLocation.getKey() );
        assertNotNull( readRecord.getKey() );

        TestEventToLocationDao.compareRecords( eventToLocation, readRecord );

        List<EventToLocation> list1= eventToLocationBo.getListByEventId( eventToLocation.getEventId() ) ; 
        assertEquals( 1 , list1.size() );

        List<EventToLocation> list2= eventToLocationBo.getListByLocationId( eventToLocation.getLocationId() ) ; 
        assertEquals( 1 , list2.size() );

        TestEventToLocationDao.modifyRecord( eventToLocation );
        count = eventToLocationBo.update( eventToLocation );
        assertEquals( 1, count );

        count = eventToLocationBo.delete( eventToLocation.getKey());
        assertEquals( 1, count );

        readRecord = eventToLocationBo.read( eventToLocation.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}*/