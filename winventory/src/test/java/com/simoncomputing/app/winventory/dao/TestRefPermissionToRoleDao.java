package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestRefPermissionToRoleDao {

    private static StringBuilder sb = new StringBuilder();
    private static String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static Random random = new Random();

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws Exception {

        SqlSession session = SessionFactory.getSession();
        RefPermissionToRoleDao refPermissionToRoleDao = session.getMapper( RefPermissionToRoleDao.class );

        try {

            RefPermissionToRole refPermissionToRole = TestRefPermissionToRoleDao.createRefPermissionToRole();
            String where = "KEY='" + refPermissionToRole.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = refPermissionToRoleDao.create( refPermissionToRole );
            assertEquals( 1, count );
            assertNotNull( refPermissionToRole.getKey() );

            RefPermissionToRole readRecord = refPermissionToRoleDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( refPermissionToRole, readRecord );

            List<RefPermissionToRole> list1= refPermissionToRoleDao.getListByPermissionId( refPermissionToRole.getPermissionId() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( refPermissionToRole, list1.get( 0 ) );

            List<RefPermissionToRole> list2= refPermissionToRoleDao.getListByRoleId( refPermissionToRole.getRoleId() ) ; 
            assertEquals( 1, list2.size() );
            compareRecords( refPermissionToRole, list2.get( 0 ) );

            modifyRecord( refPermissionToRole );
            count = refPermissionToRoleDao.update( refPermissionToRole );
            assertEquals( 1, count );

            readRecord = refPermissionToRoleDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( refPermissionToRole, readRecord );

            count = refPermissionToRoleDao.delete( map );
            assertEquals( 1, count );

            readRecord = refPermissionToRoleDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static RefPermissionToRole createRefPermissionToRole() {
        RefPermissionToRole refPermissionToRole = new RefPermissionToRole();

        refPermissionToRole.setPermissionId( randomNumber() );
        refPermissionToRole.setRoleId( randomNumber() );

        return refPermissionToRole;
    }

    public static void compareRecords( RefPermissionToRole refPermissionToRole, RefPermissionToRole readRecord ) {

        assertEquals( refPermissionToRole.getPermissionId(), readRecord.getPermissionId() );
        assertEquals( refPermissionToRole.getRoleId(), readRecord.getRoleId() );

    }

    public static void modifyRecord( RefPermissionToRole refPermissionToRole ) {

        refPermissionToRole.setPermissionId( randomNumber() );
        refPermissionToRole.setRoleId( randomNumber() );

    }

    public static int randomNumber() {

        return (int) ( Math.random() * 10 ) + 0;

    }

    public static String randomString( String fldName, int length ) {

        if ( fldName.length() >= length ) {
            return fldName.substring( 0, length );
        }

        sb.setLength( 0 );
        sb.append( fldName );
        for ( int i = fldName.length(); i < length; i++ ) {
            sb.append( chars.charAt( random.nextInt( chars.length() ) ) );
        }
        return sb.toString();
    }

    public static byte[] randomByteArray( int length ) {

        byte[] byteArray = new byte[length];
        random.nextBytes( byteArray );
        return byteArray;
    }
    // PROTECTED CODE -->

}