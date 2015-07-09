package com.simoncomputing.app.winventory.bo;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.HardwareToSoftware;
import com.simoncomputing.app.winventory.util.BoException;

public class HardwareToSoftwareBo {

    private static HardwareToSoftwareBo instance = new HardwareToSoftwareBo();

    public static HardwareToSoftwareBo getInstance() {
        return instance;
    } 

    private HardwareToSoftwareBo() {
    } 

    public int create( HardwareToSoftware value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
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

    public int update( HardwareToSoftware value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
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
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
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

    public HardwareToSoftware read( Long key ) throws BoException {
        SqlSession session = null;
        HardwareToSoftware result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
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

    public List<HardwareToSoftware> getListByHardwareId( Integer key ) throws BoException {
        SqlSession session = null;
        List<HardwareToSoftware> list;

        try {
            session = SessionFactory.getSession();
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
            list = mapper.getListByHardwareId( key );
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

    public List<HardwareToSoftware> getListBySoftwareId( Integer key ) throws BoException {
        SqlSession session = null;
        List<HardwareToSoftware> list;

        try {
            session = SessionFactory.getSession();
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
            list = mapper.getListBySoftwareId( key );
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