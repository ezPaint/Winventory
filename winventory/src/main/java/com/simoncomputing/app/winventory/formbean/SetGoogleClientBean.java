package com.simoncomputing.app.winventory.formbean;

import javax.servlet.http.HttpServletRequest;

import com.simoncomputing.app.winventory.bo.GoogleClientBo;
import com.simoncomputing.app.winventory.domain.GoogleClient;
import com.simoncomputing.app.winventory.util.BoException;

public class SetGoogleClientBean {

    private String    clientId;
    private String    clientSecret;
    
    public SetGoogleClientBean() {}
    
    public SetGoogleClientBean(HttpServletRequest request) {
    	this.setClientId(request.getParameter("id"));
    	this.setClientSecret(request.getParameter("secret"));
	}
    
    public void updateSmtp() throws BoException {
		GoogleClient gc = new GoogleClient(); 
		gc.setKey(1l);
		gc.setClientId(this.getClientId());
		gc.setClientSecret(this.getClientSecret());
		GoogleClientBo.getInstance().update(gc);
    }

	public void bindSmtp(GoogleClient gc) {
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
