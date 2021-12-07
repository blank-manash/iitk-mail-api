package dataAPI;

import java.util.Calendar;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * @author Manash
 */
public class DeleteByDate extends Deleter {

	private final Calendar C;

	public DeleteByDate(int date, int month, int year) {
		C = Calendar.getInstance();
		C.set(year, month, date);
	}

	@Override
	protected boolean f(Message element) {
		final Calendar dt = Calendar.getInstance();
		try {
			dt.setTime(element.getReceivedDate());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return (dt.before(C));
	}
}
