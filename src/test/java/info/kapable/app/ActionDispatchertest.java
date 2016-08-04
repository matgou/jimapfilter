/**
 * 
 */
package info.kapable.app;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import org.apache.jsieve.mail.ActionFileInto;
import org.apache.jsieve.mail.ActionKeep;

import info.kapable.app.utils.TestServer;
import junit.framework.TestCase;

/**
 * @author matgou
 *
 */
public class ActionDispatchertest extends TestCase {

	private ActionDispatcher actionDispatcher;
	private TestServer server;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.server = new TestServer();
		this.actionDispatcher = new ActionDispatcher();
	}

	/**
	 * Test method for {@link info.kapable.app.ActionDispatcher#execute(org.apache.jsieve.mail.Action, javax.mail.Message, info.kapable.app.Protocole.ProtocoleInterface)}.
	 */
	public void testExecute() {
		ActionKeep keepAction = new ActionKeep();
		ActionFileInto moveAction = new ActionFileInto("test");
		Message message;
		try {
			message = this.server.getSampleMessage("john.doo@test.com", "dest@test.com", "Samplesubject", "SampleText");
			this.actionDispatcher.execute(keepAction, message, this.server);
			assertEquals("Une ActionKeep a été executé. ActionKeep ne doit executer aucune action.","",this.server.getAction());
			
			this.actionDispatcher.execute(moveAction, message, server);
			assertEquals("Une ActionFileInto a été executé. ActionFileInto doit placer le message dans le repertoire", 
					"Message Samplesubject is move to test", this.server.getAction());
		} catch (AddressException e) {
			e.printStackTrace();
			fail("AddressException");
		} catch (MessagingException e) {
			e.printStackTrace();
			fail("MessagingException");
		}
	}

}
