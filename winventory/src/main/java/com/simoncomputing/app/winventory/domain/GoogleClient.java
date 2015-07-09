package com.simoncomputing.app.winventory.domain;


/**
* The GoogleClient Table.
*/
public class GoogleClient {

    private Long      key;
    private String    clientId;
    private String    clientSecret;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getClientId() { return clientId; }
    public void      setClientId( String value ) { clientId = value; }
    public String    getClientSecret() { return clientSecret; }
    public void      setClientSecret( String value ) { clientSecret = value; }
    // PROTECTED CODE -->

}