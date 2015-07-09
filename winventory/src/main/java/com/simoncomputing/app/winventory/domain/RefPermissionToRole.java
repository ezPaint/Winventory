package com.simoncomputing.app.winventory.domain;


/**
* The Many<->Many Table describing relationship between roles and permissions.
*/
public class RefPermissionToRole {

    private Long      key;
    private Integer   permissionId;
    private Integer   roleId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public Integer   getPermissionId() { return permissionId; }
    public void      setPermissionId( Integer value ) { permissionId = value; }
    public Integer   getRoleId() { return roleId; }
    public void      setRoleId( Integer value ) { roleId = value; }
    // PROTECTED CODE -->

}