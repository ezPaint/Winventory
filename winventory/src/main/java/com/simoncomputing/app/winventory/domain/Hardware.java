package com.simoncomputing.app.winventory.domain;
import java.util.Date;



/**
* The Hardware Table.
*/
public class Hardware {

    private Long      key;
    private String    type;                 //LAPTOP, DESKTOP, MONTIOR, etc. Links to RefHardwareType
    private String    description;
    private Double    cost;
    private String    serialNo;
    private String    condition;            //GOOD, FAIR, NEW, etc. Links to RefCondition
    private Integer   locationId;
    private Integer   userId;
    private Date      purchaseDate;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getType() { return type; }
    public void      setType( String value ) { type = value; }
    public String    getDescription() { return description; }
    public void      setDescription( String value ) { description = value; }
    public Double    getCost() { return cost; }
    public void      setCost( Double value ) { cost = value; }
    public String    getSerialNo() { return serialNo; }
    public void      setSerialNo( String value ) { serialNo = value; }
    public String    getCondition() { return condition; }
    public void      setCondition( String value ) { condition = value; }
    public Integer   getLocationId() { return locationId; }
    public void      setLocationId( Integer value ) { locationId = value; }
    public Integer   getUserId() { return userId; }
    public void      setUserId( Integer value ) { userId = value; }
    public Date      getPurchaseDate() { return purchaseDate; }
    public void      setPurchaseDate( Date value ) { purchaseDate = value; }
    // PROTECTED CODE -->
    public String getShortDescription() {
    	if (description.length()>22)
    		return description.substring(0,20);
    	else
    		return description;
    }
}