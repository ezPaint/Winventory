package com.simoncomputing.app.winventory.domain;


/**
* Maps events to hardware
*/
public class EventToHardware {

    private Long      key;
    private Long      eventId;
    private Long      hardwareId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public Long      getEventId() { return eventId; }
    public void      setEventId( Long value ) { eventId = value; }
    public Long      getHardwareId() { return hardwareId; }
    public void      setHardwareId( Long value ) { hardwareId = value; }
    // PROTECTED CODE -->

}