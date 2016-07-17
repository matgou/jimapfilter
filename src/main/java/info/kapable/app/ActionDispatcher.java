/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
package info.kapable.app;

import java.util.HashMap;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.jsieve.mail.Action;
import org.apache.jsieve.mail.ActionFileInto;
import org.apache.jsieve.mail.ActionKeep;
import org.apache.jsieve.mail.ActionRedirect;

import info.kapable.app.Actions.FileIntoAction;
import info.kapable.app.Actions.KeepAction;
import info.kapable.app.Actions.MailAction;
import info.kapable.app.Actions.RedirectAction;
import info.kapable.app.Actions.RejectAction;
import info.kapable.app.Protocole.ProtocoleInterface;

/**
 * This class dynamically dispatches an Action depending on the type of Action
 * received at runtime.
 * 
 * @author Mathieu GOULIN
 * @see Action
 * @since 1.0
 */
public class ActionDispatcher {

	/**
	 * Method execute executes the passed Action by invoking the method mapped
	 * by the receiver with a parameter of the EXACT type of Action.
	 * 
	 * @param anAction
	 *            not null instance of action to execute contains parameters
	 * @param aMail
	 *            not null instance of mail concerned by action
	 * @param server
	 *            not null the instance of connection to server
	 * @throws MessagingException
	 */
	public void execute(final Action anAction, final Message message, final ProtocoleInterface server)
			throws MessagingException {
		MailAction mailAction = (MailAction) getMethodMap().get(anAction.getClass());
		mailAction.execute(anAction, message, server);
	}

	/**
	 * Method to get Map between Jsieve action and JImapFilter Action
	 * 
	 * @return Map<Class, MailAction>
	 */
	private Map<Class, MailAction> getMethodMap() {
		Map<Class, MailAction> actionMap = new HashMap<Class, MailAction>();
		actionMap.put(ActionFileInto.class, new FileIntoAction());
		actionMap.put(ActionKeep.class, new KeepAction());
		actionMap.put(ActionRedirect.class, new RedirectAction());
		actionMap.put(RejectAction.class, new RejectAction());
		return actionMap;
	}

}
