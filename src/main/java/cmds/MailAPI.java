package cmds;

import javax.mail.MessagingException;

import dataAPI.Connection;
import dataAPI.DeleteByDate;
import dataAPI.DeleteByID;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/*
 * Provides an interface layer to the console (using picoli)
 * 
 *
 */
@Command(name = "api",

		mixinStandardHelpOptions = true, description = "Delete Mails or Download Attachments")
public class MailAPI implements Runnable {
	@Option(required = true, names = { "-u", "--user", "--usr" }, description = "username without @iitk.ac.in")
	private String username;
	@Option(required = true, names = { "-p", "--password" }, description = "Password to access.", interactive = true)
	private String password;

	@Override
	public void run() {
		Connection.connectByCred(username, password);
	}

	@Command(name = "ID", mixinStandardHelpOptions = true)
	private void deleteByID() {
		final DeleteByID deleteByID = new DeleteByID();
		try {
			deleteByID.delete();
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Command(name = "date", mixinStandardHelpOptions = true)
	private void deletebyDate(@Option(names = { "-y", "--year" }, required = true) int year,
			@Option(names = { "-m", "--month" }, required = true) int month,
			@Option(names = { "-d", "--date" }, required = true) int date) {
		
		final DeleteByDate deleteByDate = new DeleteByDate(year, month - 1, date);
		try {
			deleteByDate.delete();
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String... args) {
		int exitCode = new CommandLine(new MailAPI()).execute(args);
		System.exit(exitCode);
	}

}
