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

    public int link(Event event, Hardware hardware) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            EventToHardwareDao mapper = session.getMapper( EventToHardwareDao.class );
            result = mapper.link( event.getKey(), hardware.getKey() );
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
    
    public int unlink(Hardware hardware, Event event) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            EventToHardwareDao mapper = session.getMapper( EventToHardwareDao.class );
            result = mapper.unlink( event.getKey(), hardware.getKey() );
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
    
    public List<Event> getEventsOf(Hardware hw) throws BoException
    {
        SqlSession session = null;
        List<Event>  result = null;

        try {
            session = SessionFactory.getSession();
            EventToHardwareDao mapper = session.getMapper( EventToHardwareDao.class );
            result = mapper.getEventsByHardwareId(hw.getKey());
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
    
    public List<Event> getHardwareOf(Event event) throws BoException
    {
        SqlSession session = null;
        List<Event>  result = null;

        try {
            session = SessionFactory.getSession();
            EventToHardwareDao mapper = session.getMapper( EventToHardwareDao.class );
            result = mapper.getEventsByHardwareId(event.getKey());
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
    
    
//    private EventToHardwareBo ethb = EventToHardwareBo.getInstance();
//	private EventToSoftwareBo etsb = EventToSoftwareBo.getInstance();
//	private EventToLocationBo etlb = EventToLocationBo.getInstance();
//	private EventToUserBo etub  = EventToUserBo.getInstance();
//	
//	private SoftwareBo sb = SoftwareBo.getInstance();
//	private HardwareBo hb = HardwareBo.getInstance();
//	private LocationBo lb = LocationBo.getInstance();
//	private UserBo ub = UserBo.getInstance();
//	
//    /**
//     * @author nathaniel.lahn
//     * 
//     * @param barcode A valid barcode as defined by the barcode group (see wiki)
//     * @return All Events associated with that barcode item
//     * @throws BoException if the supplied barcode is not a valid barcode
//     */
//    public List<Event> getListByBarcode(String barcode) throws BoException {
//    	String invalidCode = barcode + " is not a valid barcode";
//    	if (barcode.length() != 13 && barcode.length() != 12)
//        {
//        	throw new BoException(invalidCode);
//        }
//    	
//    	//if the first number is 0 in the code, it is removed
//    	//this puts it back in to standardize the format
//    	if (barcode.length() == 12)
//    	{
//    		barcode = '0' + barcode;
//    	}
//    	
//    	long key = -777;
//        String type = barcode.substring(0, 3); // get the identifier character for the barcode
//        try {
//        	//get the primary key for the object in db
//        	key = Long.parseLong(barcode.substring(3, 12));
//        } catch (NumberFormatException e) {
//        	throw new BoException(invalidCode);
//        }
//        
//        //last character is garbage and not used
//        
//        
//        
//        switch (type)
//        {
//        case "001": //The object is a user (see wiki for details)
//        	return getListByUser(UserBo.getInstance().read(key));
//        case "002": //the object is a hardware
//        	return getListByHardware(HardwareBo.getInstance().read(key));
//        case "003": //the object is a software
//        	return getListBySoftware(SoftwareBo.getInstance().read(key));
//        case "004": //the object is a hardware
//        	return getListByLocation(LocationBo.getInstance().read(key));
//        default:
//        	throw new BoException("No such type prefix " + type + 
//        			" is configured for barcodes in EventBo.java");
//        }
//    }
// 
}