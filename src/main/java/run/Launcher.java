package run;

import java.io.IOException;

import javax.mail.MessagingException;

import dataAPI.Connection;
import dataAPI.DeleteByID;

public class Launcher {
	
	public static void main(String[] args) throws IOException {
		final DeleteByID D = new DeleteByID();
		try {
			D.delete();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		Connection.close();
		System.out.println("\nThanks!");
	}

}
