package dataAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

public class ListMails {
	private final static String host = "qasid.iitk.ac.in";
	private String user = null;
	private String password = null;
	private final Calendar C = Calendar.getInstance();
	private int dc = 0;
	
	public ListMails() { load();};
	
	private final String getCount(final Calendar dt) {
		dc++;
		return "\rDeleting Mail " + dt.get(Calendar.MONTH) +  
				" " + dt.get(Calendar.YEAR) + " .... " + dc;
	}
	private final Store connect() {
		Properties props = System.getProperties();
		props.setProperty("mail.imap.host", host);
		props.setProperty("mail.imap.port", "143");
		Session session = Session.getDefaultInstance(props, null);
		try {
			final Store s = session.getStore("imaps");
			s.connect(host, user, password);
			System.out.println("Connected Successfully\n===========\n");
			return s;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public final void load() {
		final String dir = System.getProperty("user.dir") + "/auth.properties";
		final Properties prop = new Properties();
		try {
			prop.load(new FileReader(dir));
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			int date = Integer.parseInt(prop.getProperty("date"));
			int month = Integer.parseInt(prop.getProperty("month")) - 1;
			int year  = Integer.parseInt(prop.getProperty("year"));
			C.set(year, month, date);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unused")
	private final void download(final Message msg) throws IOException, MessagingException {
		final String dir = System.getProperty("user.dir") + "/Downloads";
		final File folder = new File(dir);
		folder.mkdir();
		final Multipart m = (Multipart) msg.getContent();
		for(int i = 0; i < m.getCount(); i++) {
			final MimeBodyPart p = (MimeBodyPart) m.getBodyPart(i);
			if(Part.ATTACHMENT.equalsIgnoreCase(p.getDisposition())) {
				final String name = p.getFileName();
				p.saveFile(dir + "/" + name);
			}
		}
	}
	public final void delete(final Message[] msg) throws MessagingException {
		
		C.set(2021, Calendar.JULY, 28);
		System.out.println(C.getTime());
		
		for(int i = 0; i < msg.length; i++) {
			final Calendar dt = Calendar.getInstance();
			dt.setTime(msg[i].getReceivedDate());
			if(dt.before(C)) {
				msg[i].setFlag(Flags.Flag.DELETED, true);
				System.out.print(getCount(dt));
			}
		}
	}
	public final void list() throws MessagingException, IOException {
		Store store = connect();
		Folder f = store.getFolder("INBOX");
		f.open(Folder.READ_WRITE);
		final Message[] msg = f.getMessages();
		System.out.println("Count of Messages : " + msg.length);
		delete(msg);
		f.close();
		store.close();
		
	}
}
