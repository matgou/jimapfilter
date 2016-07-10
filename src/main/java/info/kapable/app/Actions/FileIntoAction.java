package info.kapable.app.Actions;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.jsieve.mail.Action;
import org.apache.jsieve.mail.ActionFileInto;

import info.kapable.app.Protocole.ProtocoleInterface;

public class FileIntoAction implements MailAction {

	@Override
	public void execute(Action anAction, Message message, ProtocoleInterface server) throws MessagingException {
		ActionFileInto action =  (ActionFileInto) anAction;
		System.out.println("ActionFileInto : Deplacement du message dans : " + action.getDestination());
		server.move(message, action.getDestination());
	}

}
