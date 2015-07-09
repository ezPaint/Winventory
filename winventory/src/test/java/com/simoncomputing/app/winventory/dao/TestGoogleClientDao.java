package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestGoogleClientDao {

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
        GoogleClientDao googleClientDao = session.getMapper( GoogleClientDao.class );

        try {

            GoogleClient googleClient = TestGoogleClientDao.createGoogleClient();
            String where = "KEY='" + googleClient.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = googleClientDao.create( googleClient );
            assertEquals( 1, count );
            assertNotNull( googleClient.getKey() );

            GoogleClient readRecord = googleClientDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( googleClient, readRecord );

            modifyRecord( googleClient );
            count = googleClientDao.update( googleClient );
            assertEquals( 1, count );

            readRecord = googleClientDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( googleClient, readRecord );

            count = googleClientDao.delete( map );
            assertEquals( 1, count );

            readRecord = googleClientDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static GoogleClient createGoogleClient() {
        GoogleClient googleClient = new GoogleClient();

        googleClient.setClientId( randomString( "clientId", 100 ) );
        googleClient.setClientSecret( randomString( "clientSecret", 50 ) );

        return googleClient;
    }

    public static void compareRecords( GoogleClient googleClient, GoogleClient readRecord ) {

        assertEquals( googleClient.getClientId(), readRecord.getClientId() );
        assertEquals( googleClient.getClientSecret(), readRecord.getClientSecret() );

    }

    public static void modifyRecord( GoogleClient googleClient ) {

        googleClient.setClientId( randomString( "clientId", 100 ) );
        googleClient.setClientSecret( randomString( "clientSecret", 50 ) );

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