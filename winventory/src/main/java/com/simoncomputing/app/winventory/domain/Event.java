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
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.util.Barcoder;
import com.simoncomputing.app.winventory.util.BoException;



/**
* The Event Table.
* The associated objects with this event are stored in EventTo<class name>
* where <class name> is one type of objects that may be associated with this event (ie, Hardware).
*/
public class Event {

    private Long      key;
    private Date      dateCreated;
    private String    description;          //Either a user specified description of an event, or a system specified description
    private String    category;             //For now either USER or SYSTEM depending on if it was manually or automatically made

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public Date      getDateCreated() { return dateCreated; }
    public void      setDateCreated( Date value ) { dateCreated = value; }
    public String    getDescription() { return description; }
    public void      setDescription( String value ) { description = value; }
    public String    getCategory() { return category; }
    public void      setCategory( String value ) { category = value; }
    // PROTECTED CODE -->
    


    
    /**
     * TODO make all items with barcodes implement barcodable
     * @return A list of all items with barcodes associated with this event.
     */
    public List<Barcoder> allAssociations()
    {
    	ArrayList<Barcoder> items = new ArrayList<Barcoder>();
    	throw new UnsupportedOperationException("EventBo.allAssociations() is net let implemented");
    }

    
    @Override
    public String toString()
    {
    	return String.format("%s, %s, %s, %s\n%s", key, dateCreated, description, category);
    }
    
    private static Logger logger = Logger.getLogger(Event.class);
    
    /**
     * Binds a post request containing the adduser form
     * to the user, so the user will be ready for adding/updating to the db.
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
        
        //ensure all given keys actually point to a real object
        String hardwareString = request.getParameter("hardware");
        ArrayList<Hardware> hardwareToLink = new ArrayList<Hardware>();
        
        if (hardwareString.length() > 0) 
        {
        	String[] hardware = hardwareString.split(",");
        	for (String str : hardware)
        	{
        		try {
        			logger.error(str);
        			long key = Long.parseLong(str.trim());
        			try {
        				hardwareToLink.add(HardwareBo.getInstance().read(key));
        			} catch (BoException e) {
        				errors.add("BoException thrown when reading Hardware key " + key +
        						". Does that key exist in the database?");
        			}
        			
        		} catch (NumberFormatException e){
        			errors.add("All keys must be integers");
        		}
        	}
        }
        
        String softwareString = request.getParameter("software");
        ArrayList<Software> softwareToLink = new ArrayList<Software>();
        if (softwareString.length() > 0)
        {
        	String[] software = softwareString.split(",");
        	for (String str : software)
        	{
        		try {
        			long key = Long.parseLong(str.trim());
        			try {
        				softwareToLink.add(SoftwareBo.getInstance().read(key));
        			} catch (BoException e) {
        				errors.add("BoException thrown when reading software key " + key +
        						". Does that key exist in the database?");
        			}
        			
        		} catch (NumberFormatException e){
        			errors.add("All keys must be integers");
        		}
        	}
        }

        // end form validation, return if errors exist
        if (errors.size() != 0) {
            return errors;
        }
        
        // if no errors, set the values and link the keys
        
        this.setDescription(request.getParameter("description"));
        
        //some values are automatically gathered
        this.setDateCreated(new Date(System.currentTimeMillis()));
        this.setCategory("USER");//a user created this through the insert view
        
        try {
			EventBo.getInstance().create(this);
		} catch (BoException e1) {
			logger.error("A BoException was thrown creating creating an event");
		}
        
        //Add the links to the database
        //This has to be done after the event is added. Otherwise, the current value
        //of this.getKey() won't be accurate because the db sequence won't have assigned a value
        for (Hardware hw : hardwareToLink)
        {
        	try {
				EventBo.getInstance().link(this, hw);
			} catch (BoException e) {
				logger.error("A BoException was thrown linking Event " + this.getKey() + 
						" to Hardware " + hw.getKey());
			}
        }
        
        for (Software sw : softwareToLink)
        {
        	try {
				EventBo.getInstance().link(this, sw);
			} catch (BoException e) {
				logger.error("A BoException was thrown linking Event " + this.getKey() + 
						" to Hardware " + sw.getKey());
			}
        }

        return errors;
    }

}