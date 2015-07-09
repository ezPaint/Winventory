package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestRefHardwareTypeDao {

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
        RefHardwareTypeDao refHardwareTypeDao = session.getMapper( RefHardwareTypeDao.class );

        try {

            RefHardwareType refHardwareType = TestRefHardwareTypeDao.createRefHardwareType();
            String where = "CODE='" + refHardwareType.getCode() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = refHardwareTypeDao.create( refHardwareType );
            assertEquals( 1, count );
            assertNotNull( refHardwareType.getCode() );

            RefHardwareType readRecord = refHardwareTypeDao.read( map );
            assertNotNull( readRecord.getCode() );

            compareRecords( refHardwareType, readRecord );

            modifyRecord( refHardwareType );
            count = refHardwareTypeDao.update( refHardwareType );
            assertEquals( 1, count );

            readRecord = refHardwareTypeDao.read( map );
            assertNotNull( readRecord.getCode() );

            compareRecords( refHardwareType, readRecord );

            count = refHardwareTypeDao.delete( map );
            assertEquals( 1, count );

            readRecord = refHardwareTypeDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static RefHardwareType createRefHardwareType() {
        RefHardwareType refHardwareType = new RefHardwareType();

        refHardwareType.setCode( randomString( "code", 40 ) );
        refHardwareType.setDescription( randomString( "description", 2000 ) );

        return refHardwareType;
    }

    public static void compareRecords( RefHardwareType refHardwareType, RefHardwareType readRecord ) {

        assertEquals( refHardwareType.getDescription(), readRecord.getDescription() );

    }

    public static void modifyRecord( RefHardwareType refHardwareType ) {

        refHardwareType.setDescription( randomString( "description", 2000 ) );

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