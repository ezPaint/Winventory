package com.simoncomputing.app.winventory.bo;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.EventToUser;
import com.simoncomputing.app.winventory.util.BoException;

public class EventToUserBo {

    private static EventToUserBo instance = new EventToUserBo();

    public static EventToUserBo getInstance() {
        return instance;
    } 

    private EventToUserBo() {
    } 

    public int create( EventToUser value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            EventToUserDao mapper = session.getMapper( EventToUserDao.class );
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

    public int update( EventToUser value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            EventToUserDao mapper = session.getMapper( EventToUserDao.class );
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
            EventToUserDao mapper = session.getMapper( EventToUserDao.class );
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

    public EventToUser read( Long key ) throws BoException {
        SqlSession session = null;
        EventToUser result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            EventToUserDao mapper = session.getMapper( EventToUserDao.class );
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

//    public List<EventToUser> getListByEventId( Long key ) throws BoException {
//        SqlSession session = null;
//        List<EventToUser> list;
//
//        try {
//            session = SessionFactory.getSession();
//            EventToUserDao mapper = session.getMapper( EventToUserDao.class );
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

//    public List<EventToUser> getListByUserId( Long key ) throws BoException {
//        SqlSession session = null;
//        List<EventToUser> list;
//
//        try {
//            session = SessionFactory.getSession();
//            EventToUserDao mapper = session.getMapper( EventToUserDao.class );
//            list = mapper.getListByUserId( key );
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