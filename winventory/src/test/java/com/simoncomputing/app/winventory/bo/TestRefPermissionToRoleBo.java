package com.simoncomputing.app.winventory.bo;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestRefPermissionToRoleBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        RefPermissionToRoleBo refPermissionToRoleBo = RefPermissionToRoleBo.getInstance();

        RefPermissionToRole refPermissionToRole = TestRefPermissionToRoleDao.createRefPermissionToRole();
        int count = refPermissionToRoleBo.create( refPermissionToRole );
        assertEquals( 1, count );

        RefPermissionToRole readRecord = refPermissionToRoleBo.read( refPermissionToRole.getKey() );
        assertNotNull( readRecord.getKey() );

        TestRefPermissionToRoleDao.compareRecords( refPermissionToRole, readRecord );

        List<RefPermissionToRole> list1= refPermissionToRoleBo.getListByPermissionId( refPermissionToRole.getPermissionId() ) ; 
        assertEquals( 1 , list1.size() );

        List<RefPermissionToRole> list2= refPermissionToRoleBo.getListByRoleId( refPermissionToRole.getRoleId() ) ; 
        assertEquals( 1 , list2.size() );

        TestRefPermissionToRoleDao.modifyRecord( refPermissionToRole );
        count = refPermissionToRoleBo.update( refPermissionToRole );
        assertEquals( 1, count );

        count = refPermissionToRoleBo.delete( refPermissionToRole.getKey());
        assertEquals( 1, count );

        readRecord = refPermissionToRoleBo.read( refPermissionToRole.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}