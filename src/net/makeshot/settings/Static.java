package net.makeshot.settings;

import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;

import java.awt.image.BufferedImage;

import makeshot.HotKeyListener;
import makeshot.ini.Reader;

import com.sun.jna.platform.win32.Advapi32Util;

public class Static {
	public final static String appDir = ClassLoader.getSystemClassLoader()
			.getResource(".").getPath();
	public static int areaHotkey, fullHotkey, startWithSystem, playSound,
			tooltip, saveSs, editSs, copyLink, hotkeys, timeout, imageType,
			upload, ftpPort;
	public static String ftpLogin, ftpPass, ftpDir, ftpServ, ftpUrl;
	public static String mobo = null;
	static Reader read = new Reader();

	public static String ssDirectory, ext, uploadTo;

	public static void update() {
		mobo = read.mobo();
		areaHotkey = read.hotkeys("area");
		fullHotkey = read.hotkeys("fullScreen");
		playSound = Integer.valueOf(read.settings("sound"));
		tooltip = Integer.valueOf(read.settings("tooltip"));
		saveSs = Integer.valueOf(read.settings("save"));
		editSs = Integer.valueOf(read.settings("edit"));
		copyLink = Integer.valueOf(read.settings("copyLink"));
		ssDirectory = read.settings("path");
		ext = read.settings("ext");
		hotkeys = Integer.valueOf(read.settings("hotkeys"));
		timeout = Integer.valueOf(read.settings("timeout"));
		uploadTo = read.settings("uploadTo");
		upload = Integer.valueOf(read.settings("upload"));

		ftpUrl = read.ftp("URL");
		ftpLogin = read.ftp("Login");
		ftpPass = read.ftp("Pass");
		ftpDir = read.ftp("Dir");
		ftpServ = read.ftp("Server");
		ftpPort = Integer.valueOf(read.ftp("Port"));
		if (!ext.equals("jpg")) {
			imageType = BufferedImage.TYPE_INT_ARGB;
		} else {
			imageType = BufferedImage.TYPE_3BYTE_BGR;
		}
		HotKeyListener.register();

		if (Advapi32Util
				.registryValueExists(HKEY_CURRENT_USER,
						"Software\\Microsoft\\Windows\\CurrentVersion\\Run",
						"MakeShot")) {
			startWithSystem = 1;
		} else {
			startWithSystem = 0;
		}
	}

}
