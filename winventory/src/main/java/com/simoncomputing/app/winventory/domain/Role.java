package com.simoncomputing.app.winventory.domain;


/**
* The Many<->Many Table describing relationship between users and permissions.
*/
public class Role {

    private Long      key;
    private String    title;                //e.g. Admin

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getTitle() { return title; }
    public void      setTitle( String value ) { title = value; }
    // PROTECTED CODE -->

}