package com.simoncomputing.app.winventory.domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;



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
     * All the associations that this event may have as tagged by the system or the user.
     * 
     * To promote encapsulation,  a copy of the list is made, whenever the list is changed.
     * Also helps with thread safety
     * Since events will have a relatively small number of associated items, this should not be
     * very slow.
     *
     */
  
    private CopyOnWriteArrayList<Software> software;
    private CopyOnWriteArrayList<Hardware> hardware;
    private CopyOnWriteArrayList<Location> locations;
    private CopyOnWriteArrayList<User> 	users;
    
    public Event()
    {
    	software = new CopyOnWriteArrayList<Software>();
    	hardware = new CopyOnWriteArrayList<Hardware>();
    	locations = new CopyOnWriteArrayList<Location>();
    	users = new CopyOnWriteArrayList<User>();
    }

    
    public List<Software> copyOfSoftware()
    {
    	return software;
    }
    
    public List<Hardware> copyOfHardware()
    {
    	return hardware;
    }
    
    public List<Location> copyOfLocation()
    {
    	return locations;
    }
    
    public List<User> copyOfUser()
    {
    	return users;
    }
    
    /**
     * TODO make all items with barcodes implement barcodable
     * @return A list of all items with barcodes associated with this event.
     */
    public List<Barcoded> allAssociations()
    {
    	ArrayList<Barcoded> items = new ArrayList<Barcoded>();
    	throw new UnsupportedOperationException("EventBo.allAssociations() is net let implemented");
    }
}