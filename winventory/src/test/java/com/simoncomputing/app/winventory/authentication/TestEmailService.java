package com.simoncomputing.app.winventory.authentication;

import static org.junit.Assert.*;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.junit.*;

import com.simoncomputing.app.winventory.authentication.EmailService;
import com.simoncomputing.app.winventory.bo.SmtpBo;
import com.simoncomputing.app.winventory.dao.SessionFactory;
import com.simoncomputing.app.winventory.domain.Smtp;
import com.simoncomputing.app.winventory.util.BoException;

public class TestEmailService {
	
	private static final Logger logger = Logger.getLogger(TestEmailService.class);
	
	private Smtp smtp;
	private EmailService emailer;
	
	@Before
	public void setup() {
        SessionFactory.initializeForTest();
		
		emailer = new EmailService();
	}
	
	@Test
	public void testSendEmail() {
		try {
			emailer.setSmtp();
		} catch (BoException e) {
			fail("Bo Exception!!!");
		}
		Smtp sample = emailer.getSmtp();
		System.out.println(sample.getHostName() + " " 
			+ sample.getAuthUserName() + " " 
			+ sample.getAuthPassword() + " "
			+ sample.getPort() + " "
			+ sample.getSsl());
		
		try {
			emailer.setFrom("fivewetmice@gmail.com").addTo("nickwp54@vt.edu").setSubject("Test 5").setMessage("Test #");
			emailer.sendEmail();
		} catch (EmailException e) {
			e.printStackTrace();
			fail("Email Failed");
		}
	}
}
