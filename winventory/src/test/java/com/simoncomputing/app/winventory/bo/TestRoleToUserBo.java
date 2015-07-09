package com.simoncomputing.app.winventory.bo;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestRoleToUserBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        RoleToUserBo roleToUserBo = RoleToUserBo.getInstance();

        RoleToUser roleToUser = TestRoleToUserDao.createRoleToUser();
        int count = roleToUserBo.create( roleToUser );
        assertEquals( 1, count );

        RoleToUser readRecord = roleToUserBo.read( roleToUser.getKey() );
        assertNotNull( readRecord.getKey() );

        TestRoleToUserDao.compareRecords( roleToUser, readRecord );

        List<RoleToUser> list1= roleToUserBo.getListByRoleId( roleToUser.getRoleId() ) ; 
        assertEquals( 1 , list1.size() );

        List<RoleToUser> list2= roleToUserBo.getListByUserId( roleToUser.getUserId() ) ; 
        assertEquals( 1 , list2.size() );

        TestRoleToUserDao.modifyRecord( roleToUser );
        count = roleToUserBo.update( roleToUser );
        assertEquals( 1, count );

        count = roleToUserBo.delete( roleToUser.getKey());
        assertEquals( 1, count );

        readRecord = roleToUserBo.read( roleToUser.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}