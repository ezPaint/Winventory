package com.simoncomputing.app.winventory.domain;


/**
* Maps events to users
*/
public class EventToUser {

    private Long      key;
    private Long      eventId;
    private Long      userId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public Long      getEventId() { return eventId; }
    public void      setEventId( Long value ) { eventId = value; }
    public Long      getUserId() { return userId; }
    public void      setUserId( Long value ) { userId = value; }
    // PROTECTED CODE -->

}