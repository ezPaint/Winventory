package com.simoncomputing.app.winventory.domain;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.simoncomputing.app.winventory.bo.HardwareToSoftwareBo;
import com.simoncomputing.app.winventory.util.BoException;



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
    private Boolean   isActive;

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
    public Boolean   getIsActive() { return isActive; }
    public void      setIsActive( boolean value ) { isActive = value ? true : false; }
    // PROTECTED CODE -->
    
    /**
    /**
     * Two Software objects are equal if they have the same:
     *    key
     *    name
     *    expiration date
     *    purchased date
     */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!o.getClass().equals(this.getClass())) {
            return false;
        } else {
            Software other = (Software) o;

            return (this.key.equals(other.key) && this.name.equals(other.name) && 
                this.purchasedDate.equals(other.purchasedDate) && this.expirationDate
                        .equals(other.expirationDate));
        }

    }
    
    /**
     * Checks if two software objects have the same purchased and expiration dates.
     */
    public boolean compareDates(Software s){
        return (this.getPurchasedDate().equals(s.getPurchasedDate())
            && this.getExpirationDate().equals(s.getExpirationDate()));
    }
    
    
    /**
     * Shows the user which hardware items are associated with this software item.
     * @return
     * @throws BoException
     */
    public String associatedHardware() throws BoException{
        List<Hardware> hw = HardwareToSoftwareBo.getInstance().getHardwareBySoftwareId(key);
        ArrayList<Long> keys = new ArrayList<Long>();
        for (Hardware h : hw){
            Long id = h.getKey();
            keys.add(id);
        }
        String s = "";
        for (int i = 0; i < keys.size(); i++) {
            if (i < keys.size() - 1){
                s += keys.get(i) + ", ";
            } else {
                s += keys.get(i);
            }
        }
        return s;
    }
    
	@Override
	public String toString() {
		return "Software [key=" + key + ", name=" + name + ", serialNo=" + serialNo + ", version="
				+ version + ", licenseKey=" + licenseKey + ", description=" + description
				+ ", cost=" + cost + ", purchasedDate=" + purchasedDate + ", expirationDate="
				+ expirationDate + ", isActive=" + isActive + "]";
	}
}