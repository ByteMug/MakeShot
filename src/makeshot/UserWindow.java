package makeshot;

import ini.Reader;

import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import logs.LogError;

import org.jnativehook.GlobalScreen;

import settings.Settings;
import upload.Start;
import editor.Paint;
import editor.SingleImage;

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

	private void initialize() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			LogError.get(e);
		}
		Update.auto = true;
		new Thread(this.updateCheck).start();
		this.frame = new JFrame("MakeShot");
		this.frame.setResizable(false);
		this.frame.setIconImage(Icon.get());
		this.frame.setBounds(100, 100, 240, 335);
		this.frame.setDefaultCloseOperation(2);
		this.frame.getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 234, 74);
		this.frame.getContentPane().add(panel);
		panel.setLayout(null);

		this.popup.add(this.popupText);

		JLabel lblNewLabel = new JLabel(
				"<html>Choose or drop images to upload</html>");
		lblNewLabel.setBounds(10, 0, 115, 52);
		panel.add(lblNewLabel);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 53, 214, 14);
		panel.add(progressBar);

		this.frame.getContentPane().add(this.list.create());

		JButton copyBtn = new JButton(new ImageIcon(
				UserWindow.class.getResource("/makeshot/copy.png")));
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
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.addActionListener(new ActionListener() {
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
						new Paint(fc.getSelectedFile().getPath());
					} else {
						new Start(fc.getSelectedFile().getPath(), "");
					}
				}
			}
		});
		btnNewButton.setBounds(135, 11, 89, 23);
		panel.add(btnNewButton);

		copyBtn.setBounds(20, 247, 40, 38);
		this.frame.getContentPane().add(copyBtn);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		this.frame.setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("File");
		menuBar.add(mnMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Hide");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserWindow.this.frame.dispose();
			}
		});
		mnMenu.add(mntmNewMenuItem);

		JMenuItem mntmHello = new JMenuItem("Exit");
		mntmHello.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GlobalScreen.unregisterNativeHook();
				System.exit(0);
			}
		});
		mnMenu.add(mntmHello);

		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		JMenuItem mntmPreferences = new JMenuItem("Settings");
		mntmPreferences.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Settings();
			}
		});
		mnOptions.add(mntmPreferences);

		JMenu mnNewMenu = new JMenu("Help");
		menuBar.add(mnNewMenu);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new About();
			}
		});
		mnNewMenu.add(mntmAbout);

		JMenuItem mntmCheckForUpdates = new JMenuItem("Check for updates");
		mntmCheckForUpdates.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Update.auto = false;
				new Thread(UserWindow.this.updateCheck).start();
			}
		});
		mnNewMenu.add(mntmCheckForUpdates);

		new FileDrop(System.out, panel, new FileDrop.Listener() {
			@Override
			public void filesDropped(File[] files) {
				for (int i = 0; i < files.length; i++) {
					try {
						new SingleImage(files[i].toString());
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Invalid image",
								"File", 0);
						LogError.get(e);
					}
				}
			}
		});
		JButton btnRm = new JButton(new ImageIcon(
				UserWindow.class.getResource("/makeshot/rm.png")));
		btnRm.addMouseListener(new MouseAdapter() {
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
		btnRm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserWindow.this.list.removeLink();
				UserWindow.this.popup.setVisible(false);
			}
		});
		btnRm.setBounds(184, 247, 40, 38);
		btnRm.setBorder(BorderFactory.createEmptyBorder());
		btnRm.setContentAreaFilled(false);
		this.frame.getContentPane().add(btnRm);

		JButton btnDel = new JButton(new ImageIcon(
				UserWindow.class.getResource("/makeshot/del.png")));
		btnDel.addMouseListener(new MouseAdapter() {
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
		btnDel.addActionListener(new ActionListener() {
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
		btnDel.setBorder(BorderFactory.createEmptyBorder());
		btnDel.setContentAreaFilled(false);
		btnDel.setBounds(146, 247, 40, 38);
		this.frame.getContentPane().add(btnDel);

		JButton dallBtn = new JButton(new ImageIcon(
				UserWindow.class.getResource("/makeshot/dall.png")));

		dallBtn.addMouseListener(new MouseAdapter() {
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
		dallBtn.addActionListener(new ActionListener() {
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
		dallBtn.setContentAreaFilled(false);
		dallBtn.setBorder(BorderFactory.createEmptyBorder());
		dallBtn.setBounds(107, 247, 40, 38);
		this.frame.getContentPane().add(dallBtn);
	}
}
