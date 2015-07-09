package com.simoncomputing.app.winventory.bo;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestTeamToUserBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        TeamToUserBo teamToUserBo = TeamToUserBo.getInstance();

        TeamToUser teamToUser = TestTeamToUserDao.createTeamToUser();
        int count = teamToUserBo.create( teamToUser );
        assertEquals( 1, count );

        TeamToUser readRecord = teamToUserBo.read( teamToUser.getKey() );
        assertNotNull( readRecord.getKey() );

        TestTeamToUserDao.compareRecords( teamToUser, readRecord );

        List<TeamToUser> list1= teamToUserBo.getListByUserId( teamToUser.getUserId() ) ; 
        assertEquals( 1 , list1.size() );

        List<TeamToUser> list2= teamToUserBo.getListByTeamId( teamToUser.getTeamId() ) ; 
        assertEquals( 1 , list2.size() );

        TestTeamToUserDao.modifyRecord( teamToUser );
        count = teamToUserBo.update( teamToUser );
        assertEquals( 1, count );

        count = teamToUserBo.delete( teamToUser.getKey());
        assertEquals( 1, count );

        readRecord = teamToUserBo.read( teamToUser.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}