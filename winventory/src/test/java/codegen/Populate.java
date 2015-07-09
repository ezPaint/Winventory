package codegen;

import java.sql.Date;
import java.util.Random;

import com.simoncomputing.app.winventory.bo.AddressBo;
import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.LocationBo;
import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.bo.RefHardwareTypeBo;
import com.simoncomputing.app.winventory.bo.RefPermissionBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.bo.TeamBo;
import com.simoncomputing.app.winventory.bo.TeamToUserBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.dao.SessionFactory;
import com.simoncomputing.app.winventory.domain.Address;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.domain.RefHardwareType;
import com.simoncomputing.app.winventory.domain.RefPermission;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.domain.Team;
import com.simoncomputing.app.winventory.domain.TeamToUser;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;



public class Populate {
	public static void main(String[] args) throws BoException
	{
		SessionFactory.initializeForTest();
		
		
		new Populate();
	}
	public Populate() throws BoException
	{	
		this.addTonsToUser();
		this.addTonsToTeam();
		this.addTonsToAddress();
		this.addTonsToLocations();
		this.addTonsToRefPermission();
		this.addTonsToRefCondition();
		this.addTonsToRefHardwareType();
		this.addTonsToHardware();
		this.addTonsToSoftware();
		this.addTonsToTeamToUser();
	}
	
	public void addTonsToTeamToUser()
	{
		TeamToUserBo rhtb = TeamToUserBo.getInstance();
		
		TeamToUser rht = new TeamToUser();
		for (int i = 0; i < 1000; i++)
		{
			rht.setTeamId(randomNumber() + 100);
			rht.setUserId(randomNumber() + 100);
			try {
				rhtb.create(rht);
			} catch (BoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addTonsToRefHardwareType()
	{
		RefHardwareTypeBo rhtb = RefHardwareTypeBo.getInstance();
		
		RefHardwareType rht = new RefHardwareType();
		
		
		rht.setCode("LAPTOP");
		rht.setDescription("Mobile computer");
		try {
			rhtb.create(rht);
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rht.setCode("DESKTOP");
		rht.setDescription("Heavy computer");
		try {
			rhtb.create(rht);
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rht.setCode("MONITOR");
		rht.setDescription("Window into a digital world");
		try {
			rhtb.create(rht);
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addTonsToRefCondition()
	{
		RefConditionBo rhtb = RefConditionBo.getInstance();
		
		RefCondition rht = new RefCondition();
		
		rht.setCode("GOOD");
		rht.setDescription("It's in good shape");
		try {
			rhtb.create(rht);
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rht.setCode("BAD");
		rht.setDescription("It's in rough shape");
		try {
			rhtb.create(rht);
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rht.setCode("BROKEN");
		rht.setDescription("George of the jungle got a hold of it.");
		try {
			rhtb.create(rht);
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addTonsToRefPermission()
	{
		RefPermissionBo rhtb = RefPermissionBo.getInstance();
		
		RefPermission rht = new RefPermission();
		
			
		rht.setCode("READ");
		rht.setDescription("You can read stuff");
		try {
			rhtb.create(rht);
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rht.setCode("WRITE");
		rht.setDescription("You can write stuff");
		try {
			rhtb.create(rht);
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rht.setCode("EXECUTE");
		rht.setDescription("You get the jist");
		try {
			rhtb.create(rht);
		} catch (BoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addTonsToAddress()
	{
		AddressBo rhtb = AddressBo.getInstance();
		
		Address rht = createAddress();
		for (long i = 0; i < 1000; i++)
		{
			
			rht.setKey(i);
			try {
				rhtb.create(rht);
			} catch (BoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addTonsToLocations()
	{
		LocationBo rhtb = LocationBo.getInstance();
		
		Location rht = new Location();
		for (long i = 1; i < 1000; i++)
		{
			rht.setAddressId((int) (Math.random() * 100) + 1);
			rht.setDescription("DES" + i);
			rht.setIsActive(Math.random() < .5 ? true : false);
			rht.setKey(i);
			try {
				rhtb.create(rht);
			} catch (BoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addTonsToTeam()
	{
		TeamBo rhtb = TeamBo.getInstance();
		
		Team rht = new Team();
		for (int i = 0; i < 1000; i++)
		{
			rht = createTeam();
			
			try {
				rhtb.create(rht);
			} catch (BoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void addTonsToSoftware()
	{
		SoftwareBo rhtb = SoftwareBo.getInstance();
		
		Software rht = new Software();
		for (int i = 0; i < 1000; i++)
		{
			rht = createSoftware();
			
			try {
				rhtb.create(rht);
			} catch (BoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	   public static Team createTeam() {
	        Team Team = new Team();

	      //  Team.setPk( randomNumber() );
	        Team.setName( randomString( "name", 30 ) );
	        Team.setIsActive( true  );
	        Team.setLeaderId( randomNumber() );

	        return Team;
	    }
	public void addTonsToHardware()
	{
		HardwareBo rhtb = HardwareBo.getInstance();
		
		Hardware rht = new Hardware();
		for (int i = 0; i < 1000; i++)
		{
			rht = createHardware();
			
			try {
				rhtb.create(rht);
			} catch (BoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addTonsToUser()
	{
		UserBo rhtb = UserBo.getInstance();
		
		User rht = new User();
		for (int i = 0; i < 1000; i++)
		{
			rht = createUser();
			
			try {
				rhtb.create(rht);
			} catch (BoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
    public static Hardware createHardware() {
        Hardware hardware = new Hardware();

        //hardware.setKey( randomNumber() );
        hardware.setType( Math.random() < .3 ? "LAPTOP" : Math.random() < .5 ? "DESKTOP" : "MONITOR");
        hardware.setDescription( randomString( "description", 20 ) );
        hardware.setCost( (double) randomNumber() );
        hardware.setSerialNo( randomString( "serialNo", 40 ) );
        hardware.setCondition( Math.random() < .3 ? "BROKEN" : Math.random() < .5 ? "GOOD" : "BAD");
        //hardware.setCurrentValue( (double) randomNumber() );
        hardware.setLocationId( randomNumber() + 10);
        hardware.setUserId( randomNumber() );

        return hardware;
    }
    
    public static Software createSoftware()
    {
    	 Software software = new Software();

         //software.setKey( randomNumber() );
    	 software.setName(randomString("name",20));
         software.setDescription( randomString( "description", 20 ) );
         software.setCost( (double) randomNumber() );
         software.setSerialNo( randomString( "serialNo", 10 ) );
         //software.setCurrentValue( (double) randomNumber() );

         software.setLicenseKey(randomString( "licenseKey", 10 ));
         software.setExpirationDate(new Date(randomNumber() * 100000));
         software.setPurchasedDate(new Date(randomNumber() * 100000));
         return software;
    }
    
    public static User createUser() {
        User user = new User();

       // user.setKey( randomNumber() );
        user.setUsername( randomString( "username", 40 ) );
        user.setPassword( randomString( "password", 200 ) );
        user.setFirstName( randomString( "firstName", 40 ) );
        user.setLastName( randomString( "lastName", 40 ) );
        user.setEmail( randomString( "email", 40 ) );
        user.setCellPhone( randomString( "cellPhone", 40 ) );
        user.setWorkPhone( randomString( "workPhone", 40 ) );
        user.setIsActive( true  );
        user.setRoleId(1);

        return user;
    }

    public static Address createAddress()
    {
    	Address add = new Address();
    	add.setCity(randomString("City", 10));
    	add.setState(randomString("State", 10));
    	add.setStreet1(randomString("Street1", 10));
    	add.setStreet2(randomString("Street2", 10));
    	add.setZipcode(randomString("City", 10));
    	return add;
    }
    
    public static int randomNumber() {

        return (int) ( Math.random() * 100 ) + 0;

    }

    static StringBuffer sb = new StringBuffer();
    static Random random = new Random();
    private static String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    public static String randomString( String fldName, int length ) {

        if ( fldName.length() >= length ) {
            return fldName.substring( 0, length );
        }

        sb.setLength( 0 );
        sb.append( fldName );
        for ( int i = fldName.length(); i < length; i++ ) {
            sb.append( chars.charAt( random.nextInt( chars.length() ) ) );
        }
        return sb.toString();
    }

    public static byte[] randomByteArray( int length ) {

        byte[] byteArray = new byte[length];
        random.nextBytes( byteArray );
        return byteArray;
    }
}
