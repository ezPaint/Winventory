package com.simoncomputing.app.winventory.domain;
import java.util.Date;



/**
* AccessToken
* 
* used when creating new Users and resetting a password
*/
public class AccessToken {

    private Integer   userKey;              //The User being created/updated	
    private String    token;
    private Date      expiration;           //Time that token expires

    public Integer   getUserKey() { return userKey; }
    public void      setUserKey( Integer value ) { userKey = value; }
    public String    getToken() { return token; }
    public void      setToken( String value ) { token = value; }
    public Date      getExpiration() { return expiration; }
    public void      setExpiration( Date value ) { expiration = value; }
    // PROTECTED CODE -->

}