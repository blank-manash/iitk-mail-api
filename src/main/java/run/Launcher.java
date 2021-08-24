package run;

import java.io.IOException;

import javax.mail.MessagingException;

import dataAPI.Connection;
import dataAPI.DeleteByID;

public class Launcher {
	
	public static void main(String[] args) throws IOException {
		final DeleteByID D = new DeleteByID();
		try {
			if(args.length > 0 && args[0].equals("-r"))
				D.setFlag();
			D.delete();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		Connection.close();
		System.out.println("\nThanks!");
	}

}
