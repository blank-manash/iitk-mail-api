package run;

import java.io.IOException;

import javax.mail.MessagingException;

import dataAPI.ListMails;

public class Launcher {
	private final static ListMails L = new ListMails();
	public static void main(String[] args) {
		try {
			L.list();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nThanks!");
	}

}
