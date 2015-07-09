package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestRefPermissionDao {

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
        RefPermissionDao refPermissionDao = session.getMapper( RefPermissionDao.class );

        try {

            RefPermission refPermission = TestRefPermissionDao.createRefPermission();
            String where = "KEY='" + refPermission.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = refPermissionDao.create( refPermission );
            assertEquals( 1, count );
            assertNotNull( refPermission.getKey() );

            RefPermission readRecord = refPermissionDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( refPermission, readRecord );

            modifyRecord( refPermission );
            count = refPermissionDao.update( refPermission );
            assertEquals( 1, count );

            readRecord = refPermissionDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( refPermission, readRecord );

            count = refPermissionDao.delete( map );
            assertEquals( 1, count );

            readRecord = refPermissionDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static RefPermission createRefPermission() {
        RefPermission refPermission = new RefPermission();

        refPermission.setCode( randomString( "code", 40 ) );
        refPermission.setDescription( randomString( "description", 2000 ) );

        return refPermission;
    }

    public static void compareRecords( RefPermission refPermission, RefPermission readRecord ) {

        assertEquals( refPermission.getCode(), readRecord.getCode() );
        assertEquals( refPermission.getDescription(), readRecord.getDescription() );

    }

    public static void modifyRecord( RefPermission refPermission ) {

        refPermission.setCode( randomString( "code", 40 ) );
        refPermission.setDescription( randomString( "description", 2000 ) );

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