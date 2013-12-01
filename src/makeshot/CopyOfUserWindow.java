package makeshot;

import ini.Reader;

import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import logs.LogError;

import org.jnativehook.GlobalScreen;

import settings.Settings;
import editor.Paint;

public class CopyOfUserWindow {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				CopyOfUserWindow window = new CopyOfUserWindow();
				window.frame.setVisible(true);
			}
		});
	}

	public JFrame frame;
	private final LinksList list = new LinksList();
	private final JPopupMenu popup = new JPopupMenu();
	private final JMenuItem popupText = new JMenuItem();
	private final Update updateCheck = new Update();

	/**
	 * Create the application.
	 */
	public CopyOfUserWindow() {
		initialize();
	}

	public void asetTooltip(String text) {
		popupText.setText(text);
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			LogError.get(e);
		}
		Update.auto = true;
		new Thread(updateCheck).start();
		frame = new JFrame("MakeShot");
		frame.setResizable(false);
		frame.setIconImage(Icon.get());
		frame.setBounds(100, 100, 240, 335);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 234, 74);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		popup.add(popupText);

		JLabel lblNewLabel = new JLabel(
				"<html>Choose or drop images to upload</html>");
		lblNewLabel.setBounds(10, 0, 115, 52);
		panel.add(lblNewLabel);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 53, 214, 14);
		panel.add(progressBar);

		frame.getContentPane().add(list.create());

		JButton copyBtn = new JButton(new ImageIcon(
				CopyOfUserWindow.class.getResource("/makeshot/copy.png")));
		copyBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				asetTooltip("Copy link");
				popup.setLocation(arg0.getLocationOnScreen());
				popup.setVisible(true);

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				popup.setVisible(false);
			}
		});

		copyBtn.setBorder(BorderFactory.createEmptyBorder());
		copyBtn.setContentAreaFilled(false);
		copyBtn.setBackground(SystemColor.control);
		copyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				list.copyLink();
			}
		});

		JButton btnNewButton = new JButton("Browse");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Choose image..");

				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Images (png, gif, jpg)", "jpg", "gif", "png");
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showDialog(frame, "Open");
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					if (new Reader().settings("edit").equals("1")) {
						new Paint(fc.getSelectedFile().getPath());
					} else {
						new upload.Start(fc.getSelectedFile().getPath(), "");
					}
				}
			}
		});
		btnNewButton.setBounds(135, 11, 89, 23);
		panel.add(btnNewButton);

		copyBtn.setBounds(20, 247, 40, 38);
		frame.getContentPane().add(copyBtn);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		frame.setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("File");
		menuBar.add(mnMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Hide");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
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
				new Thread(updateCheck).start();
			}
		});
		mnNewMenu.add(mntmCheckForUpdates);

		new FileDrop(System.out, panel, new FileDrop.Listener() {
			@Override
			public void filesDropped(java.io.File[] files) {
				for (int i = 0; i < files.length; i++) {
					try {
						new editor.SingleImage(files[i].toString());
					} // end try
					catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Invalid image",
								"File", JOptionPane.ERROR_MESSAGE);
						LogError.get(e);
					}
				} // end for: through each dropped file
			} // end filesDropped
		}); // end FileDrop.Listener

		JButton btnRm = new JButton(new ImageIcon(
				CopyOfUserWindow.class.getResource("/makeshot/rm.png")));
		btnRm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				asetTooltip("Remove link from list");
				popup.setLocation(arg0.getLocationOnScreen());
				popup.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				popup.setVisible(false);
			}
		});

		btnRm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				list.removeLink();
				popup.setVisible(false);
			}
		});
		btnRm.setBounds(184, 247, 40, 38);
		btnRm.setBorder(BorderFactory.createEmptyBorder());
		btnRm.setContentAreaFilled(false);
		frame.getContentPane().add(btnRm);

		JButton btnDel = new JButton(new ImageIcon(
				CopyOfUserWindow.class.getResource("/makeshot/del.png")));
		btnDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				asetTooltip("Delete image from the server");
				popup.setLocation(arg0.getLocationOnScreen());
				popup.setVisible(true);

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				popup.setVisible(false);
			}
		});

		btnDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				popup.setVisible(false);
				int dialogResult = JOptionPane
						.showConfirmDialog(
								null,
								"Do you want to delete this image from the server?\r\nIt cannot be undone!",
								"Deleting", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					LinksList.deleteImage();
				}
			}
		});

		btnDel.setBorder(BorderFactory.createEmptyBorder());
		btnDel.setContentAreaFilled(false);
		btnDel.setBounds(146, 247, 40, 38);
		frame.getContentPane().add(btnDel);

		final JButton dallBtn = new JButton(new ImageIcon(
				CopyOfUserWindow.class.getResource("/makeshot/dall.png")));

		dallBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				asetTooltip("Clear list");
				popup.setLocation(arg0.getLocationOnScreen());
				popup.setVisible(true);

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				popup.setVisible(false);
			}
		});

		dallBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				popup.setVisible(false);
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Do you want to completely clear the list?",
						"Deleting", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					LinksList.model.clear();
					LinksList.exportList();
				}
			}
		});

		dallBtn.setContentAreaFilled(false);
		dallBtn.setBorder(BorderFactory.createEmptyBorder());
		dallBtn.setBounds(107, 247, 40, 38);
		frame.getContentPane().add(dallBtn);
	}
}
