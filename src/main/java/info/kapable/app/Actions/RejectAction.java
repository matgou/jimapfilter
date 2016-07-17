package info.kapable.app.Actions;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.jsieve.mail.Action;

import info.kapable.app.Protocole.ProtocoleInterface;

public class RejectAction implements MailAction {

	@Override
	public void execute(Action anAction, Message message, ProtocoleInterface server) throws MessagingException {
		server.remove(message);
	}

}
