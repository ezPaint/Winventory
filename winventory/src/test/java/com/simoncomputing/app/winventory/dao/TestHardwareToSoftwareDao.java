package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestHardwareToSoftwareDao {

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
        HardwareToSoftwareDao hardwareToSoftwareDao = session.getMapper( HardwareToSoftwareDao.class );

        try {

            HardwareToSoftware hardwareToSoftware = TestHardwareToSoftwareDao.createHardwareToSoftware();
            String where = "KEY='" + hardwareToSoftware.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = hardwareToSoftwareDao.create( hardwareToSoftware );
            assertEquals( 1, count );
            assertNotNull( hardwareToSoftware.getKey() );

            HardwareToSoftware readRecord = hardwareToSoftwareDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( hardwareToSoftware, readRecord );

            List<HardwareToSoftware> list1= hardwareToSoftwareDao.getListByHardwareId( hardwareToSoftware.getHardwareId() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( hardwareToSoftware, list1.get( 0 ) );

            List<HardwareToSoftware> list2= hardwareToSoftwareDao.getListBySoftwareId( hardwareToSoftware.getSoftwareId() ) ; 
            assertEquals( 1, list2.size() );
            compareRecords( hardwareToSoftware, list2.get( 0 ) );

            modifyRecord( hardwareToSoftware );
            count = hardwareToSoftwareDao.update( hardwareToSoftware );
            assertEquals( 1, count );

            readRecord = hardwareToSoftwareDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( hardwareToSoftware, readRecord );

            count = hardwareToSoftwareDao.delete( map );
            assertEquals( 1, count );

            readRecord = hardwareToSoftwareDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static HardwareToSoftware createHardwareToSoftware() {
        HardwareToSoftware hardwareToSoftware = new HardwareToSoftware();

        hardwareToSoftware.setHardwareId( randomNumber() );
        hardwareToSoftware.setSoftwareId( randomNumber() );

        return hardwareToSoftware;
    }

    public static void compareRecords( HardwareToSoftware hardwareToSoftware, HardwareToSoftware readRecord ) {

        assertEquals( hardwareToSoftware.getHardwareId(), readRecord.getHardwareId() );
        assertEquals( hardwareToSoftware.getSoftwareId(), readRecord.getSoftwareId() );

    }

    public static void modifyRecord( HardwareToSoftware hardwareToSoftware ) {

        hardwareToSoftware.setHardwareId( randomNumber() );
        hardwareToSoftware.setSoftwareId( randomNumber() );

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