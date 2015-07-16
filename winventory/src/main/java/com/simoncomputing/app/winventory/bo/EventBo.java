package com.simoncomputing.app.winventory.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;
import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.controller.hardware.HardwareEditController;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.BoException;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.EventType;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Item;
import com.simoncomputing.app.winventory.domain.ItemType;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.formbean.UserInfoBean;

public class EventBo {

    private static EventBo instance = new EventBo();

    public static EventBo getInstance() {
        return instance;
    } 

    private EventBo() {
    } 

    public int create( Event value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            EventDao mapper = session.getMapper( EventDao.class );
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

    public int update( Event value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            EventDao mapper = session.getMapper( EventDao.class );
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
            EventDao mapper = session.getMapper( EventDao.class );
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

    public Event read( Long key ) throws BoException {
        SqlSession session = null;
        Event result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            EventDao mapper = session.getMapper( EventDao.class );
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

    public List<Event> getListByCreatorId( Long key ) throws BoException {
        SqlSession session = null;
        List<Event> list;

        try {
            session = SessionFactory.getSession();
            EventDao mapper = session.getMapper( EventDao.class );
            list = mapper.getListByCreatorId( key );
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

    public List<Event> getListByUserId( Long key ) throws BoException {
        SqlSession session = null;
        List<Event> list;

        try {
            session = SessionFactory.getSession();
            EventDao mapper = session.getMapper( EventDao.class );
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

    public List<Event> getListByHardwareId( Long key ) throws BoException {
        SqlSession session = null;
        List<Event> list;

        try {
            session = SessionFactory.getSession();
            EventDao mapper = session.getMapper( EventDao.class );
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

    public List<Event> getListBySoftwareId( Long key ) throws BoException {
        SqlSession session = null;
        List<Event> list;

        try {
            session = SessionFactory.getSession();
            EventDao mapper = session.getMapper( EventDao.class );
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

    public List<Event> getListByLocationId( Long key ) throws BoException {
        SqlSession session = null;
        List<Event> list;

        try {
            session = SessionFactory.getSession();
            EventDao mapper = session.getMapper( EventDao.class );
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

    
    public Location getLocationOf(Event event) throws BoException
    {
    	if (event == null)
    	{
    		return null;
    	}
        return LocationBo.getInstance().read(event.getLocationId());
    }
    
    public Hardware getHardwareOf(Event event) throws BoException
    {
    	if (event == null)
    	{
    		return null;
    	}
        return HardwareBo.getInstance().read(event.getHardwareId());
    }
    
    public Software getSoftwareOf(Event event) throws BoException
    {
    	if (event == null)
    	{
    		return null;
    	}
        return SoftwareBo.getInstance().read(event.getSoftwareId());
    }
    
    public User getUserOf(Event event) throws BoException
    {
    	if (event == null)
    	{
    		return null;
    	}
        return UserBo.getInstance().read(event.getUserId());
    }
    
    /**
     * Creates an event and stores it into the database.
     * Returns the created event
     * 
     * throws BoException if interaction with the database throws a BoException
     */
    public Event createSystemEvent(String description, UserInfoBean creator, EventType type, Hardware hw,  Location loc, Software sw,User user) throws BoException
    {
    	//log.debug("Attempting to add " + description + " system event.");
    	//TODO maybe should check for problems and rollback session in this method since multiple
    	//queries are made
    	Event event = new Event();
    	event.setCategory(EventType.getString(type));
    	event.setDateCreated(new Date(System.currentTimeMillis()));
    	event.setDescription(description);
    	event.setCreatorId(creator.getKey());

    	if (hw != null)
		{
    		event.setHardwareId(hw.getKey());
		}
    	if (loc != null)
		{
    		event.setLocationId(loc.getKey());
		}
    	if (sw != null)
		{
    		event.setSoftwareId(sw.getKey());
		}
    	if (user != null)
		{
    		event.setUserId(user.getKey());
		}

    	create(event);
    	
    	return event;
    }
    
    public List<Event> getListByDateRange(Date start, Date end) throws BoException
    { 
    	SqlSession session = null;
        List<Event> list;

        try {
            session = SessionFactory.getSession();
            EventDao mapper = session.getMapper( EventDao.class );
            list = mapper.getListByDateRange( start, end );
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
    
    public void deleteEventsOf(ItemType type, long id) throws BoException
    {
    	switch (type)
    	{
    	case SOFTWARE:
    		SqlSession session = null;

            try {
                session = SessionFactory.getSession();
                EventDao mapper = session.getMapper( EventDao.class );
                mapper.deleteAllWithSoftwareId(id);
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
 
}