package com.simoncomputing.app.winventory.domain;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.util.BoException;



/**
* The Hardware Table.
*/
public class Hardware implements Item {

    private Long      key;
    private String    type;                 //LAPTOP, DESKTOP, MONTIOR, etc. Links to RefHardwareType
    private String    description;
    private Double    cost;
    private String    serialNo;
    private String    condition;            //GOOD, FAIR, NEW, etc. Links to RefCondition
    private Long      locationId;
    private Long      userId;
    private Date      purchaseDate;
    private Boolean   isActive;

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
    public Long      getLocationId() { return locationId; }
    public void      setLocationId( Long value ) { locationId = value; }
    public Long      getUserId() { return userId; }
    public void      setUserId( Long value ) { userId = value; }
    public Date      getPurchaseDate() { return purchaseDate; }
    public void      setPurchaseDate( Date value ) { purchaseDate = value; }
    public Boolean   getIsActive() { return isActive; }
    public void      setIsActive( boolean value ) { isActive = value ? true : false; }
    // PROTECTED CODE -->
    private static LocationBo lb = LocationBo.getInstance();
    private static UserBo ub = UserBo.getInstance();

    public String getShortDescription() {
        if (description.length() > 22)
            return description.substring(0, 20);
        else
            return description;
    }

    public Location getLocation(){
    	try {
			return lb.read(locationId);
		} catch (BoException e) {
			return null;
		}
    }
    
    public User getUser(){
    	try {
			return ub.read(userId);
		} catch (BoException e) {
			return null;
		}
    }
    
    public ArrayList<String> bind(HttpServletRequest request) {

        ArrayList<String> errors = new ArrayList<String>();

        if (request.getParameter("type") != null) {
            this.setType(request.getParameter("type"));
        } else {
            errors.add("Type is required");
        }

        if (request.getParameter("condition") != null) {
            this.setCondition(request.getParameter("condition"));
        } else {
            errors.add("Condition is required");
        }

        if (request.getParameter("cost") != null) {
            this.setCost(Double.parseDouble(request.getParameter("cost")));
        } else {
            errors.add("Cost is required");
        }

        if (request.getParameter("description") != null) {
            this.setDescription(request.getParameter("description"));
        } else {
            errors.add("Description is required");
        }

        if (request.getParameter("serialNo") != null) {
            this.setSerialNo(request.getParameter("serialNo"));
        } else {
            errors.add("Serial number is required");
        }

        System.out.println(request.getParameter("isActive"));
        if (request.getParameter("isActive") != null
                && request.getParameter("isActive").equals("true")) {
            this.isActive = true;
        } else {
            this.isActive = false;
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            if (request.getParameter("date") != null) {
                this.setPurchaseDate(format.parse(request.getParameter("date")));
            } else {
                errors.add("Date is required");
            }
        } catch (ParseException e) {
            errors.add("Error parsing date");
        }

        String insertWith = request.getParameter("insertWith");

        if (insertWith != null && insertWith.equals("user")) {

            String username = request.getParameter("username");

            if (username != null && !username.equals("")) {
                User u = UserBo.getInstance().getUserByUsername(username);

                if (u != null) {
                    this.setUserId(u.getKey());
                    this.setLocationId(null);
                } else {
                    errors.add("That user does not exist");
                }

            } else {
                errors.add("Username required");
            }

        } else if (insertWith != null && insertWith.equals("location")) {
            String locationID = request.getParameter("locID");

            if (locationID != null && !locationID.equals("")) {
                Long locID = Long.parseLong(request.getParameter("locID"));

                try {
                    Location l = LocationBo.getInstance().read(locID);

                    if (l != null) {
                        this.setLocationId(l.getKey());
                        this.setUserId(null);
                    } else {
                        errors.add("Location ID does not exist");
                    }
                } catch (BoException e) {
                    errors.add("Error with location");
                }

            } else {
                errors.add("Location required");
            }
        } else {
            errors.add("Must insert by user or location");
        }

        return errors;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Hardware other = (Hardware) obj;
        if (condition == null) {
            if (other.condition != null)
                return false;
        } else if (!condition.equals(other.condition))
            return false;
        if (cost == null) {
            if (other.cost != null)
                return false;
        } else if (!cost.equals(other.cost))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (locationId == null) {
            if (other.locationId != null)
                return false;
        } else if (!locationId.equals(other.locationId))
            return false;
        if (purchaseDate == null) {
            if (other.purchaseDate != null)
                return false;
        } else if (!purchaseDate.equals(other.purchaseDate))
            return false;
        if (serialNo == null) {
            if (other.serialNo != null)
                return false;
        } else if (!serialNo.equals(other.serialNo))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }
}