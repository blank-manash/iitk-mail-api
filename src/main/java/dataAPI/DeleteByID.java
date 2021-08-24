package dataAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class DeleteByID extends Deleter {
	private final static HashSet<Address> H = new HashSet<>();
	public DeleteByID() {
		final File f = new File(System.getProperty("user.dir") + "/ID.log");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			br.lines().forEach(s -> {
				try {
					H.add(new InternetAddress(s));
				} catch (AddressException e) {
					e.printStackTrace();
				}
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
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
