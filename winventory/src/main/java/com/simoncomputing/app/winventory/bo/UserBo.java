package com.simoncomputing.app.winventory.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

public class UserBo {

    private static UserBo instance = new UserBo();

    public static UserBo getInstance() {
        return instance;
    } 

    private UserBo() {
    } 

    public int create( User value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            UserDao mapper = session.getMapper( UserDao.class );
            result = mapper.create( value );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }

        return result;
    }

    public int update( User value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            UserDao mapper = session.getMapper( UserDao.class );
            result = mapper.update( value );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }

        return result;
    }

    public int delete( Long key ) throws BoException {
        SqlSession session = null;
        int result = 0;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            UserDao mapper = session.getMapper( UserDao.class );
            result = mapper.delete( map );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }

        return result;
    }

    public User read( Long key ) throws BoException {
        SqlSession session = null;
        User result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            UserDao mapper = session.getMapper( UserDao.class );
            result = mapper.read( map );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }

        return result;
    }

    public List<User> getListByRoleId( Integer key ) throws BoException {
        SqlSession session = null;
        List<User> list;

        try {
            session = SessionFactory.getSession();
            UserDao mapper = session.getMapper( UserDao.class );
            list = mapper.getListByRoleId( key );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }

        return list;
    }

    // PROTECTED CODE -->
	
    /**
     * Returns the user with the given username, if found.
     * Returns null if not found.
     */
	public User getUserByUsername(String username) {
	    
        SqlSession sqlSession = null;
        
        // try to get the user
        try {
            
        	sqlSession = SessionFactory.getSession();
            UserDao userMapper = sqlSession.getMapper(UserDao.class);
            
            // get the user by the given username param
            User user = userMapper.getUserByUsername(username);
            
            // returns the user, returns null if user not found
            return user;
            
        } finally {
            
            // close the sql session
            if (sqlSession != null) {
                sqlSession.close();
            }
            
        }
        
	}
	
	
	/**
     * Returns the user with the given email, if found.
     * Returns null if not found.
     */
	public User getUserByEmail(String email) {
	    
        SqlSession sqlSession = null;
        
        try {
        	sqlSession = SessionFactory.getSession();
            UserDao userMapper = sqlSession.getMapper(UserDao.class);
            
            // get the user with the given email, gets null otherwise
            User user = userMapper.getUserByEmail(email);
            return user;
            
        } finally {
            
            if (sqlSession != null) {
                sqlSession.close();
            }
            
        }
	}
	
	/**
	 * Returns a list of permissions associated with a particular user key
	 * @param key the key associated with the desired permissions
	 * @return the desired permissions
	 */
	public static List<String> getPermissionsByKey(Long key){
        SqlSession sqlSession = null;
        try {
            sqlSession = SessionFactory.getSession();
            UserDao userMapper = sqlSession.getMapper(UserDao.class);
            List<String> list = userMapper.getPermissionsByUserKey(key);
            return list;
        } finally {
            sqlSession.close();
        }
    }
	
	/**
	 * Returns a list of permissions associated with a particular user
	 * @param user the user associated with the desired permissions
	 * @return the desired permissions
	 */
	public static List<String> getPermissionsByUser(User user){
        SqlSession sqlSession = null;
        try {
            sqlSession = SessionFactory.getSession();
            UserDao userMapper = sqlSession.getMapper(UserDao.class);
            List<String> list = userMapper.getPermissionsByUser(user);
            return list;
        } finally {
            sqlSession.close();
        }
    }
	
	/**
     * Returns a list of all the users in the db
     * @return
     * @throws BoException
     */
    public List<User> getAll() throws BoException {
        SqlSession session = null;
        List<User> list;

        try {
            session = SessionFactory.getSession();
            UserDao mapper = session.getMapper(UserDao.class);
            list = mapper.getAll();
            session.commit();

        } catch (Exception e) {
            session.rollback();
            throw new BoException(e);

        } finally {
            if (session != null)
                session.close();
        }

        return list;
    }
    
    /**
     * Returns a list of all the usernames in the db, up to the limit specified
     * @return
     * @throws BoException
     */
    public List<String> getAllUsernames(Integer limit) throws BoException {
        SqlSession session = null;
        List<String> usernames;

        try {
            session = SessionFactory.getSession();
            UserDao mapper = session.getMapper(UserDao.class);
            usernames = mapper.getAllUsernames(limit);
            session.commit();

        } catch (Exception e) {
            session.rollback();
            throw new BoException(e);

        } finally {
            if (session != null)
                session.close();
        }

        return usernames;
    }

}