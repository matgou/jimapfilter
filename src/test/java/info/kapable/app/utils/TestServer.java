package info.kapable.app.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import info.kapable.app.Protocole.ProtocoleInterface;

public class TestServer implements ProtocoleInterface {
	
	private String action;
	
	public TestServer()
	{
		super();
		this.action = "";
	}

	public void move(Message message, String destination) throws MessagingException {
		this.action = "Message " + message.getSubject() + " is move to " + destination;
	}

	public void copy(Message message, String destination) throws MessagingException {
		this.action = "Message" + message.getSubject() + " is copy to " + destination;
	}

	public void remove(Message message) throws MessagingException {
		this.action = "Message" + message.getSubject() + " is deleted";
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Message getSampleMessage(String from, String to, String subject, String text) throws AddressException, MessagingException {

	    // Get the default Session object.
	    Properties properties = System.getProperties();
	    Session session = Session.getDefaultInstance(properties);

	    // Create a default MimeMessage object.
	    MimeMessage message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(from));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Set Subject: header field
        message.setSubject(subject);

        // Now set the actual message
        message.setText(text);
		return message;
	}

}
