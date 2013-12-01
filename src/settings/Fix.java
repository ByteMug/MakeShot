package settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

import logs.LogError;

import org.ini4j.Ini;

public class Fix {
	public static void ssDir() {
		File ssDir = new File(Static.ssDirectory);
		if (!ssDir.exists()) {
			ssDir.mkdir();
		}
	}

	File a = new File(System.getProperty("user.home") + File.separator
			+ ".MakeShot" + File.separator);
	File hotkeys = new File(a.getPath() + "/hotkeys.ini");

	InputStream inStream = null;

	OutputStream outStream = null;
	File settings = new File(a.getPath() + "/settings.ini");

	public Fix() {
		if (a.exists()) {
			if (!hotkeys.exists())
				repair("hotkeys.ini");
			if (!settings.exists())
				repair("settings.ini");
		} else {
			a.mkdir();
			if (!hotkeys.exists())
				repair("hotkeys.ini");
			if (!settings.exists())
				repair("settings.ini");
		}
	}

	public void repair(String file) {
		try {

			inStream = getClass().getResourceAsStream("/settings/" + file);
			outStream = new FileOutputStream(a + "/" + file);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);

			}
			if (file.equals("settings.ini")) {
				Ini ini = new Ini(inStream);
				File[] f = File.listRoots();
				String disk = "";
				for (int i = 0; i < f.length; i++) {
					disk = f[0].toString();
					return;
				}
				ini.put("Settings", "path", disk + "/screenshots");
				ini.store(outStream);
			}
			inStream.close();
			outStream.close();
			JOptionPane
					.showMessageDialog(
							null,
							"There was problem with "
									+ file
									+ ". It should be fixed now.\r\nIf not, contact us.");
		} catch (IOException e) {
			LogError.get(e);
		}
	}
}
