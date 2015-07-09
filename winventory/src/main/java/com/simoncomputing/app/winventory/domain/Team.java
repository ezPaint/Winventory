package com.simoncomputing.app.winventory.domain;


/**
* The Teams Table.
*/
public class Team {

    private Long      key;
    private String    name;
    private Boolean   isActive;
    private Integer   leaderId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getName() { return name; }
    public void      setName( String value ) { name = value; }
    public Boolean   getIsActive() { return isActive; }
    public void      setIsActive( boolean value ) { isActive = value ? true : false; }
    public Integer   getLeaderId() { return leaderId; }
    public void      setLeaderId( Integer value ) { leaderId = value; }
    // PROTECTED CODE -->

}