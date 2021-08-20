import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.Test;

import dataAPI.ListMails;

public class listTester {

	@Test
	public void test() {
		ListMails l = new ListMails();
		try {
			l.list();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
