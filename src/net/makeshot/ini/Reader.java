package net.makeshot.ini;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import net.makeshot.logs.LogError;
import net.makeshot.settings.Fix;

import org.ini4j.Ini;
import org.ini4j.Profile;

public class Reader {
	private File a = new File(System.getProperty("user.home") + File.separator
			+ ".MakeShot" + File.separator);
	private String dropboxSettings = "";
	private File file;
	private String ftpSettings = "";
	private int hkCode;
	private Ini ini;
	private String settBox;

	public String dropbox(String tag) {
		this.file = new File(this.a + "/dropbox.ini");
		if (this.file.exists()) {
			try {
				this.ini = new Ini(this.file);
				Profile.Section section = this.ini.get("Dropbox");
				this.dropboxSettings = (section.get(tag));
			} catch (IOException e) {
				LogError.get(e);
			}
		} else {
			this.dropboxSettings = "";
		}
		return this.dropboxSettings;
	}

	public String ftp(String tag) {
		this.file = new File(this.a + "/ftp.ini");
		if (this.file.exists()) {
			try {
				this.ini = new Ini(this.file);
				Profile.Section section = this.ini.get("FTP server");
				this.ftpSettings = (section.get(tag));
			} catch (IOException e) {
				LogError.get(e);
			}
		} else {
			this.ftpSettings = "";
		}
		return this.ftpSettings;
	}

	public int hotkeys(String tag) {
		this.file = new File(this.a + "/hotkeys.ini");
		try {
			this.ini = new Ini(this.file);
			Profile.Section section = this.ini.get("Hotkeys");
			this.hkCode = Integer.parseInt(section.get(tag));
		} catch (IOException e) {
			new Fix().repair("settings.ini");
			new Fix().repair("hotkeys.ini");
			LogError.get(e);
		}
		return this.hkCode;
	}

	public String mobo() {
		String line = "";
		try {
			FileReader dupa = new FileReader(this.a + "/UID.txt");
			BufferedReader br = new BufferedReader(dupa);
			if ((line = br.readLine()) != null) {
				return line;
			}
			dupa.close();
		} catch (Exception e) {
			LogError.get(e);
		}
		return line;
	}

	public String settings(String tag) {
		this.file = new File(this.a + "/settings.ini");
		try {
			this.ini = new Ini(this.file);
			Profile.Section section = this.ini.get("Settings");
			this.settBox = (section.get(tag));
		} catch (IOException e) {
			LogError.get(e);
			new Fix();
		}
		return this.settBox;
	}
}
