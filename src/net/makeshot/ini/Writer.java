package net.makeshot.ini;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import net.makeshot.logs.LogError;
import net.makeshot.settings.HotKeys;

import org.ini4j.Ini;


public class Writer {
	static File settDir = new File(System.getProperty("user.home")
			+ File.separator + ".MakeShot" + File.separator);
	File file;
	Ini ini;

	public static void firstRun() {
		File saveTo = new File(settDir.getPath() + "/UID.txt");
		if (!saveTo.exists()) {
			try {
				byte[] bytesOfMessage = System.getProperties().toString()
						.getBytes("UTF-8");
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] hash = md.digest(bytesOfMessage);
				StringBuffer hexString = new StringBuffer();
				hexString.append(Long.toString(Calendar.getInstance()
						.getTimeInMillis()));
				for (int i = 0; i < hash.length; i++) {
					if ((0xFF & hash[i]) < 16) {
						hexString.append("0"
								+ Integer.toHexString(0xFF & hash[i]));
					} else {
						hexString.append(Integer.toHexString(0xFF & hash[i]));
					}
				}
				PrintWriter writer = new PrintWriter(saveTo, "UTF-8");
				writer.println(hexString);
				writer.close();
			} catch (IOException | NoSuchAlgorithmException e) {
				LogError.get(e);
			}
		}
	}

	HotKeys settings = new HotKeys();

	public void dropbox(String tag, String value) {
		try {
			this.file = new File(settDir.getPath() + "/dropbox.ini");
			if (!this.file.exists()) {
				this.file.createNewFile();
			}
			this.ini = new Ini(this.file);
			this.ini.put("Dropbox", tag, value);
			this.ini.store(this.file);
		} catch (IOException e) {
			LogError.get(e);
		}
	}

	public void ftpserv(String tag, String value) {
		try {
			this.file = new File(settDir.getPath() + "/ftp.ini");
			if (!this.file.exists()) {
				this.file.createNewFile();
			}
			this.ini = new Ini(this.file);
			this.ini.put("FTP server", tag, value);
			this.ini.store(this.file);
		} catch (IOException e) {
			LogError.get(e);
		}
	}

	public void hotkeys() {
		try {
			this.file = new File(settDir.getPath() + "/hotkeys.ini");
			this.ini = new Ini(this.file);
			if ((HotKeys.fsHK > 0) && (HotKeys.areaHK > 0)) {
				this.ini.put("Hotkeys", "fullScreen",
						Integer.toString(HotKeys.fsHK));
				this.ini.put("Hotkeys", "area",
						Integer.toString(HotKeys.areaHK));
				this.ini.store(this.file);
			}
		} catch (IOException e) {
			LogError.get(e);
		}
	}

	public void settings(String tag, String value) {
		try {
			this.file = new File(settDir.getPath() + "/settings.ini");
			this.ini = new Ini(this.file);
			this.ini.put("Settings", tag, value);
			this.ini.store(this.file);
		} catch (IOException e) {
			LogError.get(e);
		}
	}
}
