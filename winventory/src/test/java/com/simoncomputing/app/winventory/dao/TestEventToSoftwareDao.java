/*package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestEventToSoftwareDao {

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
        EventToSoftwareDao eventToSoftwareDao = session.getMapper( EventToSoftwareDao.class );

        try {

            EventToSoftware eventToSoftware = TestEventToSoftwareDao.createEventToSoftware();
            String where = "KEY='" + eventToSoftware.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = eventToSoftwareDao.create( eventToSoftware );
            assertEquals( 1, count );
            assertNotNull( eventToSoftware.getKey() );

            EventToSoftware readRecord = eventToSoftwareDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( eventToSoftware, readRecord );

            List<EventToSoftware> list1= eventToSoftwareDao.getListByEventId( eventToSoftware.getEventId() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( eventToSoftware, list1.get( 0 ) );

            List<EventToSoftware> list2= eventToSoftwareDao.getListBySoftwareId( eventToSoftware.getSoftwareId() ) ; 
            assertEquals( 1, list2.size() );
            compareRecords( eventToSoftware, list2.get( 0 ) );

            modifyRecord( eventToSoftware );
            count = eventToSoftwareDao.update( eventToSoftware );
            assertEquals( 1, count );

            readRecord = eventToSoftwareDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( eventToSoftware, readRecord );

            count = eventToSoftwareDao.delete( map );
            assertEquals( 1, count );

            readRecord = eventToSoftwareDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static EventToSoftware createEventToSoftware() {
        EventToSoftware eventToSoftware = new EventToSoftware();

        eventToSoftware.setEventId( (long) 0 );
        eventToSoftware.setSoftwareId( (long) 0 );

        return eventToSoftware;
    }

    public static void compareRecords( EventToSoftware eventToSoftware, EventToSoftware readRecord ) {

        assertEquals( eventToSoftware.getEventId(), readRecord.getEventId() );
        assertEquals( eventToSoftware.getSoftwareId(), readRecord.getSoftwareId() );

    }

    public static void modifyRecord( EventToSoftware eventToSoftware ) {

        eventToSoftware.setEventId( (long) 0 );
        eventToSoftware.setSoftwareId( (long) 0 );

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

}*/