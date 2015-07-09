package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestEventToLocationDao {

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
        EventToLocationDao eventToLocationDao = session.getMapper( EventToLocationDao.class );

        try {

            EventToLocation eventToLocation = TestEventToLocationDao.createEventToLocation();
            String where = "KEY='" + eventToLocation.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = eventToLocationDao.create( eventToLocation );
            assertEquals( 1, count );
            assertNotNull( eventToLocation.getKey() );

            EventToLocation readRecord = eventToLocationDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( eventToLocation, readRecord );

            List<EventToLocation> list1= eventToLocationDao.getListByEventId( eventToLocation.getEventId() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( eventToLocation, list1.get( 0 ) );

            List<EventToLocation> list2= eventToLocationDao.getListByLocationId( eventToLocation.getLocationId() ) ; 
            assertEquals( 1, list2.size() );
            compareRecords( eventToLocation, list2.get( 0 ) );

            modifyRecord( eventToLocation );
            count = eventToLocationDao.update( eventToLocation );
            assertEquals( 1, count );

            readRecord = eventToLocationDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( eventToLocation, readRecord );

            count = eventToLocationDao.delete( map );
            assertEquals( 1, count );

            readRecord = eventToLocationDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static EventToLocation createEventToLocation() {
        EventToLocation eventToLocation = new EventToLocation();

        eventToLocation.setEventId( (long) 0 );
        eventToLocation.setLocationId( (long) 0 );

        return eventToLocation;
    }

    public static void compareRecords( EventToLocation eventToLocation, EventToLocation readRecord ) {

        assertEquals( eventToLocation.getEventId(), readRecord.getEventId() );
        assertEquals( eventToLocation.getLocationId(), readRecord.getLocationId() );

    }

    public static void modifyRecord( EventToLocation eventToLocation ) {

        eventToLocation.setEventId( (long) 0 );
        eventToLocation.setLocationId( (long) 0 );

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