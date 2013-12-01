package upload;

import java.awt.TrayIcon;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import editor.Paint;
import makeshot.Tray;
import settings.Static;

public class Start implements Runnable {
	String pathu, type;

	public Start(String path, String type) {
		pathu = path;
		this.type = type;
	}

	@Override
	public void run() {
		if (Static.upload == 1) {
			Tray.trayIcon.displayMessage("Upload in progress",
					"Screenshot is being uploaded. Please wait.",
					TrayIcon.MessageType.INFO);

			try {
				PrintWriter writer2 = new PrintWriter(pathu + ".link", "UTF-8");
				writer2.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (Static.uploadTo.equals("Cloud")) {
				new Send().doSend(pathu, type);
			} else if (Static.uploadTo.equals("FTP server")) {
				new toFTP(pathu, type);
			} else if (Static.uploadTo.equals("Imgur")) {
				new Imgur(pathu, type);
			} else if (Static.uploadTo.equals("Imm.io")) {
				new Immio(pathu, type);
			} else if (Static.uploadTo.equals("Uploads.im")) {
				new Uploadsim(pathu, type);
			}
		} else {
			Paint.drawPanel.save();
		}
	}
}
