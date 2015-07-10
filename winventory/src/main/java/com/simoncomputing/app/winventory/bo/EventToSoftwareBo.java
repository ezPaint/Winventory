package com.simoncomputing.app.winventory.bo;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.EventToSoftware;
import com.simoncomputing.app.winventory.util.BoException;

public class EventToSoftwareBo {

    private static EventToSoftwareBo instance = new EventToSoftwareBo();

    public static EventToSoftwareBo getInstance() {
        return instance;
    } 

    private EventToSoftwareBo() {
    } 

    public int create( EventToSoftware value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            EventToSoftwareDao mapper = session.getMapper( EventToSoftwareDao.class );
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

    public int update( EventToSoftware value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            EventToSoftwareDao mapper = session.getMapper( EventToSoftwareDao.class );
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
            EventToSoftwareDao mapper = session.getMapper( EventToSoftwareDao.class );
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

    public EventToSoftware read( Long key ) throws BoException {
        SqlSession session = null;
        EventToSoftware result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            EventToSoftwareDao mapper = session.getMapper( EventToSoftwareDao.class );
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

//    public List<EventToSoftware> getListByEventId( Long key ) throws BoException {
//        SqlSession session = null;
//        List<EventToSoftware> list;
//
//        try {
//            session = SessionFactory.getSession();
//            EventToSoftwareDao mapper = session.getMapper( EventToSoftwareDao.class );
//            list = mapper.getListByEventId( key );
//            session.commit();
//
//        } catch ( Exception e ) {
//            session.rollback();
//            throw new BoException( e );
//
//        } finally { 
//            if ( session != null )
//                session.close();
//        }
//
//        return list;
//    }
//
//    public List<EventToSoftware> getListBySoftwareId( Long key ) throws BoException {
//        SqlSession session = null;
//        List<EventToSoftware> list;
//
//        try {
//            session = SessionFactory.getSession();
//            EventToSoftwareDao mapper = session.getMapper( EventToSoftwareDao.class );
//            list = mapper.getListBySoftwareId( key );
//            session.commit();
//
//        } catch ( Exception e ) {
//            session.rollback();
//            throw new BoException( e );
//
//        } finally { 
//            if ( session != null )
//                session.close();
//        }
//
//        return list;
//    }

    // PROTECTED CODE -->

}