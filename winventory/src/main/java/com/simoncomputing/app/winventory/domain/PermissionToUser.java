package com.simoncomputing.app.winventory.domain;


/**
* The PermissionToUser Table.
*/
public class PermissionToUser {

    private Long      key;
    private String    permissionCode;
    private Integer   userId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getPermissionCode() { return permissionCode; }
    public void      setPermissionCode( String value ) { permissionCode = value; }
    public Integer   getUserId() { return userId; }
    public void      setUserId( Integer value ) { userId = value; }
    // PROTECTED CODE -->

}