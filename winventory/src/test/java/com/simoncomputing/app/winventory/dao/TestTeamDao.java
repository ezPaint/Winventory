package com.simoncomputing.app.winventory.dao;

import java.util.Random;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;

public class TestTeamDao {

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
        TeamDao teamDao = session.getMapper( TeamDao.class );

        try {

            Team team = TestTeamDao.createTeam();
            String where = "KEY='" + team.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = teamDao.create( team );
            assertEquals( 1, count );
            assertNotNull( team.getKey() );

            Team readRecord = teamDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( team, readRecord );

            List<Team> list1= teamDao.getListByLeaderId( team.getLeaderId() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( team, list1.get( 0 ) );

            modifyRecord( team );
            count = teamDao.update( team );
            assertEquals( 1, count );

            readRecord = teamDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( team, readRecord );

            count = teamDao.delete( map );
            assertEquals( 1, count );

            readRecord = teamDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static Team createTeam() {
        Team team = new Team();

        team.setName( randomString( "name", 30 ) );
        team.setIsActive( true  );
        team.setLeaderId( randomNumber() );

        return team;
    }

    public static void compareRecords( Team team, Team readRecord ) {

        assertEquals( team.getName(), readRecord.getName() );
        assertEquals( team.getIsActive(), readRecord.getIsActive() );
        assertEquals( team.getLeaderId(), readRecord.getLeaderId() );

    }

    public static void modifyRecord( Team team ) {

        team.setName( randomString( "name", 30 ) );
        team.setIsActive( true  );
        team.setLeaderId( randomNumber() );

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