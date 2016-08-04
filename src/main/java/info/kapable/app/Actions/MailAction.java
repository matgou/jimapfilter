package info.kapable.app.Actions;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.jsieve.mail.Action;

import info.kapable.app.Protocole.ProtocoleInterface;

public interface MailAction {
	/**
	 * Execute an Action on given message on given server
	 * @param anAction
	 * @param message
	 * @param server
	 * @throws MessagingException
	 */
	void execute(Action anAction, Message message, ProtocoleInterface server) throws MessagingException;
}
