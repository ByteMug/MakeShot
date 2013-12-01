package makeshot;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import logs.LogError;
import net.sf.jcarrierpigeon.Notification;
import net.sf.jcarrierpigeon.NotificationQueue;
import net.sf.jcarrierpigeon.WindowPosition;
import settings.Static;
import upload.Start;

public class Notifications {
	static URI imgLink;
	static JWindow window;

	private static JWindow ErrorFrame(String what, final String file) {
		window = new JWindow();
		window.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new Thread(new Start(file, "imgur")).start();
				window.dispose();
			}
		});

		window.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		window.setSize(250, 60);
		window.getContentPane().setLayout(null);
		JLabel lblNewLabel;
		if (what.contains("FTP")) {
			lblNewLabel = new JLabel("Oops, FTP error.");
		} else {
			lblNewLabel = new JLabel("Oops, something went wrong.");
		}
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 11, 230, 14);
		window.getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBorder(BorderFactory.createEmptyBorder());
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBackground(SystemColor.control);
		btnNewButton.setIcon(new ImageIcon(Notifications.class
				.getResource("/makeshot/error.png")));
		btnNewButton.setBounds(208, 11, 32, 32);
		window.getContentPane().add(btnNewButton);
		window.getRootPane().setBorder(new LineBorder(Color.BLACK, 1));

		return window;
	}

	public static void showNotification(boolean good, String link, String file) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			LogError.get(e);
		}
		if (good) {
			imgLink = URI.create(link);
			NotificationQueue queue = new NotificationQueue();
			Notification note1 = new Notification(SuccessFrame(),
					WindowPosition.BOTTOMRIGHT, 25, 25, Static.timeout);
			note1.setAnimationSpeed(700);
			queue.add(note1);

		} else {
			NotificationQueue queue = new NotificationQueue();
			Notification note1 = new Notification(ErrorFrame("", file),
					WindowPosition.BOTTOMRIGHT, 25, 25, Static.timeout);
			note1.setAnimationSpeed(700);
			queue.add(note1);
		}
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private static JWindow SuccessFrame() {
		window = new JWindow();
		window.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop
						.getDesktop() : null;
				if (desktop != null
						&& desktop.isSupported(Desktop.Action.BROWSE)) {
					try {
						window.dispose();
						desktop.browse(imgLink);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		window.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		window.setSize(250, 60);
		window.getContentPane().setLayout(null);
		JLabel lblNewLabel = new JLabel("Uploaded successfully!");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 11, 230, 14);
		window.getContentPane().add(lblNewLabel);

		JLabel lblClickHereTo = new JLabel("Click here to open the image");
		lblClickHereTo.setBounds(10, 25, 188, 14);
		window.getContentPane().add(lblClickHereTo);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBorder(BorderFactory.createEmptyBorder());
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBackground(SystemColor.control);
		btnNewButton.setIcon(new ImageIcon(Notifications.class
				.getResource("/makeshot/success.png")));
		btnNewButton.setBounds(208, 11, 32, 32);
		window.getContentPane().add(btnNewButton);

		window.getRootPane().setBorder(new LineBorder(Color.BLACK, 1));
		return window;
	}
}
