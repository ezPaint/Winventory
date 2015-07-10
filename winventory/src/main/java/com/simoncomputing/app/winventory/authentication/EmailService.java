package com.simoncomputing.app.winventory.authentication;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.simoncomputing.app.winventory.bo.SmtpBo;
import com.simoncomputing.app.winventory.domain.Smtp;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * HOW TO SEND AN EMAIL CODE EXAMPLE:
 * 
 * NOTE!!! You need to make sure that you have a valid SMTP 
 * server. You can set the settings for this on the web site 
 * and it will reflect in your DB.
 * 
 *   	EmailService emailer = new EmailService();
 *   	try {
 *   		emailer.setSmtp();
 *			emailer.addTo(user.getEmail());
 *			emailer.setFrom(this.getUserInfo(request).getEmail());
 *			emailer.setSubject("You're Subject");
 *			emailer.setMessage("You're Message");
 *			emailer.sendEmail();
 *		} catch (Exception e) {
 *			logger.info("Email Failed to Send to: " + user.getEmail());
 *		}
 */


/**
 * A Utility Class to ease the proccess of sending emails in the code. Uses the SMTP settings
 * to send an email with the passed in sender, recipient, subject, and message.
 * 
 * This class uses Apache's Commons Mail API to send Email.
 * 
 * @author nicholas.phillpott
 *
 */
public class EmailService {
	
	private Smtp smtp; 
	private String from;
	private String subject;
	private String message;
	private String to; 
	private Email email;
	
	/**
	 * Make a new EmailService
	 */
	public EmailService() {
		email = new SimpleEmail(); 
	}
	
	/**
	 * Add a recipient to the Email. Can be Called multiple times on one EmailService. 
	 * @param to Recipient Address
	 * @return this
	 * @throws EmailException
	 */
	public EmailService addTo(String to) throws EmailException {
		email.addTo(to);
		this.to += " " + to; 
		return this;
	}
	
	/**
	 * Sets the SMTP this Email Service will use to that which is currently stored 
	 * in the DB.
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

	/**
	 * Send the email with the desired information. 
	 * 
	 * @throws EmailException
	 */
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
	 * You should use the addTo() method but because this is a setter
	 * I'm keeping it in for now. 
	 * @param to
	 * @return
	 */
	@Deprecated
	public EmailService setTo(String to) {
		this.to = to;
		return this;
	}
}
