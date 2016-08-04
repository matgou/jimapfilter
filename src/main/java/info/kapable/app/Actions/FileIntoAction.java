package info.kapable.app.Actions;

import javax.mail.Message;
import javax.mail.MessagingException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.apache.jsieve.mail.Action;
import org.apache.jsieve.mail.ActionFileInto;

import info.kapable.app.Protocole.ProtocoleInterface;

public class FileIntoAction implements MailAction {
        static final Logger logger = LogManager.getLogger(FileIntoAction.class.getName());

	public void execute(Action anAction, Message message, ProtocoleInterface server) throws MessagingException {
		ActionFileInto action =  (ActionFileInto) anAction;
		logger.debug("Deplacement du message dans : " + action.getDestination());
		server.move(message, action.getDestination());
	}

}
