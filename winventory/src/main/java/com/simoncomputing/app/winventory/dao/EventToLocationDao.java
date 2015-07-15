package com.simoncomputing.app.winventory.dao;

import java.util.List;

import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.Location;

public interface EventToLocationDao { 

    // PROTECTED CODE -->

    public int link(Long eventId, Long hardwareId);
    
    public int unlink(Long eventId, Long hardwareId);
    
    public List<Event> getEventsByLocationId(Long locationId);
    
    public List<Location> getLocationByEventId(Long eventId);


}