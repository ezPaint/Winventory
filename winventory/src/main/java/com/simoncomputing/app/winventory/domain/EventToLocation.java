package com.simoncomputing.app.winventory.domain;


/**
* Maps events to locations
*/
public class EventToLocation {

    private Long      key;
    private Long      eventId;
    private Long      locationId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public Long      getEventId() { return eventId; }
    public void      setEventId( Long value ) { eventId = value; }
    public Long      getLocationId() { return locationId; }
    public void      setLocationId( Long value ) { locationId = value; }
    // PROTECTED CODE -->

}