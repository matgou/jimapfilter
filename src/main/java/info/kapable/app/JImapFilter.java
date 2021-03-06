package info.kapable.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.search.FlagTerm;

import org.apache.jsieve.ConfigurationManager;
import org.apache.jsieve.SieveConfigurationException;
import org.apache.jsieve.SieveFactory;
import org.apache.jsieve.exception.SieveException;
import org.apache.jsieve.mail.MailAdapter;
import org.apache.jsieve.parser.generated.Node;
import org.apache.jsieve.parser.generated.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import info.kapable.app.Protocole.Imap;

/**
 * Main class
 *
 */
public class JImapFilter {
    /* Logger */
    private static final Logger logger = LogManager.getLogger("JImapFilter");

	public static void main(String[] args) {
                logger.info("JImapFilter : version 0.0.1 - Starting");

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

			Node node = null;
			try {
				node = factory.parse(script);
			} catch (ParseException e) {
				e.printStackTrace();
				server.disconnect();
				System.exit(255);
			}
			// Message messages[] = server.getCurFolder().getMessages();
            Message messages[] = server.getCurFolder().search(new FlagTerm(new Flags(Flags.Flag.DELETED),false)); 
			for (int j = 0; j < messages.length; j++) {
				try {
					logger.info( j + "/" + messages.length + " : Subjet du message a traiter : " + messages[j].getSubject() + " \n");
					mail = new SieveMailAdapter(server, messages[j]);
					factory.evaluate(mail, node);
				} catch (SieveException e) {
					// Exception non bloquante
					e.printStackTrace();
				}

			}
			server.disconnect();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			server.disconnect();
			System.exit(1);
		} catch (MessagingException e) {
			e.printStackTrace();
			server.disconnect();
			System.exit(2);
		} catch (SieveConfigurationException e) {
			e.printStackTrace();
			server.disconnect();
			System.exit(255);
		}
	}
}
