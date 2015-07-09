package com.simoncomputing.app.winventory.bo;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.EventToLocation;
import com.simoncomputing.app.winventory.util.BoException;

public class EventToLocationBo {

    private static EventToLocationBo instance = new EventToLocationBo();

    public static EventToLocationBo getInstance() {
        return instance;
    } 

    private EventToLocationBo() {
    } 

    public int create( EventToLocation value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            EventToLocationDao mapper = session.getMapper( EventToLocationDao.class );
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

    public int update( EventToLocation value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            EventToLocationDao mapper = session.getMapper( EventToLocationDao.class );
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
            EventToLocationDao mapper = session.getMapper( EventToLocationDao.class );
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

    public EventToLocation read( Long key ) throws BoException {
        SqlSession session = null;
        EventToLocation result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            EventToLocationDao mapper = session.getMapper( EventToLocationDao.class );
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

    public List<EventToLocation> getListByEventId( Long key ) throws BoException {
        SqlSession session = null;
        List<EventToLocation> list;

        try {
            session = SessionFactory.getSession();
            EventToLocationDao mapper = session.getMapper( EventToLocationDao.class );
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

    public List<EventToLocation> getListByLocationId( Long key ) throws BoException {
        SqlSession session = null;
        List<EventToLocation> list;

        try {
            session = SessionFactory.getSession();
            EventToLocationDao mapper = session.getMapper( EventToLocationDao.class );
            list = mapper.getListByLocationId( key );
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