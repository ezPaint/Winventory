package com.simoncomputing.app.winventory.bo;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.RoleToUser;
import com.simoncomputing.app.winventory.util.BoException;

public class RoleToUserBo {

    private static RoleToUserBo instance = new RoleToUserBo();

    public static RoleToUserBo getInstance() {
        return instance;
    } 

    private RoleToUserBo() {
    } 

    public int create( RoleToUser value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            RoleToUserDao mapper = session.getMapper( RoleToUserDao.class );
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

    public int update( RoleToUser value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            RoleToUserDao mapper = session.getMapper( RoleToUserDao.class );
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
            RoleToUserDao mapper = session.getMapper( RoleToUserDao.class );
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

    public RoleToUser read( Long key ) throws BoException {
        SqlSession session = null;
        RoleToUser result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            RoleToUserDao mapper = session.getMapper( RoleToUserDao.class );
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

    public List<RoleToUser> getListByRoleId( Integer key ) throws BoException {
        SqlSession session = null;
        List<RoleToUser> list;

        try {
            session = SessionFactory.getSession();
            RoleToUserDao mapper = session.getMapper( RoleToUserDao.class );
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

    public List<RoleToUser> getListByUserId( Integer key ) throws BoException {
        SqlSession session = null;
        List<RoleToUser> list;

        try {
            session = SessionFactory.getSession();
            RoleToUserDao mapper = session.getMapper( RoleToUserDao.class );
            list = mapper.getListByUserId( key );
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

}