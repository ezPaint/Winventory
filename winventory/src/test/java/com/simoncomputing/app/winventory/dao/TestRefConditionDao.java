package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestRefConditionDao {

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
        RefConditionDao refConditionDao = session.getMapper( RefConditionDao.class );

        try {

            RefCondition refCondition = TestRefConditionDao.createRefCondition();
            String where = "CODE='" + refCondition.getCode() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = refConditionDao.create( refCondition );
            assertEquals( 1, count );
            assertNotNull( refCondition.getCode() );

            RefCondition readRecord = refConditionDao.read( map );
            assertNotNull( readRecord.getCode() );

            compareRecords( refCondition, readRecord );

            modifyRecord( refCondition );
            count = refConditionDao.update( refCondition );
            assertEquals( 1, count );

            readRecord = refConditionDao.read( map );
            assertNotNull( readRecord.getCode() );

            compareRecords( refCondition, readRecord );

            count = refConditionDao.delete( map );
            assertEquals( 1, count );

            readRecord = refConditionDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static RefCondition createRefCondition() {
        RefCondition refCondition = new RefCondition();

        refCondition.setCode( randomString( "code", 40 ) );
        refCondition.setDescription( randomString( "description", 2000 ) );

        return refCondition;
    }

    public static void compareRecords( RefCondition refCondition, RefCondition readRecord ) {

        assertEquals( refCondition.getDescription(), readRecord.getDescription() );

    }

    public static void modifyRecord( RefCondition refCondition ) {

        refCondition.setDescription( randomString( "description", 2000 ) );

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