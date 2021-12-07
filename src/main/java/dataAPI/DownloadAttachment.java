package dataAPI;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;

public class DownloadAttachment {
	private final void download(final Message msg) throws IOException, MessagingException {
		final String dir = System.getProperty("user.dir") + "/Downloads";
		final File folder = new File(dir);
		folder.mkdir();
		final Multipart m = (Multipart) msg.getContent();
		for (int i = 0; i < m.getCount(); i++) {
			final MimeBodyPart p = (MimeBodyPart) m.getBodyPart(i);
			if (Part.ATTACHMENT.equalsIgnoreCase(p.getDisposition())) {
				final String name = p.getFileName();
				p.saveFile(dir + "/" + name);
			}
		}
	}
	public final void downloadAll(String addr) {
		Folder f = Connection.getFolder("INBOX");
		try {
			f.open(Folder.READ_ONLY);
			final Message[] msg = f.getMessages();
			Arrays.stream(msg).forEach(s -> {
				try {
					download(s);
				} catch (IOException | MessagingException e) {
					e.printStackTrace();
				}
			});
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
