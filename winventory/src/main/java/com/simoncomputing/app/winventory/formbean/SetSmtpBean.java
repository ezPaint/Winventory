package com.simoncomputing.app.winventory.formbean;

import javax.servlet.http.HttpServletRequest;

import com.simoncomputing.app.winventory.bo.SmtpBo;
import com.simoncomputing.app.winventory.domain.Smtp;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * A bean to to help track and interact with the forms for the SMTP setting. 
 * 
 * @author nicholas.phillpott
 *
 */
public class SetSmtpBean {

    private String    hostName;
    private Integer   port;
    private String    authUserName;
    private String    authPassword;
    private Boolean   ssl;
    
    /**
     * Constructor
     */
    public SetSmtpBean() {}
    
    /**
     * Take the HTTP requests parameters and binds them to their associated
     * field in the bean.
     * @param request the request coming back from the JSP form.
     */
    public SetSmtpBean(HttpServletRequest request) {
    	this.setHostName(request.getParameter("hostname"));
    	this.setAuthUserName(request.getParameter("username"));
    	this.setAuthPassword(request.getParameter("password"));
    	this.setPort(Integer.parseInt(request.getParameter("port")));
    	if (request.getParameter("ssl") != null) {
    		ssl = true; 
    	}
    	else {
    		ssl = false;
    	}
    		
	}
    
    /**
     * Update the bean to what is in the related DB table at key "1L"
     * @throws BoException
     */
    public void updateSmtp() throws BoException {
    	Smtp smtp = new Smtp(); 
		smtp.setKey(1L);
		smtp.setHostName(this.getHostName());
		smtp.setPort(this.getPort());
		smtp.setSsl(this.getSsl());
		smtp.setAuthUserName(this.getAuthUserName());
		smtp.setAuthPassword(this.getAuthPassword());
		SmtpBo.getInstance().update(smtp);
    }

    /**
     * Update the bean to match what is in a passed in GoogleClient
     * @param gc the object the bean will match
     */
	public void bindSmtp(Smtp smtp) {
    	this.setHostName(smtp.getHostName());
    	this.setPort(smtp.getPort());
    	this.setAuthPassword(smtp.getAuthPassword());
    	this.setAuthUserName(smtp.getAuthUserName());
    	this.setSsl(smtp.getSsl());
    }
    
    // Getters and Setter
    
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getAuthUserName() {
		return authUserName;
	}
	public void setAuthUserName(String authUserName) {
		this.authUserName = authUserName;
	}
	public String getAuthPassword() {
		return authPassword;
	}
	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}
	public Boolean getSsl() {
		return ssl;
	}
	public void setSsl(Boolean ssl) {
		this.ssl = ssl;
	}
    
}
