package com.simoncomputing.app.winventory.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.util.DaoException;

public class TestUserDao {

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
        UserDao userDao = session.getMapper( UserDao.class );

        try {

            User user = TestUserDao.createUser();
            String where = "KEY='" + user.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "where", where );

            int count = userDao.create( user );
            assertEquals( 1, count );
            assertNotNull( user.getKey() );

            User readRecord = userDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( user, readRecord );

            List<User> list1= userDao.getListByIsActive( user.getIsActive() ) ; 
            assertEquals( 1, list1.size() );
            compareRecords( user, list1.get( 0 ) );

            List<User> list2= userDao.getListByRoleId( user.getRoleId() ) ; 
            assertEquals( 1, list2.size() );
            compareRecords( user, list2.get( 0 ) );

            modifyRecord( user );
            count = userDao.update( user );
            assertEquals( 1, count );

            readRecord = userDao.read( map );
            assertNotNull( readRecord.getKey() );

            compareRecords( user, readRecord );

            count = userDao.delete( map );
            assertEquals( 1, count );

            readRecord = userDao.read( map );
            assertNull( readRecord );

        } finally {
            if ( session != null ) {
                session.rollback();
                session.close();
            }
        }
    }

    public static User createUser() {
        User user = new User();

        user.setUsername( randomString( "username", 40 ) );
        user.setPassword( randomString( "password", 200 ) );
        user.setFirstName( randomString( "firstName", 40 ) );
        user.setLastName( randomString( "lastName", 40 ) );
        user.setEmail( randomString( "email", 40 ) );
        user.setCellPhone( randomString( "cellPhone", 40 ) );
        user.setWorkPhone( randomString( "workPhone", 40 ) );
        user.setIsActive( true  );
        user.setRoleId( randomNumber() );

        return user;
    }

    public static void compareRecords( User user, User readRecord ) {

        assertEquals( user.getUsername(), readRecord.getUsername() );
        assertEquals( user.getPassword(), readRecord.getPassword() );
        assertEquals( user.getFirstName(), readRecord.getFirstName() );
        assertEquals( user.getLastName(), readRecord.getLastName() );
        assertEquals( user.getEmail(), readRecord.getEmail() );
        assertEquals( user.getCellPhone(), readRecord.getCellPhone() );
        assertEquals( user.getWorkPhone(), readRecord.getWorkPhone() );
        assertEquals( user.getIsActive(), readRecord.getIsActive() );
        assertEquals( user.getRoleId(), readRecord.getRoleId() );

    }

    public static void modifyRecord( User user ) {

        user.setUsername( randomString( "username", 40 ) );
        user.setPassword( randomString( "password", 200 ) );
        user.setFirstName( randomString( "firstName", 40 ) );
        user.setLastName( randomString( "lastName", 40 ) );
        user.setEmail( randomString( "email", 40 ) );
        user.setCellPhone( randomString( "cellPhone", 40 ) );
        user.setWorkPhone( randomString( "workPhone", 40 ) );
        user.setIsActive( true  );
        user.setRoleId( randomNumber() );

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

    /**
     * Tests that getPermissionsByUserKey works correctly
     * 
     * @throws DaoException
     */
    @Test
    public void testGetPermissionsByUserKey() throws DaoException {
        SqlSession session = SessionFactory.getSession();
        UserDao userDao = session.getMapper(UserDao.class);
        try {

            // Create a user
            User user = TestUserDao.createUser();
            long key = 1;
            user.setKey(key);
            user.setUsername("test");
            user.setRoleId(1);

            String where = "KEY='" + user.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("where", where);

            int count = userDao.create(user);
            assertTrue(count == 1);

            User test = userDao.getUserByUsername("test");
            assertNotNull(test);

            // Make sure that the returned object is not null and a list
            List<String> perms = userDao.getPermissionsByUserKey(test.getKey());
            assertNotNull(perms);
            assertEquals(userDao.getPermissionsByUserKey(test.getKey()).getClass(), ArrayList.class);

            // Make sure that list contains only non-null Strings
            for (String s : perms) {
                assertNotNull(s);
                assertEquals(s.getClass(), String.class);
            }

            userDao.delete(map);

        } finally {
            if (session != null) {
                session.rollback();
                session.close();
            }
        }
    }

    /**
     * Tests the correctness of the returned list of permissions when querying
     * by User key
     * 
     * @throws DaoException
     */
    /*
     * @Test public void testCorrectPermissionsByKey() throws DaoException {
     * SqlSession session = SessionFactory.getSession(); UserDao userDao =
     * session.getMapper(UserDao.class); try {
     * 
     * // Create user User user = TestUserDao.createUser(); long key = 1;
     * user.setKey(key); user.setUsername("test");
     * 
     * String where = "KEY='" + user.getKey() + "' "; Map<String, Object> map =
     * new HashMap<String, Object>(); map.put("where", where);
     * 
     * // Generate random role id from 1-3 int role = (int) (Math.random() * 3 +
     * 1); System.out.println("Role: " + role); user.setRoleId(role);
     * 
     * // Insert user into the table int count = userDao.create(user);
     * assertTrue(count == 1);
     * 
     * User test = userDao.getUserByUsername("test"); assertNotNull(test);
     * 
     * List<String> perms = userDao.getPermissionsByUserKey(test.getKey());
     * assertNotNull(perms);
     * 
     * // Check that the returned list of permissions contains all of the //
     * permissions associated with the given role and that the number of //
     * permissions returned is correct if (user.getRoleId() == 1) { for (String
     * s : adminPerms) { assertTrue(perms.contains(s)); }
     * 
     * assertEquals(perms.size(), adminPerms.length);
     * 
     * } else if (user.getRoleId() == 2) { for (String s : managerPerms) {
     * assertTrue(perms.contains(s)); }
     * 
     * assertEquals(perms.size(), managerPerms.length);
     * 
     * } else if (user.getRoleId() == 3) { for (String s : basicPerms) {
     * assertTrue(perms.contains(s)); }
     * 
     * assertEquals(perms.size(), basicPerms.length);
     * 
     * }
     * 
     * userDao.delete(map);
     * 
     * } finally { if (session != null) { session.rollback(); session.close(); }
     * }
     * 
     * }
     */
    /**
     * Tests that getPermissionsByUser works correctly
     * 
     * @throws DaoException
     */
    @Test
    public void testGetPermissionsByUser() throws DaoException {
        SqlSession session = SessionFactory.getSession();
        UserDao userDao = session.getMapper(UserDao.class);
        try {

            // Create a user
            User user = TestUserDao.createUser();
            long key = 1;
            user.setKey(key);
            user.setUsername("test");
            user.setRoleId(1);

            String where = "KEY='" + user.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("where", where);

            int count = userDao.create(user);
            assertTrue(count == 1);

            User test = userDao.getUserByUsername("test");
            assertNotNull(test);

            // Make sure that the returned object is not null and a list
            List<String> perms = userDao.getPermissionsByUser(test);
            assertNotNull(perms);
            assertEquals(userDao.getPermissionsByUser(test).getClass(), ArrayList.class);

            // Make sure that list contains only non-null Strings
            for (String s : perms) {
                assertNotNull(s);
                assertEquals(s.getClass(), String.class);
            }

            userDao.delete(map);

        } finally {
            if (session != null) {
                session.rollback();
                session.close();
            }
        }
    }

    /**
     * Tests the correctness of the returned list of permissions when querying
     * by User
     * 
     * @throws DaoException
     */
    @Test
    public void testCorrectPermissionsByUser() throws DaoException {
        SqlSession session = SessionFactory.getSession();
        UserDao userDao = session.getMapper(UserDao.class);

        try {

            // Create user
            User user = TestUserDao.createUser();
            long key = 1;
            user.setKey(key);
            user.setUsername("test");
            String where = "KEY='" + user.getKey() + "' ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("where", where);

            // Generate random role id from 1-3
            int role = (int) (Math.random() * 3 + 1);
            System.out.println("Role: " + role);
            user.setRoleId(role);

            // Insert user into the table
            int count = userDao.create(user);
            user.setKey((long) (Math.random() * 10 + Math.random() * 10));
            assertTrue(count == 1);

            User test = userDao.getUserByUsername("test");
            assertNotNull(test);

            List<String> perms = userDao.getPermissionsByUser(test);
            assertNotNull(perms);

            // Check that the returned list of permissions contains all of the
            // permissions associated with the given role and that the number of
            // permissions returned is correct
            if (user.getRoleId() == 1) {
                String[] adminPerms = {"createHardware", "readHardware", "updateHardware",
                        "deleteHardware", "createSoftware", "readSoftware", "updateSoftware",
                        "deleteSoftware", "createBarcode", "readBarcode", "deleteBarcode",
                        "createUser", "readUser", "updateUser", "deleteUser", "readSelf",
                        "updateSelf", "deleteSelf"};
                for (String s : adminPerms) {
                    assertTrue(perms.contains(s));
                }

                assertEquals(perms.size(), adminPerms.length);

            } else if (user.getRoleId() == 2) {
                String[] managerPerms = {"createHardware", "readHardware", "updateHardware",
                        "deleteHardware", "createSoftware", "readSoftware", "updateSoftware",
                        "deleteSoftware", "createBarcode", "readBarcode", "deleteBarcode",
                        "readUser", "updateUser", "readSelf"};
                
                for (String s : managerPerms) {
                    assertTrue(perms.contains(s));
                }

                assertEquals(perms.size(), managerPerms.length);

            } else if (user.getRoleId() == 3) {
                String[] basicPerms = {"readHardware", "readSoftware",
                        "readUser", "updateUser", "readSelf"};
        
                for (String s : basicPerms) {
                    assertTrue(perms.contains(s));
                }

                assertEquals(perms.size(), basicPerms.length);

            }

            userDao.delete(map);

        } finally {
            if (session != null) {
                session.rollback();
                session.close();
            }
        }

    }
    /**
     * Tests that the getAll function returns a list of all of the users stored
     * in the database
     */
    @Test
    public void testGetAll() {
        SqlSession session = SessionFactory.getSession();
        UserDao userDao = session.getMapper(UserDao.class);

        try {

            Map<String, Object> map = new HashMap<String, Object>();

            // Generate 10 random users to add to the database
            for (int i = 1; i <= 10; i++) {
                User user = TestUserDao.createUser();
                user.setKey((long) i + 500);
                user.setRoleId(3);

                String where = "KEY='" + user.getKey() + "' ";
                map.put("where", where);

                int count = userDao.create(user);
                assertTrue(count == 1);
            }

            List<User> users = userDao.getAll();

            assertNotNull(users);

            // Size of the returned list should be 15
            // since there are 5 users pre-loaded into the
            // database and this test case generates 10
            // additional users
            assertTrue(users.size() == 15);

            userDao.delete(map);

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

/*   /**
     * Tests that the getAll function returns a list of all of the pre-loaded
     * users stored in the database
     *//*
    @Test
    public void testGetAllPreloaded() {
        SqlSession session = SessionFactory.getSession();
        UserDao userDao = session.getMapper(UserDao.class);

        List<User> users = userDao.getAll();

        assertNotNull(users);

        // Size of the returned list should be 5
        // since there are 5 users pre-loaded into the
        // database
        assertTrue(users.size() == 5);

    }*/

}