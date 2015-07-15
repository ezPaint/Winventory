package com.simoncomputing.app.winventory.dao;

import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestEventDao {

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
        EventDao eventDao = session.getMapper( EventDao.class );

        try {

            Event event = TestEventDao.createEvent();
            String where = "KEY='" + event.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = eventDao.create( event );
            assertEquals( 1, count );
            assertNotNull( event.getKey() );

            Event readRecord = eventDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( event, readRecord );

            List<Event> list1= eventDao.getListByCreatorId( event.getCreatorId() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( event, list1.get( 0 ) );

            List<Event> list2= eventDao.getListByUserId( event.getUserId() ) ; 
            assertEquals( 1, list2.size() );
            compareRecords( event, list2.get( 0 ) );

            List<Event> list3= eventDao.getListByHardwareId( event.getHardwareId() ) ; 
            assertEquals( 1, list3.size() );
            compareRecords( event, list3.get( 0 ) );

            List<Event> list4= eventDao.getListBySoftwareId( event.getSoftwareId() ) ; 
            assertEquals( 1, list4.size() );
            compareRecords( event, list4.get( 0 ) );

            List<Event> list5= eventDao.getListByLocationId( event.getLocationId() ) ; 
            assertEquals( 1, list5.size() );
            compareRecords( event, list5.get( 0 ) );

            modifyRecord( event );
            count = eventDao.update( event );
            assertEquals( 1, count );

            readRecord = eventDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( event, readRecord );

            count = eventDao.delete( map );
            assertEquals( 1, count );

            readRecord = eventDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static Event createEvent() {
        Event event = new Event();

        event.setDateCreated( new Date() );
        event.setDescription( randomString( "description", 2000 ) );
        event.setCategory( randomString( "category", 50 ) );
        event.setCreatorId( (long) 0 );
        event.setUserId( (long) 0 );
        event.setHardwareId( (long) 0 );
        event.setSoftwareId( (long) 0 );
        event.setLocationId( (long) 0 );

        return event;
    }

    public static void compareRecords( Event event, Event readRecord ) {

        assertNotSame( event.getDateCreated(), readRecord.getDateCreated() );
        assertEquals( event.getDescription(), readRecord.getDescription() );
        assertEquals( event.getCategory(), readRecord.getCategory() );
        assertEquals( event.getCreatorId(), readRecord.getCreatorId() );
        assertEquals( event.getUserId(), readRecord.getUserId() );
        assertEquals( event.getHardwareId(), readRecord.getHardwareId() );
        assertEquals( event.getSoftwareId(), readRecord.getSoftwareId() );
        assertEquals( event.getLocationId(), readRecord.getLocationId() );

    }

    public static void modifyRecord( Event event ) {

        event.setDateCreated( new Date() );
        event.setDescription( randomString( "description", 2000 ) );
        event.setCategory( randomString( "category", 50 ) );
        event.setCreatorId( (long) 0 );
        event.setUserId( (long) 0 );
        event.setHardwareId( (long) 0 );
        event.setSoftwareId( (long) 0 );
        event.setLocationId( (long) 0 );

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