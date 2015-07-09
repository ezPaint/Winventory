package com.simoncomputing.app.winventory.domain;
import java.sql.Date;




/**
* The Software Table.
*/
public class Software {

    private Long      key;
    private String    name;
    private String    serialNo;
    private String    version;
    private String    licenseKey;
    private String    description;
    private Double    cost;
    private Date      purchasedDate;
    private Date      expirationDate;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getName() { return name; }
    public void      setName( String value ) { name = value; }
    public String    getSerialNo() { return serialNo; }
    public void      setSerialNo( String value ) { serialNo = value; }
    public String    getVersion() { return version; }
    public void      setVersion( String value ) { version = value; }
    public String    getLicenseKey() { return licenseKey; }
    public void      setLicenseKey( String value ) { licenseKey = value; }
    public String    getDescription() { return description; }
    public void      setDescription( String value ) { description = value; }
    public Double    getCost() { return cost; }
    public void      setCost( Double value ) { cost = value; }
    public Date      getPurchasedDate() { return purchasedDate; }
    public void      setPurchasedDate( Date value ) { purchasedDate = value; }
    public Date      getExpirationDate() { return expirationDate; }
    public void      setExpirationDate( Date value ) { expirationDate = value; }
    // PROTECTED CODE -->

}