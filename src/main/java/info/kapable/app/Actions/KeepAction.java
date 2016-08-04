package info.kapable.app.Actions;

import javax.mail.Message;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.apache.jsieve.mail.Action;

import info.kapable.app.Protocole.ProtocoleInterface;

public class KeepAction implements MailAction {
        static final Logger logger = LogManager.getLogger(KeepAction.class.getName());

	public void execute(Action anAction, Message message, ProtocoleInterface server) {
		logger.debug("ActionKeep : Rien à faire !");
	}

}
