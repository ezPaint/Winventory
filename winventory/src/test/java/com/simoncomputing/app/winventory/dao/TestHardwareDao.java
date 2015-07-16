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

public class TestHardwareDao {

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
        HardwareDao hardwareDao = session.getMapper( HardwareDao.class );

        try {

            Hardware hardware = TestHardwareDao.createHardware();
            String where = "KEY='" + hardware.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = hardwareDao.create( hardware );
            assertEquals( 1, count );
            assertNotNull( hardware.getKey() );

            Hardware readRecord = hardwareDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( hardware, readRecord );

            List<Hardware> list1= hardwareDao.getListByLocationId( hardware.getLocationId() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( hardware, list1.get( 0 ) );

            List<Hardware> list2= hardwareDao.getListByUserId( hardware.getUserId() ) ; 
            assertEquals( 1, list2.size() );
            compareRecords( hardware, list2.get( 0 ) );

            List<Hardware> list3= hardwareDao.getListByIsActive( hardware.getIsActive() ) ; 
            assertEquals( 1, list3.size() );
            compareRecords( hardware, list3.get( 0 ) );

            modifyRecord( hardware );
            count = hardwareDao.update( hardware );
            assertEquals( 1, count );

            readRecord = hardwareDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( hardware, readRecord );

            count = hardwareDao.delete( map );
            assertEquals( 1, count );

            readRecord = hardwareDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static Hardware createHardware() {
        Hardware hardware = new Hardware();

        hardware.setType( randomString( "type", 40 ) );
        hardware.setDescription( randomString( "description", 2000 ) );
        hardware.setCost( (double) randomNumber() );
        hardware.setSerialNo( randomString( "serialNo", 40 ) );
        hardware.setCondition( randomString( "condition", 40 ) );
        hardware.setLocationId( (long) 0 );
        hardware.setUserId( (long) 0 );
        hardware.setPurchaseDate( new Date() );
        hardware.setIsActive( true  );

        return hardware;
    }

    public static void compareRecords( Hardware hardware, Hardware readRecord ) {

        assertEquals( hardware.getType(), readRecord.getType() );
        assertEquals( hardware.getDescription(), readRecord.getDescription() );
        assertEquals( hardware.getCost(), readRecord.getCost() );
        assertEquals( hardware.getSerialNo(), readRecord.getSerialNo() );
        assertEquals( hardware.getCondition(), readRecord.getCondition() );
        assertEquals( hardware.getLocationId(), readRecord.getLocationId() );
        assertEquals( hardware.getUserId(), readRecord.getUserId() );
        assertNotSame( hardware.getPurchaseDate(), readRecord.getPurchaseDate() );
        assertEquals( hardware.getIsActive(), readRecord.getIsActive() );

    }

    public static void modifyRecord( Hardware hardware ) {

        hardware.setType( randomString( "type", 40 ) );
        hardware.setDescription( randomString( "description", 2000 ) );
        hardware.setCost( (double) randomNumber() );
        hardware.setSerialNo( randomString( "serialNo", 40 ) );
        hardware.setCondition( randomString( "condition", 40 ) );
        hardware.setLocationId( (long) 0 );
        hardware.setUserId( (long) 0 );
        hardware.setPurchaseDate( new Date() );
        hardware.setIsActive( true  );

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