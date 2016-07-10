package info.kapable.app.Protocole;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface ProtocoleInterface {

	/**
	 * Move message from current folder to another
	 * @param message
	 * @param destination
	 * @throws MessagingException
	 */
	void move(Message message, String destination) throws MessagingException;
	
	/**
	 * Copy message from current folder to another
	 * @param message
	 * @param destination
	 * @throws MessagingException
	 */
	void copy(Message message, String destination) throws MessagingException;
	
	/**
	 * Delete message from current folder
	 * @param message
	 * @throws MessagingException
	 */
	void remove(Message message) throws MessagingException;

}
