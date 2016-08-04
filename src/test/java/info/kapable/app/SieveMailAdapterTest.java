/**
 * 
 */
package info.kapable.app;

import java.util.List;

import javax.mail.Message;

import org.apache.jsieve.mail.Action;
import org.apache.jsieve.mail.ActionKeep;
import org.apache.jsieve.mail.SieveMailException;

import info.kapable.app.utils.TestServer;
import junit.framework.TestCase;

/**
 * @author matgou
 *
 */
public class SieveMailAdapterTest extends TestCase {

	private TestServer server;
	private SieveMailAdapter adapter;
	private Message message;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.server = new TestServer();
		this.message = this.server.getSampleMessage("from@exemple.com", "to@exemple.com", "Sample subject", "Sample Text");
		this.adapter = new SieveMailAdapter(server, message);
	}

	/**
	 * Test method for {@link info.kapable.app.SieveMailAdapter#addAction(org.apache.jsieve.mail.Action)}.
	 */
	public void testAddAction() {
		this.adapter.addAction(new ActionKeep());
		List<Action> actions = this.adapter.getActions();
		assertTrue(actions.get(0) instanceof ActionKeep);
	}

	/**
	 * Test method for {@link info.kapable.app.SieveMailAdapter#computeActions()}.
	 */
	public void testComputeActions() {
		//fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link info.kapable.app.SieveMailAdapter#executeActions()}.
	 */
	public void testExecuteActions() {
		//fail("Not yet implemented"); // TODO
	}


	/**
	 * Test method for {@link info.kapable.app.SieveMailAdapter#updateActions()}.
	 */
	public void testUpdateActions() {
		//fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link info.kapable.app.SieveMailAdapter#getContentType()}.
	 */
	public void testGetContentType() {
		//fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link info.kapable.app.SieveMailAdapter#getHeader(java.lang.String)}.
	 */
	public void testGetHeader() {
		List<String> to;
		try {
			to = this.adapter.getHeader("to");
			assertTrue(to.get(0).contains("to@exemple.com"));
		} catch (SieveMailException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link info.kapable.app.SieveMailAdapter#getHeaderNames()}.
	 */
	public void testGetHeaderNames() {
		List<String> s;
		try {
			s = this.adapter.getHeaderNames();
			assertTrue(s.contains("To"));
		} catch (SieveMailException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	
	}

	/**
	 * Test method for {@link info.kapable.app.SieveMailAdapter#getMatchingHeader(java.lang.String)}.
	 */
	public void testGetMatchingHeader() {
		List<String> s;
		try {
			s = this.adapter.getMatchingHeader("To");
			assertTrue(s.get(0).contains("to@exemple.com"));
		} catch (SieveMailException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link info.kapable.app.SieveMailAdapter#getSize()}.
	 */
	public void testGetSize() {
		//fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link info.kapable.app.SieveMailAdapter#isInBodyText(java.lang.String)}.
	 */
	public void testIsInBodyText() {
		//fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link info.kapable.app.SieveMailAdapter#parseAddresses(java.lang.String)}.
	 */
	public void testParseAddressesString() {
		//fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link info.kapable.app.SieveMailAdapter#parseAddresses(java.lang.String, javax.mail.Message)}.
	 */
	public void testParseAddressesStringMessage() {
		//fail("Not yet implemented"); // TODO
	}

}
