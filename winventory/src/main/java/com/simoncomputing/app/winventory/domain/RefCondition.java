package com.simoncomputing.app.winventory.domain;


/** 
* The RefCondition Table.
 */
public class RefCondition {

    private String    code;                 //FAIR, GOOD, etc. These can be user defined.
    private String    description;          // Valid Condition for Hardware 

    public String    getCode() { return code; }
    public void      setCode( String value ) { code = value; }
    public String    getDescription() { return description; }
    public void      setDescription( String value ) { description = value; }
    // PROTECTED CODE -->

}