package com.simoncomputing.app.winventory.bo;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestLocationBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        LocationBo locationBo = LocationBo.getInstance();

        Location location = TestLocationDao.createLocation();
        int count = locationBo.create( location );
        assertEquals( 1, count );

        Location readRecord = locationBo.read( location.getKey() );
        assertNotNull( readRecord.getKey() );

        TestLocationDao.compareRecords( location, readRecord );

        List<Location> list1= locationBo.getListByIsActive( location.getIsActive() ) ; 
        assertEquals( 1 , list1.size() );

        List<Location> list2= locationBo.getListByAddressId( location.getAddressId() ) ; 
        assertEquals( 1 , list2.size() );

        TestLocationDao.modifyRecord( location );
        count = locationBo.update( location );
        assertEquals( 1, count );

        count = locationBo.delete( location.getKey());
        assertEquals( 1, count );

        readRecord = locationBo.read( location.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}