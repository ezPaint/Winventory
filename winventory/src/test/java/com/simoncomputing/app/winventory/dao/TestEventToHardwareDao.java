package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestEventToHardwareDao {

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
        EventToHardwareDao eventToHardwareDao = session.getMapper( EventToHardwareDao.class );

        try {

            EventToHardware eventToHardware = TestEventToHardwareDao.createEventToHardware();
            String where = "KEY='" + eventToHardware.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = eventToHardwareDao.create( eventToHardware );
            assertEquals( 1, count );
            assertNotNull( eventToHardware.getKey() );

            EventToHardware readRecord = eventToHardwareDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( eventToHardware, readRecord );

            List<EventToHardware> list1= eventToHardwareDao.getListByEventId( eventToHardware.getEventId() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( eventToHardware, list1.get( 0 ) );

            List<EventToHardware> list2= eventToHardwareDao.getListByHardwareId( eventToHardware.getHardwareId() ) ; 
            assertEquals( 1, list2.size() );
            compareRecords( eventToHardware, list2.get( 0 ) );

            modifyRecord( eventToHardware );
            count = eventToHardwareDao.update( eventToHardware );
            assertEquals( 1, count );

            readRecord = eventToHardwareDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( eventToHardware, readRecord );

            count = eventToHardwareDao.delete( map );
            assertEquals( 1, count );

            readRecord = eventToHardwareDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static EventToHardware createEventToHardware() {
        EventToHardware eventToHardware = new EventToHardware();

        eventToHardware.setEventId( (long) 0 );
        eventToHardware.setHardwareId( (long) 0 );

        return eventToHardware;
    }

    public static void compareRecords( EventToHardware eventToHardware, EventToHardware readRecord ) {

        assertEquals( eventToHardware.getEventId(), readRecord.getEventId() );
        assertEquals( eventToHardware.getHardwareId(), readRecord.getHardwareId() );

    }

    public static void modifyRecord( EventToHardware eventToHardware ) {

        eventToHardware.setEventId( (long) 0 );
        eventToHardware.setHardwareId( (long) 0 );

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