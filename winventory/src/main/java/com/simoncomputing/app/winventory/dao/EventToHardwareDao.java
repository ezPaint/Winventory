package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.util.DaoException;

public interface EventToHardwareDao { 

    // PROTECTED CODE -->

    public int link(Long eventId, Long hardwareId);
    
    public int unlink(Long eventId, Long hardwareId);
    
    public List<Event> getEventsByHardwareId(Long hardwareId);
    
    public List<Hardware> getHardwareByEventId(Long eventId);

}