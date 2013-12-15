package net.makeshot.upload;

import java.awt.TrayIcon;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import net.makeshot.imageEditor.EditorGUI;
import net.makeshot.settings.Static;
import makeshot.Tray;

public class Start implements Runnable {
	String path, type;

	public Start(String path, String type) {
		this.path = path;
		this.type = type;
	}

	@Override
	public void run() {
		if (Static.upload == 1) {
			if (Static.tooltip == 1) {
				Tray.trayIcon.displayMessage("Upload in progress",
						"Screenshot is being uploaded. Please wait.",
						TrayIcon.MessageType.INFO);
			}

			if (Static.uploadTo.equals("Cloud")) {
				new Send().doSend(path, type);
			} else if (Static.uploadTo.equals("FTP server")) {
				new toFTP(path, type);
			} else if (Static.uploadTo.equals("Imgur")) {
				new Imgur(path, type);
			} else if (Static.uploadTo.equals("Imm.io")) {
				new Immio(path, type);
			} else if (Static.uploadTo.equals("Uploads.im")) {
				new Uploadsim(path, type);
			}
		} else {
			EditorGUI.drawPanel.save();
		}
	}
}
