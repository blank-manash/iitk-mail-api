package dataAPI;

import java.io.IOException;
import java.util.Calendar;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * @author Manash
*/
public class DeleteByDate {

	private final Calendar C = Calendar.getInstance();
	private int dc = 0;

	
	public DeleteByDate(int date, int month, int year) {
		C.set(year, month, date);
	}

	public final void delete(final Message[] msg) throws MessagingException {

		System.out.println(C.getTime());

		for (Message element : msg) {
			final Calendar dt = Calendar.getInstance();
			dt.setTime(element.getReceivedDate());
			if (dt.before(C)) {
				element.setFlag(Flags.Flag.DELETED, true);
				System.out.print(getCount(dt));
			}
		}
	}

	/*
	 * @Param : The message to be download. Downloads A Mail Message.
	 */

	private final String getCount(final Calendar dt) {
		dc++;
		return "\rDeleting Mail " + dt.get(Calendar.MONTH) + " " + dt.get(Calendar.YEAR) + " .... " + dc;
	}

	public final void deleteAll() throws MessagingException, IOException {
		Folder f = Connection.getFolder("INBOX");
		f.open(Folder.READ_WRITE);
		final Message[] msg = f.getMessages();
		System.out.println("Count of Messages : " + msg.length);
		delete(msg);
		f.close();
	}
}
