package net.makeshot.main;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import net.makeshot.logs.LogError;

public class About extends JFrame {
	private JPanel contentPane;

	public About() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				WindowsLF.apply();
				About.this.setType(JFrame.Type.POPUP);
				About.this.setTitle("MakeShot");
				About.this.setDefaultCloseOperation(2);
				About.this.setBounds(100, 100, 420, 270);
				About.this.setResizable(false);
				About.this.contentPane = new JPanel();
				About.this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				About.this.setContentPane(About.this.contentPane);
				About.this.setIconImage(Icon.get());
				About.this.contentPane.setLayout(null);

				JPanel panel = new JPanel();
				panel.setBounds(68, 26, 287, 55);
				About.this.contentPane.add(panel);
				panel.setLayout(null);

				JLabel lblNewLabel = new JLabel("1.1");
				lblNewLabel.setBounds(296, 81, 46, 14);
				About.this.contentPane.add(lblNewLabel);

				JScrollPane scrollPane = new JScrollPane();

				scrollPane.setHorizontalScrollBarPolicy(31);
				scrollPane.setBounds(45, 100, 330, 106);
				scrollPane.getVerticalScrollBar().setValue(
						scrollPane.getVerticalScrollBar().getMinimum());
				About.this.contentPane.add(scrollPane);

				JTextPane txtpnBytemugcom = new JTextPane();
				txtpnBytemugcom.requestFocus();
				txtpnBytemugcom.setEditable(false);
				scrollPane.setViewportView(txtpnBytemugcom);
				txtpnBytemugcom
						.setText("© 2013 ByteMug\r\nFound a bug? Do you want to suggest a feature? \r\nFeel free to write an e-mail. We'll gladly read your message!\r\n\r\ncontact@makeshot.net\r\nhelp@bytemug.com");

				JButton btnmakeshotnet = new JButton(
						"<html><a href=\"http://makeshot.net\">MakeShot.net</a>");
				btnmakeshotnet.setCursor(Cursor.getPredefinedCursor(12));
				btnmakeshotnet.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (Desktop.isDesktopSupported()) {
							try {
								Desktop.getDesktop().browse(
										URI.create("http://makeshot.net"));
							} catch (IOException localIOException) {
							}
						}
					}
				});
				btnmakeshotnet.setHorizontalAlignment(2);
				btnmakeshotnet.setBounds(305, 207, 99, 23);
				btnmakeshotnet.setOpaque(false);
				btnmakeshotnet.setContentAreaFilled(false);
				btnmakeshotnet.setBorderPainted(false);
				About.this.contentPane.add(btnmakeshotnet);
				About.this.setVisible(true);

			}
		});
	}
}
