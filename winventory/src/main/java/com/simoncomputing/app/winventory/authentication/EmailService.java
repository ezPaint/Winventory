package com.simoncomputing.app.winventory.authentication;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.simoncomputing.app.winventory.bo.SmtpBo;
import com.simoncomputing.app.winventory.domain.Smtp;
import com.simoncomputing.app.winventory.util.BoException;

public class EmailService {
	
	private Smtp smtp; 
	private String from;
	private String subject;
	private String message;
	private String to; 
	private Email email;
	
	public EmailService() {
		email = new SimpleEmail(); 
	}
	
	public EmailService addTo(String to) throws EmailException {
		email.addTo(to);
		this.to += " " + to; 
		return this;
	}
	
	/**
	 * Sets smtp to the DBs current smtp;
	 * @throws BoException 
	 */
	public void setSmtp() throws BoException {
		smtp = SmtpBo.getInstance().read(1L);
		if (smtp == null) {
			smtp = new Smtp();
			smtp.setKey(1L);
			smtp.setHostName("smtp.host.com");
			smtp.setPort(465);
			smtp.setSsl(true);
			smtp.setAuthUserName("username");
			smtp.setAuthPassword("password");
			SmtpBo.getInstance().create(smtp);
		}
	}

	public void sendEmail() throws EmailException {
		email.setHostName(smtp.getHostName());
		email.setSmtpPort(smtp.getPort());
		email.setAuthenticator(new DefaultAuthenticator(smtp.getAuthUserName(), smtp.getAuthPassword()));
		email.setSSLOnConnect(true);
		email.setFrom(from);
		email.setSubject(subject);
		email.setMsg(message);
		email.send();
	}
	
	// Getters and Setters
	
	public Smtp getSmtp() {
		return smtp;
	}

	public EmailService setSmtp(Smtp smtp) {
		this.smtp = smtp;
		return this;
	}

	public String getFrom() {
		return from;
	}

	public EmailService setFrom(String from) {
		this.from = from;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public EmailService setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public EmailService setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getTo() {
		return to;
	}

	/**
	 * Do not use this!!
	 * @param to
	 * @return
	 */
	@Deprecated
	public EmailService setTo(String to) {
		this.to = to;
		return this;
	}
}
