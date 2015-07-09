package com.simoncomputing.app.winventory.domain;


/**
* The Location Table.
*/
public class Location {

    private Long      key;
    private String    description;          //Specific location of item at specified address (e.g. suite 200, desk #3)
    private Boolean   isActive;
    private Integer   addressId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getDescription() { return description; }
    public void      setDescription( String value ) { description = value; }
    public Boolean   getIsActive() { return isActive; }
    public void      setIsActive( boolean value ) { isActive = value ? true : false; }
    public Integer   getAddressId() { return addressId; }
    public void      setAddressId( Integer value ) { addressId = value; }
    // PROTECTED CODE -->

}