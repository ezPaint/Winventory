package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestTeamToUserDao {

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
        TeamToUserDao teamToUserDao = session.getMapper( TeamToUserDao.class );

        try {

            TeamToUser teamToUser = TestTeamToUserDao.createTeamToUser();
            String where = "KEY='" + teamToUser.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = teamToUserDao.create( teamToUser );
            assertEquals( 1, count );
            assertNotNull( teamToUser.getKey() );

            TeamToUser readRecord = teamToUserDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( teamToUser, readRecord );

            List<TeamToUser> list1= teamToUserDao.getListByUserId( teamToUser.getUserId() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( teamToUser, list1.get( 0 ) );

            List<TeamToUser> list2= teamToUserDao.getListByTeamId( teamToUser.getTeamId() ) ; 
            assertEquals( 1, list2.size() );
            compareRecords( teamToUser, list2.get( 0 ) );

            modifyRecord( teamToUser );
            count = teamToUserDao.update( teamToUser );
            assertEquals( 1, count );

            readRecord = teamToUserDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( teamToUser, readRecord );

            count = teamToUserDao.delete( map );
            assertEquals( 1, count );

            readRecord = teamToUserDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static TeamToUser createTeamToUser() {
        TeamToUser teamToUser = new TeamToUser();

        teamToUser.setUserId( randomNumber() );
        teamToUser.setTeamId( randomNumber() );

        return teamToUser;
    }

    public static void compareRecords( TeamToUser teamToUser, TeamToUser readRecord ) {

        assertEquals( teamToUser.getUserId(), readRecord.getUserId() );
        assertEquals( teamToUser.getTeamId(), readRecord.getTeamId() );

    }

    public static void modifyRecord( TeamToUser teamToUser ) {

        teamToUser.setUserId( randomNumber() );
        teamToUser.setTeamId( randomNumber() );

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