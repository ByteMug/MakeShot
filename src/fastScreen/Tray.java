package fastScreen;

import javax.swing.*;

import settings.Settings;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tray {
	static Image image = Toolkit.getDefaultToolkit().getImage(
			"C:/Users/Adrian/Desktop/MUIqNVX.jpg");
	static MenuItem windowItem;
	static UserWindow usr = new UserWindow();
	public static void main(String args[]) {
		new HotKeyListener().main(null);z
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			PopupMenu popup = new PopupMenu();
			windowItem = new MenuItem("Show");
			MenuItem openSettings = new MenuItem("Settings");
			MenuItem areaShot = new MenuItem("Take area screenshot");
			MenuItem fullShot = new MenuItem("Take screenshot");
			MenuItem aboutItem = new MenuItem("About");
			MenuItem exitItem = new MenuItem("Exit");
			popup.add(windowItem);
			popup.add(openSettings);
			popup.addSeparator();
			popup.add(areaShot);
			popup.add(fullShot);
			popup.add(aboutItem);
			popup.add(exitItem);
			TrayIcon trayIcon = new TrayIcon(image, "FastScreen", popup);
			trayIcon.setImageAutoSize(true);
			// exit listener
			exitItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			// exit listener
			aboutItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// open About window
				}
			});
			// full listener
			fullShot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// take screenshto
				}
			});
			// area listener
			areaShot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// take area ss
					System.out.println(new UserWindow().frame.isVisible());
				}
			});
			// setting open listener
			openSettings.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Settings.main(null);
				}
			});
			// window open listener
			windowItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(usr.frame.isVisible());
					if (usr.frame.isVisible()) {
						usr.frame.toFront();
					} else if (!usr.frame.isVisible()) {
						//usr.main(null);
						//usr.frame.setVisible(false);
						System.out.println("chleb2");
						usr.frame.setVisible(true);
					}
				}
			});
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println("Can't add to tray");
			}
		} else {
			System.err.println("Tray unavailable");
		}
	}
}