package com.simoncomputing.app.winventory.domain;


/**
* The Addresses Table.
*/
public class Address {

    private Long      key;
    private String    street1;
    private String    street2;
    private String    city;
    private String    state;
    private String    zipcode;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getStreet1() { return street1; }
    public void      setStreet1( String value ) { street1 = value; }
    public String    getStreet2() { return street2; }
    public void      setStreet2( String value ) { street2 = value; }
    public String    getCity() { return city; }
    public void      setCity( String value ) { city = value; }
    public String    getState() { return state; }
    public void      setState( String value ) { state = value; }
    public String    getZipcode() { return zipcode; }
    public void      setZipcode( String value ) { zipcode = value; }
    // PROTECTED CODE -->

}