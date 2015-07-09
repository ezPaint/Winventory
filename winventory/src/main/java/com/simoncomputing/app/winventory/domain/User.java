package com.simoncomputing.app.winventory.domain;


/**
* The User Table.
*/
public class User {

    private Long      key;
    private String    username;
    private String    password;             //not necessarily the actual password, but some encrypted version
    private String    firstName;
    private String    lastName;
    private String    email;
    private String    cellPhone;
    private String    workPhone;
    private Boolean   isActive;
    private Integer   roleId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getUsername() { return username; }
    public void      setUsername( String value ) { username = value; }
    public String    getPassword() { return password; }
    public void      setPassword( String value ) { password = value; }
    public String    getFirstName() { return firstName; }
    public void      setFirstName( String value ) { firstName = value; }
    public String    getLastName() { return lastName; }
    public void      setLastName( String value ) { lastName = value; }
    public String    getEmail() { return email; }
    public void      setEmail( String value ) { email = value; }
    public String    getCellPhone() { return cellPhone; }
    public void      setCellPhone( String value ) { cellPhone = value; }
    public String    getWorkPhone() { return workPhone; }
    public void      setWorkPhone( String value ) { workPhone = value; }
    public Boolean   getIsActive() { return isActive; }
    public void      setIsActive( boolean value ) { isActive = value ? true : false; }
    public Integer   getRoleId() { return roleId; }
    public void      setRoleId( Integer value ) { roleId = value; }
    // PROTECTED CODE -->

}