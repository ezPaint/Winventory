package com.simoncomputing.app.winventory.domain;


/**
* Maps events to software
*/
public class EventToSoftware {

    private Long      key;
    private Long      eventId;
    private Long      softwareId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public Long      getEventId() { return eventId; }
    public void      setEventId( Long value ) { eventId = value; }
    public Long      getSoftwareId() { return softwareId; }
    public void      setSoftwareId( Long value ) { softwareId = value; }
    // PROTECTED CODE -->

}