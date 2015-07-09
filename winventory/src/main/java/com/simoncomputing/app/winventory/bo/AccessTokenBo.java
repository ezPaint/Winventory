package com.simoncomputing.app.winventory.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.AccessToken;
import com.simoncomputing.app.winventory.util.BoException;

public class AccessTokenBo {

    private static AccessTokenBo instance = new AccessTokenBo();

    public static AccessTokenBo getInstance() {
        return instance;
    } 

    private AccessTokenBo() {
    } 

    public int create( AccessToken value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            AccessTokenDao mapper = session.getMapper( AccessTokenDao.class );
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

    public int update( AccessToken value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            AccessTokenDao mapper = session.getMapper( AccessTokenDao.class );
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

    public int delete( Integer userKey ) throws BoException {
        SqlSession session = null;
        int result = 0;
        String where = "USER_KEY='" + userKey + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            AccessTokenDao mapper = session.getMapper( AccessTokenDao.class );
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

    public AccessToken read( Integer userKey ) throws BoException {
        SqlSession session = null;
        AccessToken result;
        String where = "USER_KEY='" + userKey + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            AccessTokenDao mapper = session.getMapper( AccessTokenDao.class );
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

}