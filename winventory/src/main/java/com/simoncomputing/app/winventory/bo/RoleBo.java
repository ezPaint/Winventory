package com.simoncomputing.app.winventory.bo;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.Role;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

public class RoleBo {

    private static RoleBo instance = new RoleBo();

    public static RoleBo getInstance() {
        return instance;
    } 

    private RoleBo() {
    } 

    public int create( Role value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            RoleDao mapper = session.getMapper( RoleDao.class );
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

    public int update( Role value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            RoleDao mapper = session.getMapper( RoleDao.class );
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
            RoleDao mapper = session.getMapper( RoleDao.class );
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

    public Role read( Long key ) throws BoException {
        SqlSession session = null;
        Role result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            RoleDao mapper = session.getMapper( RoleDao.class );
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

    // PROTECTED CODE -->
    /**
     * Returns a list of all the Roles in the db
     * 
     * @return
     * @throws BoException
     */
    public List<Role> getAll() throws BoException {
        SqlSession session = null;
        List<Role> list;

        try {
            session = SessionFactory.getSession();
            RoleDao mapper = session.getMapper(RoleDao.class);
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
     * Alternative function to create.
     * 
     * Acts as a work around for the problems caused by automatic sequence
     * 
     * @param newRole
     *            the Role to insert into the db
     * @return 1 if insert is successful
     * @throws BoException
     */
    public int insert(Role newRole) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            RoleDao mapper = session.getMapper(RoleDao.class);
            result = mapper.insert(newRole);
            session.commit();

        } catch (Exception e) {
            session.rollback();
            throw new BoException(e);

        } finally {
            if (session != null)
                session.close();
        }

        return result;
    }

}