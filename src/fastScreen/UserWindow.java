package fastScreen;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

import settings.Settings;

public class UserWindow {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				UserWindow window = new UserWindow();
				window.frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			// Set System L&F
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		TitledBorder titled = new TitledBorder("Upload");

		frame = new JFrame();
		frame.setBounds(100, 100, 250, 350);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Upload", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(0, 0, 234, 74);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel(
				"<html>Choose pictures to <br>upload</html>");
		lblNewLabel.setBounds(10, 19, 115, 23);
		panel.add(lblNewLabel);

		JButton btnNewButton = new JButton("Browse");
		btnNewButton.setBounds(135, 19, 89, 23);
		panel.add(btnNewButton);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 53, 214, 14);
		panel.add(progressBar);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		frame.setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("File");
		menuBar.add(mnMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Hide");
		mnMenu.add(mntmNewMenuItem);

		JMenuItem mntmHello = new JMenuItem("Exit");
		mnMenu.add(mntmHello);

		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		JMenuItem mntmPreferences = new JMenuItem("Settings");
		mntmPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Settings().main(null);
			}
		});
		mnOptions.add(mntmPreferences);

		JMenu mnNewMenu = new JMenu("Help");
		menuBar.add(mnNewMenu);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnNewMenu.add(mntmAbout);

		JMenuItem mntmCheckForUpdates = new JMenuItem("Check for updates");
		mnNewMenu.add(mntmCheckForUpdates);
	}
}
