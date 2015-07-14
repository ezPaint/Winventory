package com.simoncomputing.app.winventory.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.RefPermission;
import com.simoncomputing.app.winventory.util.BoException;

public class RefPermissionBo {

    private static RefPermissionBo instance = new RefPermissionBo();

    public static RefPermissionBo getInstance() {
        return instance;
    } 

    private RefPermissionBo() {
    } 

    public int create( RefPermission value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            RefPermissionDao mapper = session.getMapper( RefPermissionDao.class );
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

    public int update( RefPermission value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            RefPermissionDao mapper = session.getMapper( RefPermissionDao.class );
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
            RefPermissionDao mapper = session.getMapper( RefPermissionDao.class );
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

    public RefPermission read( Long key ) throws BoException {
        SqlSession session = null;
        RefPermission result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            RefPermissionDao mapper = session.getMapper( RefPermissionDao.class );
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

    public List<RefPermission> getAll() throws BoException {
        SqlSession session = null;
        List<RefPermission> result;

        try {
            session = SessionFactory.getSession();
            RefPermissionDao mapper = session.getMapper( RefPermissionDao.class );
            result = mapper.getAll();
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
    
    public RefPermission getRefPermissionByName(String name) throws BoException{
        SqlSession session = null;
        RefPermission result;

        try {
            session = SessionFactory.getSession();
            RefPermissionDao mapper = session.getMapper( RefPermissionDao.class );
            result = mapper.getRefPermissionByName(name);
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
}