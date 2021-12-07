package dataAPI;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class Connection {
	private static String user;
	private final static String host = "qasid.iitk.ac.in";
	private static String password;
	private static Store store;

	public final static void connect() {

		Properties props = System.getProperties();
		props.setProperty("mail.imap.host", host);
		props.setProperty("mail.imap.port", "143");
		Session session = Session.getDefaultInstance(props, null);
		try {
			final Store s = session.getStore("imaps");
			s.connect(host, user, password);
			System.out.println("Connected Successfully\n===========\n");
			store = s;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	
	public static void connectByCred(String usr, String pw) {
		user = usr;
		password = pw;
		connect();
	}
	private Connection() {};
	public final static void load() {
		final String dir = System.getProperty("user.dir") + "/auth.properties";
		final Properties prop = new Properties();
		try {
			prop.load(new FileReader(dir));
			user = prop.getProperty("user");
			password = prop.getProperty("password");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final static Folder getFolder(final String S) {
		try {
			return store.getFolder(S);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final void close() {
		try {
			store.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
