package dataAPI;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
/*
 * @author : Manash
 * @param : A boolean function (msg) which returns true if the mail is eligible to be deleted.
 * This is an abstract Class which provides the functionality to delete mails based on a boolean function.
 */
public abstract class Deleter {
	private int dc = 0;
	private final String folder = "INBOX";
	private int len = 0;
	private int myFlag = Folder.READ_WRITE;

	private final String dcc(String from) {
		++dc;
		len = Integer.max(len, from.length());
		while (from.length() < len) {
			from = from + " ";
		}
		return "\rDeleted Mails " + from + " .... " + dc;
	}

	public final void delete() throws MessagingException {
		final Folder f = Connection.getFolder(folder);
		f.open(myFlag);
		report();
		final Message msg[] = f.getMessages();
		int n = msg.length;
		for (int i = 0; i < n; ++i) {
			if (f(msg[i])) {
				msg[i].setFlag(Flags.Flag.DELETED, true);
				System.out.print(dcc(msg[i].getFrom()[0].toString()));
			}
		}
		f.close();
	}

	protected abstract boolean f(Message msg);

	private final void report() {
		if (myFlag == Folder.READ_ONLY)
			System.out.println("Opening in READ Mode");
		else
			System.out.println("Opening in WRITE Mode");
	}

	public final void setFlag() {
		myFlag = Folder.READ_ONLY;
	}
}
