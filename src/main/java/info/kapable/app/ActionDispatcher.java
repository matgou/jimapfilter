package info.kapable.app;

import java.util.HashMap;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.jsieve.mail.Action;
import org.apache.jsieve.mail.ActionFileInto;

import org.apache.jsieve.mail.ActionKeep;
import info.kapable.app.Actions.FileIntoAction;
import info.kapable.app.Actions.KeepAction;
import info.kapable.app.Actions.MailAction;
import info.kapable.app.Protocole.ProtocoleInterface;

/**
 * Dynamically dispatches an Action depending on the type of Action received at runtime. 
 */
public class ActionDispatcher {
	/**
	 * Method execute executes the passed Action by invoking the method mapped by the
	 * receiver with a parameter of the EXACT type of Action.
	 * @param anAction not null
	 * @param aMail not null
	 * @param context not null
	 * @throws MessagingException
	 */
	public void execute(final Action anAction, final Message message, final ProtocoleInterface server) throws MessagingException
	{
		MailAction mailAction = (MailAction) getMethodMap().get(anAction.getClass());
		mailAction.execute(anAction, message, server);
	}

	private Map<Class, MailAction> getMethodMap() {
		Map<Class, MailAction> actionMap = new HashMap<Class,MailAction>();
        actionMap.put(ActionFileInto.class, new FileIntoAction());
        actionMap.put(ActionKeep.class, new KeepAction());
		return actionMap;
	}

}
