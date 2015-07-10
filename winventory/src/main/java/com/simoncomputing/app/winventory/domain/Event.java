package com.simoncomputing.app.winventory.domain;
import java.util.ArrayList;
import java.util.Calendar;
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

    
    public List<Software> getSoftware()
    {
    	return software;
    }
    
    public List<Hardware> getHardware()
    {
    	return hardware;
    }
    
    public List<Location> getLocations()
    {
    	return locations;
    }
    
    public List<User> getUsers()
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
    
    //Test for every field being equal
    
    @Override
    public boolean equals(Object other)
    {
    	if (!other.getClass().equals(this.getClass()))
    	{
    		return false;
    	}
    	Event otherEvent = (Event)other;
    	
    	Calendar cal1 = Calendar.getInstance();
    	Calendar cal2 = Calendar.getInstance();
    	cal1.setTime(this.dateCreated);
    	cal2.setTime(otherEvent.dateCreated);
    	boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
    	                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    	
    	return 	
    			this.deepEquals(this.getSoftware(), otherEvent.getSoftware()) &&
//    			this.deepEquals(this.getHardware(), otherEvent.getHardware()) &&
//    			this.deepEquals(this.getLocations(), otherEvent.getLocations()) &&
//    			this.deepEquals(this.getUsers(), otherEvent.getUsers()) &&
    			this.category.equals(otherEvent.category) &&
    			sameDay &&  
    			this.description.equals(otherEvent.getDescription()) &&
    			this.key.equals(otherEvent.getKey());
    }
    
    @Override
    public String toString()
    {
    	return String.format("%s, %s, %s, %s\n%s", key, dateCreated, description, category, software);
    }
    
    /**
     * @return Whether every object in list one is equal to the corresponding object in List two
     * Uses .equals on the objects so that should be implemented.
     */
    private boolean deepEquals(List one, List two)
    {
    	if (one.size() != two.size())
    	{
    		return false;
    	}
    	for (int i = 0;i < one.size(); i++)
    	{
    		if (!one.get(i).equals(two.get(i)))
    		{
    			return false;
    		}
    	}
    	return true;
    }
}