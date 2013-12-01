package fastScreen;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import settings.Settings;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;

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
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		TitledBorder titled = new TitledBorder("Upload");

		frame = new JFrame();
		frame.setBounds(100, 100, 250, 350);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				"C:/Users/Adrian/Desktop/icon.jpg"));
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

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 53, 214, 14);
		panel.add(progressBar);

		final LinksList list = new LinksList();
		frame.getContentPane().add(list.create());

		JButton btnNewButton_1 = new JButton("Copy link");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				list.copyLink();
			}
		});

		JButton btnNewButton = new JButton("Browse");
		btnNewButton.setBounds(135, 19, 89, 23);
		panel.add(btnNewButton);

		btnNewButton_1.setBounds(135, 256, 89, 23);
		frame.getContentPane().add(btnNewButton_1);

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
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnNewMenu.add(mntmAbout);

		JMenuItem mntmCheckForUpdates = new JMenuItem("Check for updates");
		mnNewMenu.add(mntmCheckForUpdates);
	}
}
