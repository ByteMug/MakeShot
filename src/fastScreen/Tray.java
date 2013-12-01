package fastScreen;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import printScreen.Area;
import settings.Settings;

public class Tray {
	Image image = Toolkit.getDefaultToolkit().getImage(
			ClassLoader.getSystemClassLoader().getResource(".").getPath()
					+ "/icon.jpg");
	MenuItem windowItem;
	UserWindow usr = new UserWindow();
	public static TrayIcon trayIcon;

	public static void main(String[] args) {
		new Tray().create();
	}

	private void create() {

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
			trayIcon = new TrayIcon(image, "FastScreen", popup);
			trayIcon.setImageAutoSize(true);
			// exit listener
			exitItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			// full listener
			fullShot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new printScreen.FullScreen();
				}
			});
			// area listener
			areaShot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("AREA PHOTO TAKIN'");
					new printScreen.Area().create();
					new printScreen.Area().isVisible = 1;
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
						// usr.main(null);
						// usr.frame.setVisible(false);
						System.out.println("chleb2");
						usr.frame.setVisible(true);
					}
				}
			});
			aboutItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new About();
				}
			});
			try {
				tray.add(trayIcon);
				HotKeyListener.main(null);
			} catch (AWTException e) {
				System.err.println("Can't add to tray");
			}
		} else {
			System.err.println("Tray unavailable");
		}
	}
}