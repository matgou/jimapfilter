package info.kapable.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

import org.apache.jsieve.ConfigurationManager;
import org.apache.jsieve.SieveConfigurationException;
import org.apache.jsieve.SieveFactory;
import org.apache.jsieve.exception.SieveException;
import org.apache.jsieve.mail.MailAdapter;
import org.apache.jsieve.parser.generated.ParseException;

import info.kapable.app.Protocole.Imap;

/**
 * Main class
 *
 */
public class JImapFilter {
	public static void main(String[] args) {
		System.out.println("JImapFilter : Hello World!");

		// Lecture de la configuration initiale
		Properties prop = new Properties();
		FileInputStream input = null;
		FileInputStream script = null;
		try {
			input = new FileInputStream("config.properties");
			script = new FileInputStream("script.txt");
			prop.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(255);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(255);
		}

		Imap server = new Imap(prop.getProperty("server"),prop.getProperty("folder", "INBOX"), prop.getProperty("username"), prop.getProperty("password"));

		ConfigurationManager cm;
		try {
			server.connect();
			// Load Sieve configuration
			cm = new ConfigurationManager();
			SieveFactory factory = cm.build();
			MailAdapter mail = null;

			Message messages[] = server.getCurFolder().getMessages();
			for (int j = 0; j < messages.length; j++) {
				try {
					System.out.println("*****************************************************************");
					System.out.println("Subjet du message a traiter : " + messages[j].getSubject() + " \n");
					mail = new SieveMailAdapter(server, messages[j]);
					factory.interpret(mail, script);
				} catch (ParseException e) {
					e.printStackTrace();
					System.exit(255);
				} catch (SieveException e) {
					// Exception non bloquante
					e.printStackTrace();
				}

			}

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (MessagingException e) {
			e.printStackTrace();
			System.exit(2);
		} catch (SieveConfigurationException e) {
			e.printStackTrace();
			System.exit(255);
		}
	}
}
