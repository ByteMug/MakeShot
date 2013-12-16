package net.makeshot.main;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.makeshot.imageEditor.*;
import net.makeshot.logs.LOG;
import net.makeshot.settings.Settings;
import net.makeshot.upload.Start;
import net.makeshot.ini.Reader;

import org.jnativehook.GlobalScreen;

public class UserWindow {
	public JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				UserWindow window = new UserWindow();
				window.frame.setVisible(true);
			}
		});
	}

	private final LinksList list = new LinksList();
	private final JPopupMenu popup = new JPopupMenu();
	private final JMenuItem popupText = new JMenuItem();
	private final Update updateCheck = new Update();

	public UserWindow() {
		initialize();
	}

	public void asetTooltip(String text) {
		this.popupText.setText(text);
	}

	/*
	 * GUI creation happens below
	 */
	private void initialize() {
		WindowsLF.apply();
		new Thread(this.updateCheck).start();
		this.frame = new JFrame("MakeShot");
		this.frame.setResizable(false);
		this.frame.setIconImage(Icon.get());
		this.frame.setBounds(100, 100, 240, 335);
		this.frame.setDefaultCloseOperation(2);
		this.frame.getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		frameMenuBar();
		browsePanel(panel);
		copyLinkBtn();
		browseBtn(panel);
		rmFromListBtn();
		delFromSrvBtn();
		clearListBtn();
	}

	private void browsePanel(JPanel panel) {

		panel.setBounds(0, 0, 234, 74);
		this.frame.getContentPane().add(panel);
		panel.setLayout(null);

		this.popup.add(this.popupText);

		JLabel dropUploadLabel = new JLabel(
				"<html>Choose or drop images to upload</html>");
		dropUploadLabel.setBounds(10, 0, 115, 52);
		panel.add(dropUploadLabel);

		this.frame.getContentPane().add(this.list.create());

		new FileDrop(System.out, panel, new FileDrop.Listener() {
			@Override
			public void filesDropped(File[] files) {
				for (int i = 0; i < files.length; i++) {
					new SingleImage(files[i].toString());
				}
			}
		});
	}

	private void browseBtn(JPanel panel) {
		JButton browseForImageBtn = new JButton("Browse");
		browseForImageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Choose image..");

				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Images (png, gif, jpg)", new String[] { "jpg", "gif",
								"png" });
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showDialog(UserWindow.this.frame, "Open");
				if (returnVal == 0) {
					if (new Reader().settings("edit").equals("1")) {
						new EditorGUI(fc.getSelectedFile().getPath());
					} else {
						new Start(fc.getSelectedFile().getPath(), "");
					}
				}
			}
		});
		browseForImageBtn.setBounds(135, 11, 89, 23);
		panel.add(browseForImageBtn);
	}

	private void clearListBtn() {
		JButton clearListBtn = new JButton(new ImageIcon(
				UserWindow.class.getResource("/net/makeshot/main/dall.png")));

		clearListBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				UserWindow.this.asetTooltip("Clear list");
				UserWindow.this.popup.setLocation(arg0.getLocationOnScreen());
				UserWindow.this.popup.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				UserWindow.this.popup.setVisible(false);
			}
		});
		clearListBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserWindow.this.popup.setVisible(false);
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Do you want to completely clear the list?",
						"Deleting", 0);
				if (dialogResult == 0) {
					LinksList.model.clear();
					LinksList.exportList();
				}
			}
		});
		clearListBtn.setContentAreaFilled(false);
		clearListBtn.setBorder(BorderFactory.createEmptyBorder());
		clearListBtn.setBounds(107, 247, 40, 38);
		this.frame.getContentPane().add(clearListBtn);
	}

	private void copyLinkBtn() {
		JButton copyBtn = new JButton(new ImageIcon(
				UserWindow.class.getResource("/net/makeshot/main/copy.png")));

		copyBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				UserWindow.this.asetTooltip("Copy link");
				UserWindow.this.popup.setLocation(arg0.getLocationOnScreen());
				UserWindow.this.popup.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				UserWindow.this.popup.setVisible(false);
			}
		});
		copyBtn.setBorder(BorderFactory.createEmptyBorder());
		copyBtn.setContentAreaFilled(false);
		copyBtn.setBackground(SystemColor.control);
		copyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserWindow.this.list.copyLink();
			}
		});
		copyBtn.setBounds(20, 247, 40, 38);
		this.frame.getContentPane().add(copyBtn);
	}

	private void frameMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		this.frame.setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem hideMenuItem = new JMenuItem("Hide");
		hideMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserWindow.this.frame.dispose();
			}
		});
		fileMenu.add(hideMenuItem);

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GlobalScreen.unregisterNativeHook();
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);

		JMenu optionMenu = new JMenu("Options");
		menuBar.add(optionMenu);

		JMenuItem settingsMenuItem = new JMenuItem("Settings");
		settingsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Settings();
			}
		});
		optionMenu.add(settingsMenuItem);

		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		JMenuItem helpMenuItem = new JMenuItem("Help contents");
		helpMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop
						.getDesktop() : null;
				if (desktop != null
						&& desktop.isSupported(Desktop.Action.BROWSE)) {
					try {
						desktop.browse(URI.create("http://makeshot.net/help"));
					} catch (IOException ez) {
						ez.printStackTrace();
					}
				}
			}
		});
		helpMenu.add(helpMenuItem);
		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new About();
			}
		});
		helpMenu.add(aboutMenuItem);

		JMenuItem updateMenuItem = new JMenuItem("Check for updates");
		updateMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Update.auto = false;
				new Thread(UserWindow.this.updateCheck).start();
			}
		});
		helpMenu.add(updateMenuItem);
	}

	private void rmFromListBtn() {
		JButton removeFromListBtn = new JButton(new ImageIcon(
				UserWindow.class.getResource("/net/makeshot/main/rm.png")));
		removeFromListBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				UserWindow.this.asetTooltip("Remove link from list");
				UserWindow.this.popup.setLocation(arg0.getLocationOnScreen());
				UserWindow.this.popup.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				UserWindow.this.popup.setVisible(false);
			}
		});
		removeFromListBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserWindow.this.list.removeLink();
				UserWindow.this.popup.setVisible(false);
			}
		});
		removeFromListBtn.setBounds(184, 247, 40, 38);
		removeFromListBtn.setBorder(BorderFactory.createEmptyBorder());
		removeFromListBtn.setContentAreaFilled(false);
		this.frame.getContentPane().add(removeFromListBtn);
	}

	private void delFromSrvBtn() {
		JButton deleteFromServerBtn = new JButton(new ImageIcon(
				UserWindow.class.getResource("/net/makeshot/main/del.png")));
		deleteFromServerBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				UserWindow.this.asetTooltip("Delete image from the server");
				UserWindow.this.popup.setLocation(arg0.getLocationOnScreen());
				UserWindow.this.popup.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				UserWindow.this.popup.setVisible(false);
			}
		});
		deleteFromServerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserWindow.this.popup.setVisible(false);
				int dialogResult = JOptionPane
						.showConfirmDialog(
								null,
								"Do you want to delete this image from the server?\r\nIt cannot be undone!",
								"Deleting", 0);
				if (dialogResult == 0) {
					LinksList.deleteImage();
				}
			}
		});
		deleteFromServerBtn.setBorder(BorderFactory.createEmptyBorder());
		deleteFromServerBtn.setContentAreaFilled(false);
		deleteFromServerBtn.setBounds(146, 247, 40, 38);
		this.frame.getContentPane().add(deleteFromServerBtn);
	}
}
