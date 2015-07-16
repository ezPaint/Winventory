package com.simoncomputing.app.winventory.domain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.EventBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.formbean.UserInfoBean;
import com.simoncomputing.app.winventory.util.Barcoder;
import com.simoncomputing.app.winventory.util.BoException;



/**
* The Event Table.
* The associated objects with this event are stored in EventTo<class name>
* where <class name> is one type of objects that may be associated with this event (ie, Hardware).
*/
public class Event implements Item {

    private Long      key;
    private Date      dateCreated;
    private String    description;          //Either a user specified description of an event, or a system specified description
    private String    category;             //For now either USER or SYSTEM depending on if it was manually or automatically made
    private Long      creatorId;            //The user that created this object
    private Long      userId;
    private Long      hardwareId;
    private Long      softwareId;
    private Long      locationId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public Date      getDateCreated() { return dateCreated; }
    public void      setDateCreated( Date value ) { dateCreated = value; }
    public String    getDescription() { return description; }
    public void      setDescription( String value ) { description = value; }
    public String    getCategory() { return category; }
    public void      setCategory( String value ) { category = value; }
    public Long      getCreatorId() { return creatorId; }
    public void      setCreatorId( Long value ) { creatorId = value; }
    public Long      getUserId() { return userId; }
    public void      setUserId( Long value ) { userId = value; }
    public Long      getHardwareId() { return hardwareId; }
    public void      setHardwareId( Long value ) { hardwareId = value; }
    public Long      getSoftwareId() { return softwareId; }
    public void      setSoftwareId( Long value ) { softwareId = value; }
    public Long      getLocationId() { return locationId; }
    public void      setLocationId( Long value ) { locationId = value; }
    // PROTECTED CODE -->
    
    public User getUser() {
        User u = null;
        try {
             u = UserBo.getInstance().read(this.creatorId);
        } catch (BoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return u;
    }
    
    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s\n%s", key, dateCreated, description, category);
    }

    private static Logger logger = Logger.getLogger(Event.class);

    /**
     * Binds a post request containing the adduser form to the user, so the user
     * will be ready for adding/updating to the db.
     * 
     * @param request
     * @return errors an arraylist of error messages
     */
    public ArrayList<String> bindAndStore(HttpServletRequest request) {
        // FORM VALIDATION

        // ArrayList of errors to display for form validation problems
        ArrayList<String> errors = new ArrayList<String>();

        // ensure all required parameters exist
        if (request.getParameter("description") == null) {
            errors.add("Description field is required.");
        }

        // ensure all given keys actually point to a real object
        String hardwareString = request.getParameter("hardware");
        String softwareString = request.getParameter("software");
        String locString = request.getParameter("location");
        String userString = request.getParameter("user");
        long hkey = 0, skey = 0, lkey = 0, ukey = 0;
        
        if (!hardwareString.isEmpty())
        {
        
        	if (hardwareString.length() > 10)
        	{
        		Object obj = Barcoder.getObject(hardwareString);
        		if (obj == null)
        		{
        			errors.add("Hardware " + hardwareString + " not found in database");
        		}
        		else 
        		{
        			hkey = ((Hardware)obj).getKey();
        		}
        	}
        	else
        	{
        		try {
    	        	hkey = Long.parseLong(hardwareString);
    	        } catch(NumberFormatException nfe) {
    	        	errors.add("Hardware " + hardwareString + " not found in database");
    	        }
        	}
        }
        
        if (!softwareString.isEmpty())
        {
        
        	if (softwareString.length() > 10)//Attempt at trying to read both barcodes and keys here, but doesn't work yet.
        	{
        		Object obj = Barcoder.getObject(softwareString);
        		if (obj == null)
        		{
        			errors.add("Software " + softwareString + " not found in database");
        		}
        		else 
        		{
        			hkey = ((Software)obj).getKey();
        		}
        	}
        	else
        	{
        		try {
    	        	hkey = Long.parseLong(softwareString);
    	        } catch(NumberFormatException nfe) {
    	        	errors.add("Hardware " + softwareString + " not found in database");
    	        }
        	}
        }
        
        if (!locString.isEmpty())
        {
	        
	        
	        try {
	        	lkey = Long.parseLong(locString);
	        } catch(NumberFormatException nfe) {
	        	errors.add("Location key must be an integer");
	        }
        }
        
      
        if (!userString.isEmpty())
        {
	        
	        
	        try {
	        	ukey = Long.parseLong(userString);
	        } catch(NumberFormatException nfe) {
	        	errors.add("User key must be an integer");
	        }
	        
	        // end form validation, return if errors exist
	        if (errors.size() != 0) {
	            return errors;
	        }
        }
        // if no errors, set the values and link the keys

        this.setDescription(request.getParameter("description"));

        // some values are automatically gathered
        this.setDateCreated(new Date(System.currentTimeMillis()));

        this.setCategory(EventType.getString(EventType.MANUAL));// a user
        // created this
        // through the
        // insert view
        UserInfoBean uib = (UserInfoBean)request.getSession().getAttribute("userInfo");
        this.setCreatorId(uib.getKey());
        

        this.setCategory(EventType.getString(EventType.MANUAL));// a user
                                                                // created this
                                                                // through the
                                                                // insert view

        this.setHardwareId(hkey);
        this.setSoftwareId(skey);
        this.setLocationId(lkey);
        this.setUserId(ukey);
        
        //Create the entry into the event table in db
        try {
            EventBo.getInstance().create(this);
        } catch (BoException e1) {
            logger.info("A BoException was thrown creating creating an event.");
            logger.info("Perhaps a key was wrong?");
            logger.info(String.format("HwId: %d, swId: %d, locId: %d, userId: %d", 
            		this.getHardwareId(), this.getSoftwareId(), 
            		this.getLocationId(), this.getUserId()));
            errors.add("One or more keys is invalid. Are you sure all the associated items exist?");
        }
        

        return errors;
    }

}