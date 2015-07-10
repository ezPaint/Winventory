package com.simoncomputing.app.winventory.formbean;

import javax.servlet.http.HttpServletRequest;

import com.simoncomputing.app.winventory.bo.GoogleClientBo;
import com.simoncomputing.app.winventory.domain.GoogleClient;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * A bean to to help track and interact with the forms for the SMTP setting. 

 * @author nicholas.phillpott
 *
 */
public class SetGoogleClientBean {

    private String    clientId;
    private String    clientSecret;
    
    /**
     * Constructor
     */
    public SetGoogleClientBean() {}
    
    /**
     * Take the HTTP requests parameters and binds them to their associated
     * field in the bean.
     * @param request the request coming back from the JSP form.
     */
    public SetGoogleClientBean(HttpServletRequest request) {
    	this.setClientId(request.getParameter("id"));
    	this.setClientSecret(request.getParameter("secret"));
	}
    
    /**
     * Update the bean to what is in the related DB table at key "1L"
     * @throws BoException
     */
    public void updateSmtp() throws BoException {
		GoogleClient gc = new GoogleClient(); 
		gc.setKey(1l);
		gc.setClientId(this.getClientId());
		gc.setClientSecret(this.getClientSecret());
		GoogleClientBo.getInstance().update(gc);
    }

    /**
     * Update the bean to match what is in a passed in GoogleClient
     * @param gc the object the bean will match
     */
	public void bindGoogleClient(GoogleClient gc) {
    	this.setClientId(gc.getClientId());
    	this.setClientSecret(gc.getClientSecret());
    }
	
	// Getters Setters

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
    
}
