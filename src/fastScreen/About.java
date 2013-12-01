package fastScreen;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Window.Type;

public class About extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public About() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Set System L&F
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					setType(Type.POPUP);
					setTitle("MakeShot");
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					setBounds(100, 100, 450, 300);
					contentPane = new JPanel();
					contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
					setContentPane(contentPane);
					contentPane.setLayout(null);

					JLabel lblIfYouNeed = new JLabel(
							"<html>If you have a small amount of time to spend, visit our website or write an e-mail. <br>\r\nEvery feedback is really appreciated.<br>\r\nIf you're asking for help or support, remember to include details about your OS and version of MakeShot you're using.\r\n<br><br><center>\r\n<a href=\"contact@makeshot.net\">contact@makeshot.net</a> | <a href=\"http://makeshot.net\">makeshot.net</a></center></html>");
					lblIfYouNeed.setBounds(10, 153, 414, 97);
					contentPane.add(lblIfYouNeed);
					setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
