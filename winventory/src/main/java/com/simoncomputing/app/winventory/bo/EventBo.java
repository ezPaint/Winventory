package com.simoncomputing.app.winventory.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.BoException;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.EventToHardware;
import com.simoncomputing.app.winventory.domain.EventToLocation;
import com.simoncomputing.app.winventory.domain.EventToSoftware;
import com.simoncomputing.app.winventory.domain.EventToUser;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.domain.User;

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

    // PROTECTED CODE -->

    /**
     * @author nathaniel.lahn
     * 
     * @param barcode A valid barcode as defined by the barcode group (see wiki)
     * @return All Events associated with that barcode item
     * @throws BoException if the supplied barcode is not a valid barcode
     */
    public List<Event> getListByBarcode(String barcode) throws BoException {
    	String invalidCode = barcode + " is not a valid barcode";
    	if (barcode.length() != 13 && barcode.length() != 12)
        {
        	throw new BoException(invalidCode);
        }
    	
    	//if the first number is 0 in the code, it is removed
    	//this puts it back in to standardize the format
    	if (barcode.length() == 12)
    	{
    		barcode = '0' + barcode;
    	}
    	
    	long key = -777;
        String type = barcode.substring(0, 3); // get the identifier character for the barcode
        try {
        	//get the primary key for the object in db
        	key = Long.parseLong(barcode.substring(3, 12));
        } catch (NumberFormatException e) {
        	throw new BoException(invalidCode);
        }
        
        //last character is garbage and not used
        
        
        
        switch (type)
        {
        case "001": //The object is a user (see wiki for details)
        	return getListByUser(UserBo.getInstance().read(key));
        case "002": //the object is a hardware
        	return getListByHardware(HardwareBo.getInstance().read(key));
        case "003": //the object is a software
        	return getListBySoftware(SoftwareBo.getInstance().read(key));
        case "004": //the object is a hardware
        	return getListByLocation(LocationBo.getInstance().read(key));
        default:
        	throw new BoException("No such type prefix " + type + 
        			" is configured for barcodes in EventBo.java");
        }
    }
    
    public List<Event> getListByUser(User user) throws BoException {
    	EventToUserBo bo = EventToUserBo.getInstance();
    	List<Event> toReturn = new ArrayList<Event>();
    	
    	for (EventToUser item : bo.getListByUserId(user.getKey()))
    	{
    		toReturn.add(this.read(item.getEventId()));
    	}
    	return toReturn;
    }
    
    public List<Event> getListByLocation(Location user) throws BoException {
    	EventToLocationBo bo = EventToLocationBo.getInstance();
    	List<Event> toReturn = new ArrayList<Event>();
    	
    	for (EventToLocation item : bo.getListByLocationId(user.getKey()))
    	{
    		toReturn.add(this.read(item.getEventId()));
    	}
    	return toReturn;
    }
    
    public List<Event> getListByHardware(Hardware user) throws BoException {
    	EventToHardwareBo bo = EventToHardwareBo.getInstance();
    	List<Event> toReturn = new ArrayList<Event>();
    	
    	for (EventToHardware item : bo.getListByHardwareId(user.getKey()))
    	{
    		toReturn.add(this.read(item.getEventId()));
    	}
    	return toReturn;
    }
    
    public List<Event> getListBySoftware(Software user) throws BoException {
    	EventToSoftwareBo bo = EventToSoftwareBo.getInstance();
    	List<Event> toReturn = new ArrayList<Event>();
    	
    	for (EventToSoftware item : bo.getListBySoftwareId(user.getKey()))
    	{
    		toReturn.add(this.read(item.getEventId()));
    	}
    	return toReturn;
    }
    
    /**
     * The database doesn't store the barcodable items associated with Events in the Event table,
     * 	It stores them in tables such as EventToHardware
     * However, the Event domain object should have associated items in it.
     * 
     * The purpose of this and all following crud operations are to create an event object in the
     * database AND store add all necessary relations to the relations tables, which Batgen does
     * not handle with its CRUD operations for this class.
     * 
     * In general, these methods should be used instead of the Batgen methods. The other methods
     * will NOT store associations.
     * 
     * @param e The event to create
     */
    public void createAndAssociate(Event e)
    {
    	//remember to call session.commit!!
    	//TODO
    }
    
    /**
     * Updates the event as seen in update()
     * Also, updates association tables to the current state of the Event parameter
     * @param e The event to update
     */
    public void updateAndAssociate(Event e)
    {
    	//TODO
    }
    
    /**
     * Reads the event as seen in read()
     * Also, updates domain object item associations based on association tables
     * @param e The event to update
     */
    public void readWithAssociations(Event e)
    {
    	//TODO
    }
    
    /**
     * Deletes the event as seen in delete()
     * Also, deletes rows with this event in the association tables
     * @param e The event to update
     */
    public void deleteWithAssociations(Event e)
    {
    	//TODO
    }
}