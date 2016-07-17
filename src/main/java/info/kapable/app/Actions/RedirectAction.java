package info.kapable.app.Actions;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.jsieve.mail.Action;

import info.kapable.app.Exception.NotImplementedException;
import info.kapable.app.Protocole.ProtocoleInterface;

public class RedirectAction implements MailAction {

	@Override
	public void execute(Action anAction, Message message, ProtocoleInterface server) throws MessagingException {
		try {
			// TODO Implements function
			throw new NotImplementedException("Action RedirectAction isn't implemented in this version of JImapFilter");
		} catch (NotImplementedException e) {
			e.printStackTrace();
		}
	}

}
