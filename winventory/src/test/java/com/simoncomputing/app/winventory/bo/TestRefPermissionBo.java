package com.simoncomputing.app.winventory.bo;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestRefPermissionBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        RefPermissionBo refPermissionBo = RefPermissionBo.getInstance();

        RefPermission refPermission = TestRefPermissionDao.createRefPermission();
        int count = refPermissionBo.create( refPermission );
        assertEquals( 1, count );

        RefPermission readRecord = refPermissionBo.read( refPermission.getKey() );
        assertNotNull( readRecord.getKey() );

        TestRefPermissionDao.compareRecords( refPermission, readRecord );

        TestRefPermissionDao.modifyRecord( refPermission );
        count = refPermissionBo.update( refPermission );
        assertEquals( 1, count );

        count = refPermissionBo.delete( refPermission.getKey());
        assertEquals( 1, count );

        readRecord = refPermissionBo.read( refPermission.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}