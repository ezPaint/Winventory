package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestRoleDao {

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
        RoleDao roleDao = session.getMapper( RoleDao.class );

        try {

            Role role = TestRoleDao.createRole();
            String where = "KEY='" + role.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = roleDao.create( role );
            assertEquals( 1, count );
            assertNotNull( role.getKey() );

            Role readRecord = roleDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( role, readRecord );

            modifyRecord( role );
            count = roleDao.update( role );
            assertEquals( 1, count );

            readRecord = roleDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( role, readRecord );

            count = roleDao.delete( map );
            assertEquals( 1, count );

            readRecord = roleDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static Role createRole() {
        Role role = new Role();

        role.setTitle( randomString( "title", 30 ) );

        return role;
    }

    public static void compareRecords( Role role, Role readRecord ) {

        assertEquals( role.getTitle(), readRecord.getTitle() );

    }

    public static void modifyRecord( Role role ) {

        role.setTitle( randomString( "title", 30 ) );

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