package run;

import java.io.IOException;

import javax.mail.MessagingException;

import dataAPI.Connection;
import dataAPI.DeleteByID;

public class Launcher {
	private final static DeleteByID D = new DeleteByID();
	private final static void del() {
		try {
			D.delete();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		if("-ex".equals(args[0])) {
			System.out.println(D.exists(args[1]));
		} else if(args[0].equals("--ref")) {
			D.refresh();
		} else if(args[0].equals("--run")) {
			Connection.connect();
			if(args[1].equals("-r")) {
				D.setFlag();
			}
			del();
			Connection.close();
		} else if(args[0].equals("-i")) {
			for(int i = 1; i < args.length; i++) {
				D.insert(args[i]);
			}
			D.refresh();
		} else if(args[0].equals("-e")) {
			for(int i = 1; i < args.length; i++) {
				D.erase(args[i]);
			}
			D.refresh();
			
		}

		System.out.println("\nThanks!");
	}

}
