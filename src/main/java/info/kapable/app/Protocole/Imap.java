package info.kapable.app.Protocole;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;


public class Imap implements ProtocoleInterface {

	private static final int WAIT_TIMEOUT = 5000;
	private String url;
	private String username;
	private String password;
	private String folder;
	private Folder curFolder;
	private Store store;
	
	public Imap(String url, String folder, String username, String password)
	{
		this.setUrl(url);
		this.setFolder(folder);
		this.setUsername(username);
		this.setPassword(password);
	}

	/** 
	 * Init the mail store
	 * @throws MessagingException
	 */
	public void connect() throws MessagingException
	{
		if(this.store == null) {
			Properties props = System.getProperties();
			Session session = Session.getDefaultInstance(props, null);
			store = session.getStore("imaps");
			store.connect(this.getUrl(), this.getUsername(), this.password);
			curFolder = store.getFolder(folder);
			curFolder.open(Folder.READ_WRITE);
		}

	}
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Store getStore() throws MessagingException {
		this.connect();
		return this.store;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public Folder getCurFolder() {
		return curFolder;
	}

	public void setCurFolder(Folder curFolder) {
		this.curFolder = curFolder;
	}

	@Override
	public void move(Message message, String destination) throws MessagingException {
		this.copy(message, destination);
		this.remove(message);
	}

    private void waitForMoveCompletion(Folder folder) {
        synchronized (folder) {
            try {
				folder.wait(Imap.WAIT_TIMEOUT);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

	@Override
	public void copy(Message message, String destination) throws MessagingException {
		// TODO Auto-generated method stub
		Folder destFolder = this.getStore().getFolder(destination);
		destFolder.open(Folder.READ_WRITE);
		Message messagesToCopy[] = new Message[1];
		messagesToCopy[0] = message;
		this.getCurFolder().copyMessages(messagesToCopy, destFolder);
        waitForMoveCompletion(destFolder);
		
	}

	@Override
	public void remove(Message message) throws MessagingException {
		Message messagesToCopy[] = new Message[1];
		messagesToCopy[0] = message;
        this.getCurFolder().setFlags(messagesToCopy, new Flags(Flags.Flag.DELETED), true);
		
	}
}
