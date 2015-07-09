package com.simoncomputing.app.winventory.bo;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.PermissionToUser;
import com.simoncomputing.app.winventory.util.BoException;

public class PermissionToUserBo {

    private static PermissionToUserBo instance = new PermissionToUserBo();

    public static PermissionToUserBo getInstance() {
        return instance;
    } 

    private PermissionToUserBo() {
    } 

    public int create( PermissionToUser value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            PermissionToUserDao mapper = session.getMapper( PermissionToUserDao.class );
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

    public int update( PermissionToUser value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            PermissionToUserDao mapper = session.getMapper( PermissionToUserDao.class );
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
            PermissionToUserDao mapper = session.getMapper( PermissionToUserDao.class );
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

    public PermissionToUser read( Long key ) throws BoException {
        SqlSession session = null;
        PermissionToUser result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            PermissionToUserDao mapper = session.getMapper( PermissionToUserDao.class );
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

    public List<PermissionToUser> getListByPermissionCode( String key ) throws BoException {
        SqlSession session = null;
        List<PermissionToUser> list;

        try {
            session = SessionFactory.getSession();
            PermissionToUserDao mapper = session.getMapper( PermissionToUserDao.class );
            list = mapper.getListByPermissionCode( key );
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

    public List<PermissionToUser> getListByUserId( Integer key ) throws BoException {
        SqlSession session = null;
        List<PermissionToUser> list;

        try {
            session = SessionFactory.getSession();
            PermissionToUserDao mapper = session.getMapper( PermissionToUserDao.class );
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