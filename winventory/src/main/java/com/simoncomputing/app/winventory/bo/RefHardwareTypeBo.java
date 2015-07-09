package com.simoncomputing.app.winventory.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.RefHardwareType;
import com.simoncomputing.app.winventory.util.BoException;

public class RefHardwareTypeBo {

    private static RefHardwareTypeBo instance = new RefHardwareTypeBo();

    public static RefHardwareTypeBo getInstance() {
        return instance;
    } 

    private RefHardwareTypeBo() {
    } 

    public int create( RefHardwareType value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            RefHardwareTypeDao mapper = session.getMapper( RefHardwareTypeDao.class );
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

    public int update( RefHardwareType value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            RefHardwareTypeDao mapper = session.getMapper( RefHardwareTypeDao.class );
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

    public int delete( String code ) throws BoException {
        SqlSession session = null;
        int result = 0;
        String where = "CODE='" + code + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            RefHardwareTypeDao mapper = session.getMapper( RefHardwareTypeDao.class );
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

    public RefHardwareType read( String code ) throws BoException {
        SqlSession session = null;
        RefHardwareType result;
        String where = "CODE='" + code + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            RefHardwareTypeDao mapper = session.getMapper( RefHardwareTypeDao.class );
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