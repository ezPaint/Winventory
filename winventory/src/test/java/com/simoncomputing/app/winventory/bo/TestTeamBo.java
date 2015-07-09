package com.simoncomputing.app.winventory.bo;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestTeamBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        TeamBo teamBo = TeamBo.getInstance();

        Team team = TestTeamDao.createTeam();
        int count = teamBo.create( team );
        assertEquals( 1, count );

        Team readRecord = teamBo.read( team.getKey() );
        assertNotNull( readRecord.getKey() );

        TestTeamDao.compareRecords( team, readRecord );

        List<Team> list1= teamBo.getListByLeaderId( team.getLeaderId() ) ; 
        assertEquals( 1 , list1.size() );

        TestTeamDao.modifyRecord( team );
        count = teamBo.update( team );
        assertEquals( 1, count );

        count = teamBo.delete( team.getKey());
        assertEquals( 1, count );

        readRecord = teamBo.read( team.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}