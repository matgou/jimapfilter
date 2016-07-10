package info.kapable.app.Actions;

import javax.mail.Message;

import org.apache.jsieve.mail.Action;

import info.kapable.app.Protocole.ProtocoleInterface;

public class KeepAction implements MailAction {

	@Override
	public void execute(Action anAction, Message message, ProtocoleInterface server) {
		System.out.println("ActionKeep : Rien à faire !");
	}

}
