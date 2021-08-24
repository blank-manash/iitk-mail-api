package dataAPI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.TreeSet;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class DeleteByID extends Deleter {
	private final static HashSet<Address> H = new HashSet<>();
	private final static TreeSet<String> organize = new TreeSet<>();
	private final static String dir = System.getProperty("user.dir") + "/ID.log";
	public DeleteByID() {
		final File f = new File(dir);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			br.lines().forEach(s -> {
				try {
					organize.add(s);
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
	public final void refresh() {
		try {
			final PrintWriter wr = new PrintWriter(new BufferedWriter(new FileWriter(dir)));
			for(String x : organize) {
				wr.println(x);
			}
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public final String exists(final String id) {
		if(organize.contains(id))
			return "Mail Exists";
		else
			return "Doesn't Exist";
	}
	public final void insert(final String id) {
		if(organize.contains(id)) {
			return;
		}
		organize.add(id);
		try {
			H.add(new InternetAddress(id));
		} catch (AddressException e) {
			e.printStackTrace();
		}
	}
	public final void erase(String id) {
		if(organize.contains(id)) {
			organize.remove(id);
			try {
				H.remove(new InternetAddress(id));
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
