package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestSmtpDao {

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
        SmtpDao smtpDao = session.getMapper( SmtpDao.class );

        try {

            Smtp smtp = TestSmtpDao.createSmtp();
            String where = "KEY='" + smtp.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = smtpDao.create( smtp );
            assertEquals( 1, count );
            assertNotNull( smtp.getKey() );

            Smtp readRecord = smtpDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( smtp, readRecord );

            modifyRecord( smtp );
            count = smtpDao.update( smtp );
            assertEquals( 1, count );

            readRecord = smtpDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( smtp, readRecord );

            count = smtpDao.delete( map );
            assertEquals( 1, count );

            readRecord = smtpDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static Smtp createSmtp() {
        Smtp smtp = new Smtp();

        smtp.setHostName( randomString( "hostName", 50 ) );
        smtp.setPort( randomNumber() );
        smtp.setAuthUserName( randomString( "authUserName", 50 ) );
        smtp.setAuthPassword( randomString( "authPassword", 50 ) );
        smtp.setSsl( true  );

        return smtp;
    }

    public static void compareRecords( Smtp smtp, Smtp readRecord ) {

        assertEquals( smtp.getHostName(), readRecord.getHostName() );
        assertEquals( smtp.getPort(), readRecord.getPort() );
        assertEquals( smtp.getAuthUserName(), readRecord.getAuthUserName() );
        assertEquals( smtp.getAuthPassword(), readRecord.getAuthPassword() );
        assertEquals( smtp.getSsl(), readRecord.getSsl() );

    }

    public static void modifyRecord( Smtp smtp ) {

        smtp.setHostName( randomString( "hostName", 50 ) );
        smtp.setPort( randomNumber() );
        smtp.setAuthUserName( randomString( "authUserName", 50 ) );
        smtp.setAuthPassword( randomString( "authPassword", 50 ) );
        smtp.setSsl( true  );

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