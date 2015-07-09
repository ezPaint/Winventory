package com.simoncomputing.app.winventory.domain;


/**
* The HardwareToSoftware Table.
*/
public class HardwareToSoftware {

    private Long      key;
    private Integer   hardwareId;
    private Integer   softwareId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public Integer   getHardwareId() { return hardwareId; }
    public void      setHardwareId( Integer value ) { hardwareId = value; }
    public Integer   getSoftwareId() { return softwareId; }
    public void      setSoftwareId( Integer value ) { softwareId = value; }
    // PROTECTED CODE -->

}