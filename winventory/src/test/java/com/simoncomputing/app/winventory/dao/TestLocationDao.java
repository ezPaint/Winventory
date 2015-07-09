package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestLocationDao {

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
        LocationDao locationDao = session.getMapper( LocationDao.class );

        try {

            Location location = TestLocationDao.createLocation();
            String where = "KEY='" + location.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = locationDao.create( location );
            assertEquals( 1, count );
            assertNotNull( location.getKey() );

            Location readRecord = locationDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( location, readRecord );

            List<Location> list1= locationDao.getListByAddressId( location.getAddressId() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( location, list1.get( 0 ) );

            modifyRecord( location );
            count = locationDao.update( location );
            assertEquals( 1, count );

            readRecord = locationDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( location, readRecord );

            count = locationDao.delete( map );
            assertEquals( 1, count );

            readRecord = locationDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static Location createLocation() {
        Location location = new Location();

        location.setDescription( randomString( "description", 2000 ) );
        location.setIsActive( true  );
        location.setAddressId( randomNumber() );

        return location;
    }

    public static void compareRecords( Location location, Location readRecord ) {

        assertEquals( location.getDescription(), readRecord.getDescription() );
        assertEquals( location.getIsActive(), readRecord.getIsActive() );
        assertEquals( location.getAddressId(), readRecord.getAddressId() );

    }

    public static void modifyRecord( Location location ) {

        location.setDescription( randomString( "description", 2000 ) );
        location.setIsActive( true  );
        location.setAddressId( randomNumber() );

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