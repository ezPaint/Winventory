package com.simoncomputing.app.winventory.bo;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestUserBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        UserBo userBo = UserBo.getInstance();

        User user = TestUserDao.createUser();
        int count = userBo.create( user );
        assertEquals( 1, count );

        User readRecord = userBo.read( user.getKey() );
        assertNotNull( readRecord.getKey() );

        TestUserDao.compareRecords( user, readRecord );

        List<User> list1= userBo.getListByRoleId( user.getRoleId() ) ; 
        assertEquals( 1 , list1.size() );

        TestUserDao.modifyRecord( user );
        count = userBo.update( user );
        assertEquals( 1, count );

        count = userBo.delete( user.getKey());
        assertEquals( 1, count );

        readRecord = userBo.read( user.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

    /**
     * Stub
     */
    // @Test
    public void testGetUserByUsername() throws BoException {
        UserBo ub = UserBo.getInstance();
        User user = new User();
        user.setKey(5544L);
        user.setPassword("SHA-256$20000$UTF-8$uegf5hijubj9samgsbd4klhguc$014cfb0d05b9504a7d6e51a4ecc0480d4e0e9c9642feb6738142b3b1c960947a");
        user.setUsername("Painter");
        user.setEmail("nphillpott@outlook.com");
        user.setFirstName("Nick");
        user.setLastName("Phillpott");
        user.setCellPhone("7576524776");
        user.setWorkPhone("7573012507");
        user.setIsActive(true);
        user.setRoleId(1);

        ub.create(user);

        ub.delete(user.getKey());

    }

    /**
     * Stub
     */
    @Test
    public void testGetUserByEmail() {
        // TODO
        assertTrue(true);
    }

    /**
     * Tests the getPermissionsByUser method of the UserBo
     * 
     * @throws BoException
     */
    // @Test
    public void testGetPermissionsByUser() throws BoException {
        UserBo ub = UserBo.getInstance();
        User user = new User();
        user.setKey(1L);
        user.setPassword("password");
        user.setUsername("test");
        user.setEmail("test@test.com");
        user.setFirstName("test");
        user.setLastName("sample");
        user.setCellPhone("xxx");
        user.setWorkPhone("xxx");
        user.setIsActive(true);
        user.setRoleId(1);

        ub.create(user);

        List<String> permissions = UserBo.getPermissionsByUser(user);
        assertNotNull(permissions);

        ub.delete(user.getKey());

    }

    /**
     * Tests the getPermissionsByKey method of the UserBo
     * 
     * @throws BoException
     */
    @Test
    public void testGetPermissionsByKey() throws BoException {
        UserBo ub = UserBo.getInstance();
        User user = new User();
        user.setKey(1L);
        user.setPassword("password");
        user.setUsername("test");
        user.setEmail("test@test.com");
        user.setFirstName("test");
        user.setLastName("sample");
        user.setCellPhone("xxx");
        user.setWorkPhone("xxx");
        user.setIsActive(true);
        user.setRoleId(1);

        ub.create(user);

        List<String> permissions = UserBo.getPermissionsByKey(user.getKey());
        assertNotNull(permissions);

        ub.delete(user.getKey());
    }

    /**
     * Tests the getAll method of the UserBo
     * 
     * Makes sure that the correct number of users is returned, based on the
     * pre-loaded users
     * 
     * @throws BoException
     */
    @Test
    public void testGetAll() throws BoException {
        UserBo bo = UserBo.getInstance();

        List<User> all = bo.getAll();

        assertNotNull(all);
        assertTrue(all.size() == 5);

    }

}