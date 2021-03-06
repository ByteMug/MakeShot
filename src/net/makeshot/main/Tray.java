package net.makeshot.main;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import net.makeshot.ini.Writer;
import net.makeshot.logs.LOG;
import net.makeshot.logs.Logging;
import net.makeshot.printScreen.FullScreen;
import net.makeshot.settings.Fix;
import net.makeshot.settings.Kit;
import net.makeshot.settings.Settings;
import net.makeshot.settings.Static;

import org.jnativehook.GlobalScreen;

public class Tray {
	public static TrayIcon trayIcon;
	private static MenuItem windowItem;
	private static MenuItem openSettings;
	private static MenuItem areaShot;
	private static MenuItem fullShot;
	private static MenuItem hotkeysOff;
	private static MenuItem aboutItem;
	private static MenuItem exitItem;
	private static MenuItem websiteItem;

	public static void main(String[] args) {
		try {
			if (new RandomAccessFile(System.getProperty("java.io.tmpdir")
					+ "makeshot.tmp", "rw").getChannel().tryLock() == null) {
				JOptionPane.showMessageDialog(null, "App is already running!");
			} else {
				Writer.firstRun();
				new Tray().create();
				new Fix();

				LinksList.importList();
			}
		} catch (IOException e) {
			LOG.error(e);
		}
	}

	Image image = Kit.get().getImage(getClass().getResource("MakeShot.png"));
	private final PopupMenu popup = new PopupMenu();
	Settings settings;
	private final SystemTray tray = SystemTray.getSystemTray();
	UserWindow usr;

	private void create() {
		Static.update();
		WindowsLF.apply();
		if (SystemTray.isSupported()) {
			windowItem = new MenuItem("Show");
			openSettings = new MenuItem("Settings");
			areaShot = new MenuItem("Take area screenshot");
			fullShot = new MenuItem("Take screenshot");
			websiteItem = new MenuItem("Capture website");
			hotkeysOff = new MenuItem("Turn hotkeys off");
			aboutItem = new MenuItem("About");
			exitItem = new MenuItem("Exit");
			this.popup.add(windowItem);
			this.popup.add(openSettings);
			this.popup.addSeparator();
			this.popup.add(areaShot);
			this.popup.add(fullShot);
			this.popup.add(websiteItem);
			this.popup.add(hotkeysOff);

			this.popup.add(aboutItem);
			this.popup.add(exitItem);
			trayIcon = new TrayIcon(Icon.get(), "MakeShot", this.popup);
			trayIcon.setImageAutoSize(true);
			trayIcon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e2) {
					if (Tray.this.usr == null) {
						Tray.this.usr = new UserWindow();
					}
					if (Tray.this.usr.frame.isVisible()) {
						Tray.this.usr.frame.toFront();
					} else if (!Tray.this.usr.frame.isVisible()) {
						Tray.this.usr.frame.setVisible(true);
					}
				}
			});
			exitItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GlobalScreen.unregisterNativeHook();
					System.exit(0);
				}
			});
			fullShot.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new FullScreen();
				}
			});
			areaShot.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					HotKeyListener.areaPrintScreen.create();
				}
			});
			openSettings.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Tray.this.settings == null) {
						Tray.this.settings = new Settings();
					}
					if (Settings.frame.isVisible()) {
						Settings.frame.toFront();
					} else if (!Settings.frame.isVisible()) {
						Settings.frame.setVisible(true);
					}
				}
			});
			windowItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Tray.this.usr == null) {
						Tray.this.usr = new UserWindow();
					}
					if (Tray.this.usr.frame.isVisible()) {
						Tray.this.usr.frame.toFront();
					} else if (!Tray.this.usr.frame.isVisible()) {
						Tray.this.usr.frame.setVisible(true);
					}
				}
			});
			aboutItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new About();
				}
			});
			hotkeysOff.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Tray.hotkeysOff.getLabel().equals("Turn hotkeys off")) {
						Static.hotkeys = 2;
						Tray.hotkeysOff.setLabel("Turn hotkeys on");
					} else if (Tray.hotkeysOff.getLabel().equals(
							"Turn hotkeys on")) {
						Static.hotkeys = 1;
						Tray.hotkeysOff.setLabel("Turn hotkeys off");
					}
				}
			});
			try {
				this.tray.add(trayIcon);
			} catch (AWTException e) {
				LOG.error(e);
			}
		} else {
			Logging.logger.info("Tray unavailable");
		}
	}
}
