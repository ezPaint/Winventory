package com.simoncomputing.app.winventory.bo;

import java.sql.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestEventBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        EventBo eventBo = EventBo.getInstance();

        Event event = TestEventDao.createEvent();
        int count = eventBo.create( event );
        assertEquals( 1, count );

        Event readRecord = eventBo.read( event.getKey() );
        assertNotNull( readRecord.getKey() );

        TestEventDao.compareRecords( event, readRecord );

        TestEventDao.modifyRecord( event );
        count = eventBo.update( event );
        assertEquals( 1, count );

        count = eventBo.delete( event.getKey());
        assertEquals( 1, count );

        readRecord = eventBo.read( event.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->
    
    //Tests should not alter database so...
    @After
    public void resetDB()
    {
    	SessionFactory.getSession().rollback();
    	SessionFactory.getSession().close();
    }
    
    
    @Ignore("Not yet finished")
    public void testGetListByBarcode()
    {
    	EventBo eb = EventBo.getInstance();
    	for (int i = 0; i < 1000; i++)
    	{
    		Event event = genRandomEvent();
    		try {
				eb.create(event);
			} catch (BoException e) {
				e.printStackTrace();
				fail();
			}
    		
    		//TODO finish this
    	}
    }
    
    
    //----------------
    //Helper Methods
    
    
    public void populate()
    {
    	
    }
    
    public Event genRandomEvent()
    {
    	Event e = new Event();
    	e.setCategory(Math.random() < .5 ? "SYSTEM" : "USER");
    	e.setDateCreated(new Date((long) (System.currentTimeMillis() + Math.random() * 100000)));
    	e.setDescription(randomString("Desc", 20));
    	return e;
    }
    
    public static int randomNumber() {

        return (int) ( Math.random() * 100 ) + 0;

    }

    static StringBuffer sb = new StringBuffer();
    static Random random = new Random();
    private static String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
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
}