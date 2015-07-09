package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestAddressDao {

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
        AddressDao addressDao = session.getMapper( AddressDao.class );

        try {

            Address address = TestAddressDao.createAddress();
            String where = "KEY='" + address.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = addressDao.create( address );
            assertEquals( 1, count );
            assertNotNull( address.getKey() );

            Address readRecord = addressDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( address, readRecord );

            modifyRecord( address );
            count = addressDao.update( address );
            assertEquals( 1, count );

            readRecord = addressDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( address, readRecord );

            count = addressDao.delete( map );
            assertEquals( 1, count );

            readRecord = addressDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static Address createAddress() {
        Address address = new Address();

        address.setStreet1( randomString( "street1", 50 ) );
        address.setStreet2( randomString( "street2", 50 ) );
        address.setCity( randomString( "city", 50 ) );
        address.setState( randomString( "state", 50 ) );
        address.setZipcode( randomString( "zipcode", 10 ) );

        return address;
    }

    public static void compareRecords( Address address, Address readRecord ) {

        assertEquals( address.getStreet1(), readRecord.getStreet1() );
        assertEquals( address.getStreet2(), readRecord.getStreet2() );
        assertEquals( address.getCity(), readRecord.getCity() );
        assertEquals( address.getState(), readRecord.getState() );
        assertEquals( address.getZipcode(), readRecord.getZipcode() );

    }

    public static void modifyRecord( Address address ) {

        address.setStreet1( randomString( "street1", 50 ) );
        address.setStreet2( randomString( "street2", 50 ) );
        address.setCity( randomString( "city", 50 ) );
        address.setState( randomString( "state", 50 ) );
        address.setZipcode( randomString( "zipcode", 10 ) );

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