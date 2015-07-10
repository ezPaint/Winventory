/*package com.simoncomputing.app.winventory.bo;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.EventToHardware;
import com.simoncomputing.app.winventory.util.BoException;

public class EventToHardwareBo {

    private static EventToHardwareBo instance = new EventToHardwareBo();

    public static EventToHardwareBo getInstance() {
        return instance;
    } 

    private EventToHardwareBo() {
    } 

    public int create( EventToHardware value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            EventToHardwareDao mapper = session.getMapper( EventToHardwareDao.class );
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

    public int update( EventToHardware value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            EventToHardwareDao mapper = session.getMapper( EventToHardwareDao.class );
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
            EventToHardwareDao mapper = session.getMapper( EventToHardwareDao.class );
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

    public EventToHardware read( Long key ) throws BoException {
        SqlSession session = null;
        EventToHardware result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            EventToHardwareDao mapper = session.getMapper( EventToHardwareDao.class );
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

    public List<EventToHardware> getListByEventId( Long key ) throws BoException {
        SqlSession session = null;
        List<EventToHardware> list;

        try {
            session = SessionFactory.getSession();
            EventToHardwareDao mapper = session.getMapper( EventToHardwareDao.class );
            list = mapper.getListByEventId( key );
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

    public List<EventToHardware> getListByHardwareId( Long key ) throws BoException {
        SqlSession session = null;
        List<EventToHardware> list;

        try {
            session = SessionFactory.getSession();
            EventToHardwareDao mapper = session.getMapper( EventToHardwareDao.class );
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

    // PROTECTED CODE -->

}*/