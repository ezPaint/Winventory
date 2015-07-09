package com.simoncomputing.app.winventory.domain;


/**
 * The RefHardwareType Table.
 */
public class RefHardwareType {

    private String    code;                 //LAPTOP, MOUSE, KEYBOARD, etc. These can be user-defined.
    private String    description;

    public String    getCode() { return code; }
    public void      setCode( String value ) { code = value; }
    public String    getDescription() { return description; }
    public void      setDescription( String value ) { description = value; }
    // PROTECTED CODE -->

}