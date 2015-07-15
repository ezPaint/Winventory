package com.simoncomputing.app.winventory.domain;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.AddressBo;
import com.simoncomputing.app.winventory.util.BoException;



/**
* The Addresses Table.
*/
public class Address {

    private Long      key;
    private String    name;
    private String    street1;
    private String    street2;
    private String    city;
    private String    state;
    private String    zipcode;
    private Boolean   isActive;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getName() { return name; }
    public void      setName( String value ) { name = value; }
    public String    getStreet1() { return street1; }
    public void      setStreet1( String value ) { street1 = value; }
    public String    getStreet2() { return street2; }
    public void      setStreet2( String value ) { street2 = value; }
    public String    getCity() { return city; }
    public void      setCity( String value ) { city = value; }
    public String    getState() { return state; }
    public void      setState( String value ) { state = value; }
    public String    getZipcode() { return zipcode; }
    public void      setZipcode( String value ) { zipcode = value; }
    public Boolean   getIsActive() { return isActive; }
    public void      setIsActive( boolean value ) { isActive = value ? true : false; }
    // PROTECTED CODE -->
    private static Logger logger = Logger.getLogger(Address.class);
    
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
        
        // List of existing addresses needed for validation
        AddressBo addressBo = AddressBo.getInstance();
        List<Address> existingAddresses = new ArrayList<Address>();
        try {
			existingAddresses = addressBo.getAll();
		} catch (BoException e) {
			logger.error("Error getting all Addresses from AddressBo");
		}
        
        
        if (request.getParameter("name") == null) {
            errors.add("Address name field is required.");
        }
        //Address name must be unique since inserting a location
        //selects the address by address name
        for (Address a : existingAddresses) {
        	if (a.getName().equalsIgnoreCase(request.getParameter("name"))) {
        		errors.add("Address name already exists");
        	}
        }
        
        this.setName(request.getParameter("name"));
       
        if (request.getParameter("street1") == null) {
            errors.add("Street address field is required.");
        }
        this.setStreet1(request.getParameter("street1"));
        this.setStreet2(request.getParameter("street2"));
        
        if (request.getParameter("city") == null) {
            errors.add("City field is required.");
        }
        this.setCity(request.getParameter("city"));
        
        if (request.getParameter("state") == null) {
            errors.add("State field is required.");
        }
        this.setState(request.getParameter("state"));
        
        if (request.getParameter("zipcode") == null) {
            errors.add("Zipcode field is required.");
        }
        this.setZipcode(request.getParameter("zipcode"));

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
        AddressBo addressBo = AddressBo.getInstance();
        try {
            addressBo.create(this);
        } catch (BoException e) {
            errors.add("address could not be added.");
            logger.error("BoException when inserting address");
        } 
        return errors;
    }

}