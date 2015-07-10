package com.simoncomputing.app.winventory.dao;

import java.sql.Date;
import java.util.Random;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestSoftwareDao {

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
        SoftwareDao softwareDao = session.getMapper( SoftwareDao.class );

        try {

            Software software = TestSoftwareDao.createSoftware();
            String where = "KEY='" + software.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = softwareDao.create( software );
            assertEquals( 1, count );
            assertNotNull( software.getKey() );

            Software readRecord = softwareDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( software, readRecord );

            List<Software> list1= softwareDao.getListByName( software.getName() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( software, list1.get( 0 ) );

            List<Software> list2= softwareDao.getListBySerialNo( software.getSerialNo() ) ; 
            assertEquals( 1, list2.size() );
            compareRecords( software, list2.get( 0 ) );

            List<Software> list3= softwareDao.getListByLicenseKey( software.getLicenseKey() ) ; 
            assertEquals( 1, list3.size() );
            compareRecords( software, list3.get( 0 ) );

            List<Software> list4= softwareDao.getListByPurchasedDate( software.getPurchasedDate() ) ; 
            assertEquals( 1, list4.size() );
            compareRecords( software, list4.get( 0 ) );

            List<Software> list5= softwareDao.getListByExpirationDate( software.getExpirationDate() ) ; 
            assertEquals( 1, list5.size() );
            compareRecords( software, list5.get( 0 ) );

            modifyRecord( software );
            count = softwareDao.update( software );
            assertEquals( 1, count );

            readRecord = softwareDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( software, readRecord );

            count = softwareDao.delete( map );
            assertEquals( 1, count );

            readRecord = softwareDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static Software createSoftware() {
        Software software = new Software();

        software.setName( randomString( "name", 30 ) );
        software.setSerialNo( randomString( "serialNo", 30 ) );
        software.setVersion( randomString( "version", 20 ) );
        software.setLicenseKey( randomString( "licenseKey", 30 ) );
        software.setDescription( randomString( "description", 2000 ) );
        software.setCost( (double) randomNumber() );
        software.setPurchasedDate( new Date(0) );
        software.setExpirationDate( new Date(0) );

        return software;
    }

    public static void compareRecords( Software software, Software readRecord ) {

        assertEquals( software.getName(), readRecord.getName() );
        assertEquals( software.getSerialNo(), readRecord.getSerialNo() );
        assertEquals( software.getVersion(), readRecord.getVersion() );
        assertEquals( software.getLicenseKey(), readRecord.getLicenseKey() );
        assertEquals( software.getDescription(), readRecord.getDescription() );
        assertEquals( software.getCost(), readRecord.getCost() );
        assertNotSame( software.getPurchasedDate(), readRecord.getPurchasedDate() );
        assertNotSame( software.getExpirationDate(), readRecord.getExpirationDate() );

    }

    public static void modifyRecord( Software software ) {

        software.setName( randomString( "name", 30 ) );
        software.setSerialNo( randomString( "serialNo", 30 ) );
        software.setVersion( randomString( "version", 20 ) );
        software.setLicenseKey( randomString( "licenseKey", 30 ) );
        software.setDescription( randomString( "description", 2000 ) );
        software.setCost( (double) randomNumber() );
        software.setPurchasedDate( new Date(0) );
        software.setExpirationDate( new Date(0) );

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