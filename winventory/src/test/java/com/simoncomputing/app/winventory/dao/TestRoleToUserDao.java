package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestRoleToUserDao {

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
        RoleToUserDao roleToUserDao = session.getMapper( RoleToUserDao.class );

        try {

            RoleToUser roleToUser = TestRoleToUserDao.createRoleToUser();
            String where = "KEY='" + roleToUser.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = roleToUserDao.create( roleToUser );
            assertEquals( 1, count );
            assertNotNull( roleToUser.getKey() );

            RoleToUser readRecord = roleToUserDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( roleToUser, readRecord );

            List<RoleToUser> list1= roleToUserDao.getListByRoleId( roleToUser.getRoleId() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( roleToUser, list1.get( 0 ) );

            List<RoleToUser> list2= roleToUserDao.getListByUserId( roleToUser.getUserId() ) ; 
            assertEquals( 1, list2.size() );
            compareRecords( roleToUser, list2.get( 0 ) );

            modifyRecord( roleToUser );
            count = roleToUserDao.update( roleToUser );
            assertEquals( 1, count );

            readRecord = roleToUserDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( roleToUser, readRecord );

            count = roleToUserDao.delete( map );
            assertEquals( 1, count );

            readRecord = roleToUserDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static RoleToUser createRoleToUser() {
        RoleToUser roleToUser = new RoleToUser();

        roleToUser.setRoleId( randomNumber() );
        roleToUser.setUserId( randomNumber() );

        return roleToUser;
    }

    public static void compareRecords( RoleToUser roleToUser, RoleToUser readRecord ) {

        assertEquals( roleToUser.getRoleId(), readRecord.getRoleId() );
        assertEquals( roleToUser.getUserId(), readRecord.getUserId() );

    }

    public static void modifyRecord( RoleToUser roleToUser ) {

        roleToUser.setRoleId( randomNumber() );
        roleToUser.setUserId( randomNumber() );

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