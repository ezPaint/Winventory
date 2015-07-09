package com.simoncomputing.app.winventory.domain;


/**
* The Smtp Table.
*/
public class Smtp {

    private Long      key;
    private String    hostName;
    private Integer   port;
    private String    authUserName;
    private String    authPassword;
    private Boolean   ssl;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getHostName() { return hostName; }
    public void      setHostName( String value ) { hostName = value; }
    public Integer   getPort() { return port; }
    public void      setPort( Integer value ) { port = value; }
    public String    getAuthUserName() { return authUserName; }
    public void      setAuthUserName( String value ) { authUserName = value; }
    public String    getAuthPassword() { return authPassword; }
    public void      setAuthPassword( String value ) { authPassword = value; }
    public Boolean   getSsl() { return ssl; }
    public void      setSsl( boolean value ) { ssl = value ? true : false; }
    // PROTECTED CODE -->

}