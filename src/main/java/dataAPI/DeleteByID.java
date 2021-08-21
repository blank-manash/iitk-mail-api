package dataAPI;

import java.util.HashSet;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class DeleteByID extends Deleter {
	private final static HashSet<Address> H = new HashSet<>();
	public DeleteByID() {
		final String rep[] = {
				"no-reply@amazon.in",
				"store-news@amazon.in",
				"quincy@freecodecamp.org",
				"no-reply@leetcode.com",
				"no-reply@hackerrankmail.com",
				"mncsecy@iitk.ac.in",
				"presidentsg@iitk.ac.in",
				"pgduoh@appliedroots.com",
				"irctctourismoffers@irctc.co.in",
				"sntsecy@iitk.ac.in",
				"sportsecy@iitk.ac.in",
				"pg_anc@iitk.ac.in",
				"ug_anc@iitk.ac.in",
				"clib@iitk.ac.in",
				"dosa@iitk.ac.in",
				"order-update@amazon.in",
				"do-not-reply@amazon.in",
				"vivekananda.samiti.iitkanpur@gmail.com",
				"registrar@iitk.ac.in",
				"careers@expertrons.in",
				"marketplace-messages@amazon.in",
				"customer-reviews-messages@amazon.in",
				"shipment-tracking@amazon.in",
				"doad@iitk.ac.in",
				"dord@iitk.ac.in",
				"ccmt2021help@mnit.ac.in",
				"auto-confirm@amazon.in",
				"onlinecourses@iitk.ac.in"
		};
		for(String s : rep) {
			try {
				Address r = new InternetAddress(s);
				H.add(r);
			} catch (AddressException e) {
				e.printStackTrace();
			}
			
		}
	}
	@Override
	protected boolean f(Message msg) {
		try {
			Address c = msg.getFrom()[0];
			return H.contains(c);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}

}
