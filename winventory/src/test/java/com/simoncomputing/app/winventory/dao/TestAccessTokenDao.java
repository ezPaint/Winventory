package com.simoncomputing.app.winventory.dao;

import java.util.Date;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestAccessTokenDao {

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
        AccessTokenDao accessTokenDao = session.getMapper( AccessTokenDao.class );

        try {

            AccessToken accessToken = TestAccessTokenDao.createAccessToken();
            String where = "USER_KEY='" + accessToken.getUserKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = accessTokenDao.create( accessToken );
            assertEquals( 1, count );
            assertNotNull( accessToken.getUserKey() );

            AccessToken readRecord = accessTokenDao.read( map );
            assertNotNull( readRecord.getUserKey() );

            compareRecords( accessToken, readRecord );

            modifyRecord( accessToken );
            count = accessTokenDao.update( accessToken );
            assertEquals( 1, count );

            readRecord = accessTokenDao.read( map );
            assertNotNull( readRecord.getUserKey() );

            compareRecords( accessToken, readRecord );

            count = accessTokenDao.delete( map );
            assertEquals( 1, count );

            readRecord = accessTokenDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static AccessToken createAccessToken() {
        AccessToken accessToken = new AccessToken();

        accessToken.setUserKey( randomNumber() );
        accessToken.setToken( randomString( "token", 200 ) );
        accessToken.setExpiration( new Date() );

        return accessToken;
    }

    public static void compareRecords( AccessToken accessToken, AccessToken readRecord ) {

        assertEquals( accessToken.getToken(), readRecord.getToken() );
        assertNotSame( accessToken.getExpiration(), readRecord.getExpiration() );

    }

    public static void modifyRecord( AccessToken accessToken ) {

        accessToken.setToken( randomString( "token", 200 ) );
        accessToken.setExpiration( new Date() );

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