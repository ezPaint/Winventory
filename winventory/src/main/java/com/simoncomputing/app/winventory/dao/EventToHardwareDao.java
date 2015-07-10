package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.EventToHardware;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.util.DaoException;

public interface EventToHardwareDao { 
    
    public int create( EventToHardware value ) throws DaoException;

    public int update( EventToHardware value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public EventToHardware read( Map<String, Object> map ) throws DaoException;

    // PROTECTED CODE -->

    public int link(Long eventId, Long hardwareId);
    
    public int unlink(Long eventId, Long hardwareId);
    
    public List<Event> getEventsByHardwareId(Long hardwareId);
    
    public List<Hardware> getHardwareByEventId(Long eventId);

}