package com.simoncomputing.app.winventory.domain;


/**
* RefPermissions
* 
* stores codes and descriptions for different permissions
*/
public class RefPermission {

    private Long      key;
    private String    code;                 //admin, self view, selfedit, etc.  These can be user defined.
    private String    description;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getCode() { return code; }
    public void      setCode( String value ) { code = value; }
    public String    getDescription() { return description; }
    public void      setDescription( String value ) { description = value; }
    // PROTECTED CODE -->

}