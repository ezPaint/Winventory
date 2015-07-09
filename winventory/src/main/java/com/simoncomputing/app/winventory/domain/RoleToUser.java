package com.simoncomputing.app.winventory.domain;


/**
* The Many<->Many Table describing relationship between roles and users.
*/
public class RoleToUser {

    private Long      key;
    private Integer   roleId;
    private Integer   userId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public Integer   getRoleId() { return roleId; }
    public void      setRoleId( Integer value ) { roleId = value; }
    public Integer   getUserId() { return userId; }
    public void      setUserId( Integer value ) { userId = value; }
    // PROTECTED CODE -->

}