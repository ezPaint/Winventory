package com.simoncomputing.app.winventory.bo;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestEventToUserBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        EventToUserBo eventToUserBo = EventToUserBo.getInstance();

        EventToUser eventToUser = TestEventToUserDao.createEventToUser();
        int count = eventToUserBo.create( eventToUser );
        assertEquals( 1, count );

        EventToUser readRecord = eventToUserBo.read( eventToUser.getKey() );
        assertNotNull( readRecord.getKey() );

        TestEventToUserDao.compareRecords( eventToUser, readRecord );

        List<EventToUser> list1= eventToUserBo.getListByEventId( eventToUser.getEventId() ) ; 
        assertEquals( 1 , list1.size() );

        List<EventToUser> list2= eventToUserBo.getListByUserId( eventToUser.getUserId() ) ; 
        assertEquals( 1 , list2.size() );

        TestEventToUserDao.modifyRecord( eventToUser );
        count = eventToUserBo.update( eventToUser );
        assertEquals( 1, count );

        count = eventToUserBo.delete( eventToUser.getKey());
        assertEquals( 1, count );

        readRecord = eventToUserBo.read( eventToUser.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}