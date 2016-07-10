package info.kapable.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.jsieve.SieveContext;
import org.apache.jsieve.exception.InternetAddressException;
import org.apache.jsieve.exception.SieveException;
import org.apache.jsieve.mail.Action;
import org.apache.jsieve.mail.MailAdapter;
import org.apache.jsieve.mail.MailUtils;
import org.apache.jsieve.mail.SieveMailException;
import org.apache.jsieve.parser.address.SieveAddressBuilder;

import info.kapable.app.Protocole.ProtocoleInterface;

public class SieveMailAdapter implements MailAdapter {
	/**
	 * The message to process
	 */
	protected Message message;
	private ProtocoleInterface server;

    private String contentAsLowerCaseString; 
    
	/**
	 * List of Actions to perform.
	 */
	private List<Action> fieldActions;
	private ActionDispatcher dispatcher;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public SieveMailAdapter(ProtocoleInterface server, Message message) {
		this.dispatcher = new ActionDispatcher();
		this.setMessage(message);
		this.setServer(server);
	}

	@Override
	public void addAction(Action action) {
		getActions().add(action);

	}

	/**
	 * Returns a new List of actions.
	 * 
	 * @return List
	 */
	protected List<Action> computeActions() {
		return new ArrayList<Action>();
	}

	/**
	 * Returns the List of actions.
	 * 
	 * @return List
	 */
	private List<Action> getActionsBasic() {
		return fieldActions;
	}

	@Override
	public void executeActions() throws SieveException {
		final List<Action> actions = getActions();
		for (final Action action : actions) {
			System.out.println("Executing " + action.toString());
			try
			{
				dispatcher.execute(action, this.getMessage(), this.server);
			} catch (MessagingException e) {
				throw new SieveException(e);
			}
		}
	}

	/**
	 * Set actions
	 */
	public void setActions(List<Action> actions) {
		this.fieldActions = actions;
	}

	@Override
	public List<Action> getActions() {
		List<Action> actions = null;
		if (null == (actions = getActionsBasic())) {
			updateActions();
			return getActions();
		}
		return actions;
	}

	/**
	 * Updates the actions.
	 */
	protected void updateActions() {
		setActions(computeActions());
	}

	@Override
	public String getContentType() throws SieveMailException {
		try {
			return getMessage().getContentType();
		} catch (MessagingException ex) {
			throw new SieveMailException(ex);
		}
	}

	@Override
	public List<String> getHeader(String name) throws SieveMailException {
		try {
			String[] headers = getMessage().getHeader(name);
			if (headers == null) {
				return new ArrayList<String>(0);
			} else {
				return Arrays.asList(headers);
			}
		} catch (MessagingException ex) {
			throw new SieveMailException(ex);
		}
	}

	@Override
	public List<String> getHeaderNames() throws SieveMailException {
		Set<String> headerNames = new HashSet<String>();
		try {
			Enumeration allHeaders = getMessage().getAllHeaders();
			while (allHeaders.hasMoreElements()) {
				Header h = (Header) allHeaders.nextElement();
				headerNames.add(h.getName());
			}
			return new ArrayList<String>(headerNames);
		} catch (MessagingException ex) {
			throw new SieveMailException(ex);
		}
	}

	@Override
	public List<String> getMatchingHeader(String name) throws SieveMailException {
		return MailUtils.getMatchingHeader(this, name);
	}

	@Override
	public int getSize() throws SieveMailException {
		try {
			return getMessage().getSize();
		} catch (MessagingException ex) {
			throw new SieveMailException(ex);
		}
	}

	@Override
	public boolean isInBodyText(String phraseCaseInsensitive) throws SieveMailException {
        try { 
            return contentAsText().indexOf(phraseCaseInsensitive.toLowerCase()) != -1; 
        } catch (MessagingException ex) { 
            throw new SieveMailException(ex); 
        } catch (IOException ex) { 
            throw new SieveMailException(ex); 
        } 
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
    private String contentAsText() throws IOException, MessagingException { 
        if (contentAsLowerCaseString == null) { 
            contentAsLowerCaseString = getMessage().getContent().toString().toLowerCase(); 
        }
        return contentAsLowerCaseString; 
    } 

	@Override
	public Address[] parseAddresses(String headerName) throws SieveMailException, InternetAddressException {
        return parseAddresses(headerName, getMessage()); 
	}

    /**
     * Parses the value from the given message into addresses. 
     *  
     * @param headerName 
     *            header name, to be matched case insensitively 
     * @param message 
     *            <code>Message</code>, not null 
     * @return <code>Address</code> array, not null possibly empty 
     * @throws SieveMailException 
     */ 
    public Address[] parseAddresses(final String headerName, 
            final Message message) throws SieveMailException { 
        try { 
            final SieveAddressBuilder builder = new SieveAddressBuilder(); 
 
            for (Enumeration en = message.getAllHeaders(); en.hasMoreElements();) { 
                final Header header = (Header) en.nextElement(); 
                final String name = header.getName(); 
                if (name.trim().equalsIgnoreCase(headerName)) { 
                    builder.addAddresses(header.getValue()); 
                } 
            } 
 
            final Address[] results = builder.getAddresses(); 
            return results; 
 
        } catch (MessagingException ex) { 
            throw new SieveMailException(ex); 
        } catch (org.apache.jsieve.parser.generated.address.ParseException ex) { 
            throw new SieveMailException(ex); 
        } 
    } 

	@Override
    public void setContext(SieveContext context) {}

	public ProtocoleInterface getServer() {
		return server;
	}

	public void setServer(ProtocoleInterface server) {
		this.server = server;
	} 

}
