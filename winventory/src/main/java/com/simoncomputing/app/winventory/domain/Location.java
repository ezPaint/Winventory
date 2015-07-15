package com.simoncomputing.app.winventory.domain;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.AddressBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.util.BoException;



/**
* The Location Table.
*/
public class Location  implements Item{

    private Long      key;
    private String    description;          //Specific location of item at specified address (e.g. suite 200, desk #3)
    private Boolean   isActive;
    private Integer   addressId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getDescription() { return description; }
    public void      setDescription( String value ) { description = value; }
    public Boolean   getIsActive() { return isActive; }
    public void      setIsActive( boolean value ) { isActive = value ? true : false; }
    public Integer   getAddressId() { return addressId; }
    public void      setAddressId( Integer value ) { addressId = value; }
    // PROTECTED CODE -->
    private static Logger logger = Logger.getLogger(Location.class);
    
    /**
     * Binds a post request containing the insertlocation form
     * to the location, so the location will be ready for adding/updating to the db.
     * @param request
     * @return errors an arraylist of error messages
     */
    public ArrayList<String> bindInsertForm(HttpServletRequest request) {
        // FORM VALIDATION
        
        // ArrayList of errors to display for form validation problems
        ArrayList<String> errors = new ArrayList<String>();
        
        // Set description
        if (request.getParameter("description") == null) {
            errors.add("description field is required.");
        }
        this.setDescription(request.getParameter("description"));
        
        // isActive is true if true, false if null
        this.setIsActive(request.getParameter("isActive") != null);
        
        // find the address id for the specified address name, and set location's address id to match
        String addressKey = request.getParameter("address");
        this.setAddressId( Integer.parseInt(addressKey) );
        
        // Done, return the empty errors ArrayList
        return errors;
    }
    
    /**
     * Saves the location to the db
     * if empty arraylist is returned, the location was saved. else, not saved.
     * @return errors array list of error messages when saving.
     */
    public ArrayList<String> create() {
        ArrayList<String> errors = new ArrayList<String>();
        LocationBo locationBo = LocationBo.getInstance();
        try {
            locationBo.create(this);
        } catch (BoException e) {
            errors.add("location could not be added.");
            logger.error("BoException when inserting location");
        } 
        return errors;
    }

}