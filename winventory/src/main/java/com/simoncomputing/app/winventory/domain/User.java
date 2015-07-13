package com.simoncomputing.app.winventory.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.authentication.EmailService;
import com.simoncomputing.app.winventory.authentication.PasswordHasher;
import com.simoncomputing.app.winventory.bo.AccessTokenBo;
import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.formbean.UserInfoBean;
import com.simoncomputing.app.winventory.util.BoException;


/**
* The User Table.
*/
public class User {

    private Long      key;
    private String    username;
    private String    password;             //not necessarily the actual password, but some encrypted version
    private String    firstName;
    private String    lastName;
    private String    email;
    private String    cellPhone;
    private String    workPhone;
    private Boolean   isActive;
    private Integer   roleId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public String    getUsername() { return username; }
    public void      setUsername( String value ) { username = value; }
    public String    getPassword() { return password; }
    public void      setPassword( String value ) { password = value; }
    public String    getFirstName() { return firstName; }
    public void      setFirstName( String value ) { firstName = value; }
    public String    getLastName() { return lastName; }
    public void      setLastName( String value ) { lastName = value; }
    public String    getEmail() { return email; }
    public void      setEmail( String value ) { email = value; }
    public String    getCellPhone() { return cellPhone; }
    public void      setCellPhone( String value ) { cellPhone = value; }
    public String    getWorkPhone() { return workPhone; }
    public void      setWorkPhone( String value ) { workPhone = value; }
    public Boolean   getIsActive() { return isActive; }
    public void      setIsActive( boolean value ) { isActive = value ? true : false; }
    public Integer   getRoleId() { return roleId; }
    public void      setRoleId( Integer value ) { roleId = value; }
    // PROTECTED CODE -->
    
    private static Logger logger = Logger.getLogger(User.class);
    
    /**
     * Binds a post request containing the adduser form
     * to the user, so the user will be ready for adding/updating to the db.
     * @param request
     * @return errors an arraylist of error messages
     */
    public ArrayList<String> bind(HttpServletRequest request) {
        // FORM VALIDATION
        
        // ArrayList of errors to display for form validation problems
        ArrayList<String> errors = new ArrayList<String>();
        
        // ensure all required parameters exist
        if (request.getParameter("username") == null) {
            errors.add("Username field is required.");
        }
        if (request.getParameter("email") == null) {
            errors.add("Valid email is required.");
        } else {
            // if email exists, ensure email is valid
            try {
                InternetAddress emailAddr = new InternetAddress(request.getParameter("email").toString());
                emailAddr.validate();
            } catch (AddressException e) {
                errors.add("Valid email is required.");
            }
        }
        
        // ensure username is valid form
        String numbers = "0123456789";
        if (request.getParameter("username") != null) {
            if (numbers.contains(request.getParameter("username").substring(0, 1))) {
                errors.add("Username cannot start with a number.");
            }
        }
        
        // end form validation, return if errors exist
        if (errors.size() != 0) {
            return errors;
        }
        
        // if no errors, set the values
        
        // set username
        this.setUsername(request.getParameter("username"));
        
        // set email
        this.setEmail(request.getParameter("email"));
        
        // set the password
        if (request.getParameter("isGoogle") != null) {
            this.setPassword("googleUser");
        } 
        else {
            this.setPassword("UnsetPassword");
        }
        
        // Set first and last name, phone numbers, and is active
        this.setFirstName(request.getParameter("firstName"));
        this.setLastName(request.getParameter("lastName"));
        this.setCellPhone(request.getParameter("cellPhone"));
        this.setWorkPhone(request.getParameter("workPhone"));
        this.setIsActive(request.getParameter("isActive") != null);
        
        // Set role, given role title:
        // Start by getting all roles
        ArrayList<Role> roles = new ArrayList<Role>();
        try {
            roles = (ArrayList<Role>) RoleBo.getInstance().getAll();
        } catch (BoException e) {
            logger.error("BoException in UserInsertController when trying to get roles");
        }
        
        // find the role id for the specified role title, and set user's role id to that
        String roleTitle = request.getParameter("roleTitle");
        for (Role r : roles) {
            if (r.getTitle().equals(roleTitle)) {
                this.setRoleId( r.getKey().intValue() );
            }
        }
        
        // Done, return the empty errors ArrayList
        return errors;
    }
    
    /**
     * Binds a post request containing the edituser form
     * to the user, so the user will be ready for adding/updating to the db.
     * This differs from bind() because the edit form does not contain
     * user key, username, isActive, or email. Those cannot be edited through the edit-user form.
     * @param request
     * @return errors an arraylist of error messages
     */
    public ArrayList<String> bindEditForm(HttpServletRequest request) {
        // FORM VALIDATION
        
        // ArrayList of errors to display for form validation problems
        ArrayList<String> errors = new ArrayList<String>();
        
        // if no errors, set the values
        
        // Set first and last name, phone numbers, and is active
        this.setFirstName(request.getParameter("firstName"));
        this.setLastName(request.getParameter("lastName"));
        this.setCellPhone(request.getParameter("cellPhone"));
        this.setWorkPhone(request.getParameter("workPhone"));
        
        // Set role, given role title:
        // Start by getting all roles
        ArrayList<Role> roles = new ArrayList<Role>();
        try {
            roles = (ArrayList<Role>) RoleBo.getInstance().getAll();
        } catch (BoException e) {
            logger.error("BoException in UserInsertController when trying to get roles");
        }
        
        // find the role id for the specified role title, and set user's role id to that
        String roleTitle = request.getParameter("roleTitle");
        for (Role r : roles) {
            if (r.getTitle().equals(roleTitle)) {
                this.setRoleId( r.getKey().intValue() );
            }
        }
        
        // Done, return the empty errors ArrayList
        return errors;
    }
    
    /**
     * Saves the user to the db, and ensures unique username and email.
     * Also ensures username does not start with a number (barcode problems).
     * if empty arraylist is returned, the user was saved. else, not saved.
     * @return errors array list of error messages when saving.
     */
    public ArrayList<String> create() {
        ArrayList<String> errors = new ArrayList<String>();
        UserBo bo = UserBo.getInstance();
        try {
            // make sure username is unique
            if (bo.getUserByUsername(this.username) != null) {
                errors.add("Username " + this.username + " is taken.");
                logger.info("Rejected user creation because username was not unique: " + this.username);
            }
            // make sure email is unique
            if (bo.getUserByEmail(this.email) != null) {
                errors.add("Email " + this.email + " already belongs to an account.");
                logger.info("Rejected user creation because email was not unique: " + this.email);
            }
            if (errors.size() != 0) {
                return errors;
            }
            // add to the db
            bo.create(this);
        } catch (BoException e) {
            errors.add("User could not be added.");
            logger.error("BoException when inserting user in UserInsertController");
        } 
        return errors;
    }
    
    
    /**
     * Sends invite to this user
     * @param request
     * @throws Exception if no user is logged in (email must be initiated by valid user)
     */
    public void sendEmailInvite(HttpServletRequest request) throws Exception {
        
        // Set password for google user
        if (this.getPassword().equals("googleUser")) {
            logger.info("Sending login-with-google email invite to: " + this.getEmail());
            this.sendInviteGoogle(request);
        }
        
        // Send email to non-Google users to change their password
        else {
            logger.info("Sending login-without-google email invite to: " + this.getEmail());
            this.sendInviteRegular(request);
        }
    }
    
    
    
    /**
     * Sends an invite to the winventory for non-google-signin users
     * @param request
     * @throws Exception if there is no user logged in
     */
    private void sendInviteRegular(HttpServletRequest request) throws Exception {
        
        // the currently logged in user, who is sending the email
        UserInfoBean userInfo = (UserInfoBean) request.getSession().getAttribute("userInfo");
        if (userInfo == null) {
            throw new Exception("An attempt was made to invite a new non-google user,"
                    + " with no user logged in on session!");
        }
        
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString().replaceAll("-", "");
        
        //should be changed to https
        String urlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String message = "You have been added to the Winventory! \n"
                + "Your Username is: " + this.getUsername() + "\n"
                + "Please reset your password at: "
                + urlPath + "/changepassword" + "?token=" + token + "&user=" + this.getKey().intValue();
          
        //insert into database hashed version of token
        AccessTokenBo accessTokenBo = AccessTokenBo.getInstance();
        AccessToken accessToken = new AccessToken();
        
        //TODO: change access token domain object to match type
        accessToken.setUserKey(this.getKey().intValue());
        accessToken.setToken(PasswordHasher.encodePassword(token));
        
        //set in how many minutes the token will expire
        int minutesToExpire = 30;
        Date date = new Date(System.currentTimeMillis()+minutesToExpire*60*1000);
        accessToken.setExpiration(date);
        
        try {
            //check if an access token for the user currently exists
            AccessToken readToken = accessTokenBo.read(this.getKey().intValue());
            if (readToken != null) {
                // User already has a token in the AccessToken table
                // delete the old entry in preparation for the new entry
                accessTokenBo.delete(readToken.getUserKey());
            }
            
        } catch (BoException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        // Insert the new access token and email the user a link
        // with the token embedded in the url
        try {
            accessTokenBo.create(accessToken);
            this.sendPasswordResetEmail(this.getEmail(), message);
            
        } catch (BoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    
    
    
    /**
     * Sends a google invite to this user
     * @param request
     * @param user
     * @throws Exception if there is no logged in user on the request session
     */
    private void sendInviteGoogle(HttpServletRequest request) throws Exception {
        
        // the emailer
        EmailService emailer = new EmailService();
        
        // the currently logged in user, who is sending the email
        UserInfoBean userInfo = (UserInfoBean) request.getSession().getAttribute("userInfo");
        if (userInfo == null) {
            throw new Exception("An attempt was made to invite a new google user, "
                    + "with no user logged in on session!");
        }
        
        // send the email
        try {
            emailer.setSmtp();
            emailer.addTo(this.getEmail());
            emailer.setFrom(userInfo.getEmail());
            emailer.setSubject("Welcome To Winventory");
            emailer.setMessage(
                    "You have been added to the Winventory!\n"
                    + "Your Username is: " + this.getUsername());
            emailer.sendEmail();
        } catch (Exception e) {
            logger.info("Email Failed to Send to: " + this.getEmail());
        }
        
    }
    
    
    /**
     * Sends the email for allowing a password reset
     * @param toEmailAddress email address to which to send the email
     * @param message the message the email should contain
     */
    private void sendPasswordResetEmail(String toEmailAddress, String message) {
        EmailService sendResetEmail = new EmailService();
        try {
            sendResetEmail.setSmtp();
            /* setFrom does not actually matter since it uses the email 
             * stored in the smtp table
             */
            sendResetEmail.setFrom("-@gmail.com");
            //currently send here for testing purposes
            sendResetEmail.addTo(toEmailAddress);
            sendResetEmail.setSubject("Your Winventory account has been created");
            sendResetEmail.setMessage(message);
            sendResetEmail.sendEmail();
        } catch (EmailException | BoException e) {
            logger.error("email or bo exception in User.sendPasswordResetEmail");
        }
    }

}