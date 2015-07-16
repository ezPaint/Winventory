package com.simoncomputing.app.winventory.formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.Barcoder;
import com.simoncomputing.app.winventory.util.BoException;

public class BarcodeBean extends BaseBean{
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(BarcodeBean.class);
	private static UserBo ub = UserBo.getInstance();
	private static LocationBo lb = LocationBo.getInstance();
	private static HardwareBo hb = HardwareBo.getInstance();
	private Long userId;
	private Long locationId;
	private List<Long> hardwareIds;	//ids for hardware given
	private List<Long> removalIds;	//ids for hardware to be removed UPON CONFIRMATION ONLY
	private boolean clear;			//clear all for a new search
	private boolean update;			//confirmation for addition or removal of hardware
	private String barcode;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public List<Long> getHardwareIds() {
		return hardwareIds;
	}
	
	public void setHardwareIds(List<Long> hardwareIds) {
		this.hardwareIds = hardwareIds;
	}
	
	public List<Long> getRemovalIds() {
		return removalIds;
	}

	public void setRemovalIds(List<Long> removalIds) {
		this.removalIds = removalIds;
	}

	public boolean shouldClear() {
		return clear;
	}

	public void setClear(boolean clear) {
		this.clear = clear;
	}

	public boolean shouldUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	public User getUser(){
		try {
			return ub.read(userId);
		} catch (BoException e) {
			return null;
		}
	}
	
	public Location getLocation(){
		try {
			return lb.read(locationId);
		} catch (BoException e) {
			return null;
		}
	}
	
	public ArrayList<Hardware> getHardwareList(){
		ArrayList<Hardware> ret = new ArrayList<Hardware>();
		for (Long l: hardwareIds){
			try {
				ret.add(hb.read(l));
			} catch (BoException e) {
				logger.error("invalid hardware id " + l + " given to barcode bean.");
			}
		}
		return ret;
	}

    /**
     * check if valid input
     * @return true if barcode or username is valid
     */
    
    public boolean isValidInput() {
    	return hasValidBarcode() || hasValidUsername();
    }
    
    /**
     * return if str is a valid barcode
     * @return true if the input is a valid barcode
     */
    
    private boolean hasValidBarcode(){
    	return Barcoder.barcodeErrorMessage(barcode)==null;
    }
    
    /**
     * return true if the input is the username of someone in the system
     * @return true if a User has username matching barcode input
     */
    
    private boolean hasValidUsername(){
    	return !(ub.getUserByUsername(barcode)==null);
    }
    
    /**
     * get the list of the keys for all hardware in the given list
     * @param hardware the given list of hardware
     * @return the list of keys for that hardware
     */
    
    private static List<Long> getHardwareKeys(List<Hardware> hardware){
		List<Long> hwIds = new ArrayList<Long>();
		for (Hardware h: hardware){
			hwIds.add(h.getKey());
		}
		return hwIds;
    }
    
    /**
     * @precondition the barcode must be valid input
     * assign userId, locationId, and hardwareIds based on the barcode entered
     * @return an error message if fails or a success message if it succeeds
     */
    
	public String processBarcode(){
		Object obj = Barcoder.getObject(barcode);
		Long pk;
		if (obj instanceof User){	// a User barcode
			
			/*
			 * if got a user barcode, set userId to the pk in that
			 * get rid of locationId
			 * set hardwareIds to the ones associated with that user
			 */
			User user = (User)obj;
			pk = user.getKey();
			logger.trace("preloading based on user " + pk);
			this.setUserId(((User) obj).getKey());	// assign correct user id
			this.setLocationId(null);	// ensure no location id
			try {
				this.setHardwareIds(getHardwareKeys(hb.getListByUserId(pk)));
				return "success:Successfully preloaded for " + user.getUsername();
			} catch (BoException e) {
				logger.error("Could not get list of hardware for user with pk " + pk);
				return "error:Could not load Hardware for " + user.getUsername();
			}
		} else if (obj instanceof Hardware){	// a Hardware barcode
			
			/*
			 * if got a hardware barcode, check if that hardware is already in the list
			 * if not, add it
			 * if it is, return error message claiming its already in the list
			 */
			Hardware h = (Hardware)obj;
			pk = h.getKey();
			if (getUserId()==null && getLocationId()==null){
				if (h.getUserId()==null)
					return "error:Hardware is in storage. Please begin by entering a user or location.";
				setUserId(h.getUserId());
				try {
					this.setHardwareIds(getHardwareKeys(hb.getListByUserId(getUserId())));
					List<Long> added = this.getRemovalIds();
					added.add(h.getKey());
					this.setRemovalIds(added);
					return "success:Successfully preloaded for hardware with key " + pk;
				} catch (BoException e) {
					logger.error("Could not get list of hardware for user with pk " + pk);
					return "error:Could not preload for hardware with key " + pk;
				}
			}
			else {
				logger.trace("adding to hardware list in barcode page");
				logger.debug("hardwareIds before processing is " + hardwareIds);
				if (!hardwareIds.contains(pk)){				// check if that reference is already in the list
					hardwareIds.add(pk);						// add the piece of hardware if not already present
					logger.trace("hardwareIds after processing is " + hardwareIds);
					return "success:Hardware with key " + pk + " sucessfully added";
				}
				else {
					logger.trace("attempted to add duplicate hardware in barcode page");
					return "error:Hardware with key " + pk + " is already in the list.";
				}
			}
		}else if (obj instanceof Location) {	// if a Location barcode
			
			/*
			 * if have a location barcode, null userId and set locationId
			 * get hardwareIds based on locationId
			 */
			Location loc = (Location)obj;
			pk = loc.getKey();
			logger.trace("preloading based on location " + pk);
			this.setUserId(null);	// ensure no user on the session
			this.setLocationId(pk);	// put correct location on the session
			try {
				this.setHardwareIds(getHardwareKeys(hb.getListByLocationId(pk)));
				return "success:Successfully preloaded for " + loc.getDescription();
			} catch (BoException e) {
				logger.error("Could not get list of hardware for location with pk " + pk);
				return "error:Could not load Hardware for " + loc.getDescription();
			}
		}else{// if (obj == null){	// entered a username
			
			/*
			 * this is why there is a precondition of valid input
			 * if input is valid, can only reached default if entered a
			 * valid username
			 */
			
			logger.trace("preloading based on username " + barcode);
			User user = ub.getUserByUsername(barcode);		// get user
			pk = user.getKey();
			this.setLocationId(null);	// ensure no location on session
			this.setUserId(pk);	// set user attribute appropriately
			try {
				this.setHardwareIds(getHardwareKeys(hb.getListByUserId(pk)));
				return "success:Successfully preloaded for " + user.getUsername();
			} catch (BoException e) {
				logger.error("Could not get list of hardware for user with pk " + pk);
				return "error:Could not load Hardware for " + user.getUsername();
			}
		}
	}
    
	public BarcodeBean() {}

	/**
	 * Bind Form bean to an HTTP request from parameters
	 * @param request
	 */
	
	public void bind(HttpServletRequest request) {
		
		/*
		 * get userId from request
		 * set userId to null if request gives null or empty string
		 * otherwise, set it to the Long value given
		 */
		
		String userId = request.getParameter("userId");
		if (userId==null || userId.equals(""))
			this.setUserId(null);
		else
			this.setUserId(Long.parseLong(userId));
		
		/*
		 * get locationId from request
		 * set it to null if request gives null or empty string
		 * otherwise, set it to the Long value given
		 */
		
		String locId = request.getParameter("locationId");
		if (locId==null || locId.equals(""))
			this.setLocationId(null);
		else
			this.setLocationId(Long.parseLong(locId));
		
		/*
		 * get the list of hardwareIds from the request, ignore if null
		 * set hardwareIds to the the values given
		 */
		
		List<Long> hwIds = new ArrayList<Long>();
		String[] hardwarePersist = request.getParameterValues("hardwareId");
		if(hardwarePersist!=null){
			logger.debug("hardwareIds is " + getHardwareIds() + " before binding");
			for (String s: hardwarePersist){
				if (!s.equals(""))
					hwIds.add(Long.parseLong(s));
			}
		}
		this.setHardwareIds(hwIds);		
		logger.debug("hardwareIds is " + getHardwareIds() + " after binding");
		
		/*
		 * get the list of removal ids from the request, ignore if null
		 * set removalIds to those values
		 */
		
		List<Long> remIds = new ArrayList<Long>();
		String[] removeHardware = request.getParameterValues("removeHw");
		if (removeHardware != null){
			for (String s: removeHardware){
				if (!s.equals(""))
					remIds.add(Long.parseLong(s));
			}
		}
		this.setRemovalIds(remIds);
		
		// get the clear boolean from the request
		this.setClear(Boolean.parseBoolean(request.getParameter("clear")));
		
		// get the update boolean from the request
		this.setUpdate(Boolean.parseBoolean(request.getParameter("toSubmit")));
		
		// get the barcode from the request
		this.setBarcode(request.getParameter("barcode"));
	}
}
