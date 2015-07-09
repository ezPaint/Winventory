package com.simoncomputing.app.winventory.bo;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestRoleBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        RoleBo roleBo = RoleBo.getInstance();

        Role role = TestRoleDao.createRole();
        int count = roleBo.create( role );
        assertEquals( 1, count );

        Role readRecord = roleBo.read( role.getKey() );
        assertNotNull( readRecord.getKey() );

        TestRoleDao.compareRecords( role, readRecord );

        TestRoleDao.modifyRecord( role );
        count = roleBo.update( role );
        assertEquals( 1, count );

        count = roleBo.delete( role.getKey());
        assertEquals( 1, count );

        readRecord = roleBo.read( role.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}