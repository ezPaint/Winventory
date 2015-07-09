package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestEventToUserDao {

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
        EventToUserDao eventToUserDao = session.getMapper( EventToUserDao.class );

        try {

            EventToUser eventToUser = TestEventToUserDao.createEventToUser();
            String where = "KEY='" + eventToUser.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = eventToUserDao.create( eventToUser );
            assertEquals( 1, count );
            assertNotNull( eventToUser.getKey() );

            EventToUser readRecord = eventToUserDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( eventToUser, readRecord );

            List<EventToUser> list1= eventToUserDao.getListByEventId( eventToUser.getEventId() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( eventToUser, list1.get( 0 ) );

            List<EventToUser> list2= eventToUserDao.getListByUserId( eventToUser.getUserId() ) ; 
            assertEquals( 1, list2.size() );
            compareRecords( eventToUser, list2.get( 0 ) );

            modifyRecord( eventToUser );
            count = eventToUserDao.update( eventToUser );
            assertEquals( 1, count );

            readRecord = eventToUserDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( eventToUser, readRecord );

            count = eventToUserDao.delete( map );
            assertEquals( 1, count );

            readRecord = eventToUserDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static EventToUser createEventToUser() {
        EventToUser eventToUser = new EventToUser();

        eventToUser.setEventId( (long) 0 );
        eventToUser.setUserId( (long) 0 );

        return eventToUser;
    }

    public static void compareRecords( EventToUser eventToUser, EventToUser readRecord ) {

        assertEquals( eventToUser.getEventId(), readRecord.getEventId() );
        assertEquals( eventToUser.getUserId(), readRecord.getUserId() );

    }

    public static void modifyRecord( EventToUser eventToUser ) {

        eventToUser.setEventId( (long) 0 );
        eventToUser.setUserId( (long) 0 );

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