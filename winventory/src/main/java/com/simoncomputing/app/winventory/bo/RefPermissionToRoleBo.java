package com.simoncomputing.app.winventory.bo;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.RefPermissionToRole;
import com.simoncomputing.app.winventory.domain.Role;
import com.simoncomputing.app.winventory.util.BoException;

public class RefPermissionToRoleBo {

    private static RefPermissionToRoleBo instance = new RefPermissionToRoleBo();

    public static RefPermissionToRoleBo getInstance() {
        return instance;
    } 

    private RefPermissionToRoleBo() {
    } 

    public int create( RefPermissionToRole value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            RefPermissionToRoleDao mapper = session.getMapper( RefPermissionToRoleDao.class );
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

    public int update( RefPermissionToRole value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            RefPermissionToRoleDao mapper = session.getMapper( RefPermissionToRoleDao.class );
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
            RefPermissionToRoleDao mapper = session.getMapper( RefPermissionToRoleDao.class );
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

    public RefPermissionToRole read( Long key ) throws BoException {
        SqlSession session = null;
        RefPermissionToRole result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            RefPermissionToRoleDao mapper = session.getMapper( RefPermissionToRoleDao.class );
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

    public List<RefPermissionToRole> getListByPermissionId( Integer key ) throws BoException {
        SqlSession session = null;
        List<RefPermissionToRole> list;

        try {
            session = SessionFactory.getSession();
            RefPermissionToRoleDao mapper = session.getMapper( RefPermissionToRoleDao.class );
            list = mapper.getListByPermissionId( key );
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

    public List<RefPermissionToRole> getListByRoleId( Integer key ) throws BoException {
        SqlSession session = null;
        List<RefPermissionToRole> list;

        try {
            session = SessionFactory.getSession();
            RefPermissionToRoleDao mapper = session.getMapper( RefPermissionToRoleDao.class );
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
    
    public List<RefPermissionToRole> getAll() throws BoException {
        SqlSession session = null;
        List<RefPermissionToRole> list;

        try {
            session = SessionFactory.getSession();
            RefPermissionToRoleDao mapper = session.getMapper( RefPermissionToRoleDao.class );
            list = mapper.getAll();
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
    
    public void insert(Long key, int permKey, int roleKey) throws BoException {
        SqlSession session = null;

        try {
            session = SessionFactory.getSession();
            RefPermissionToRoleDao mapper = session.getMapper( RefPermissionToRoleDao.class );
            
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("key", key);
            map.put("permissionId", permKey);
            map.put("roleId", roleKey);
            
            mapper.insert( map );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }
    }

}