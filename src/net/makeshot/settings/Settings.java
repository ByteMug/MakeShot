package net.makeshot.settings;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import net.makeshot.ini.Reader;
import net.makeshot.logs.LOG;
import net.makeshot.main.Cleaner;
import net.makeshot.main.Icon;
import net.makeshot.main.WindowsLF;

public class Settings {
	public static JFrame frame;
	JCheckBox chckbxSaveIn = new JCheckBox("Save screenshots in:");
	JComboBox<String> comboBox;
	HotKeys hk = new HotKeys();
	MainSettings ms = new MainSettings();
	JTabbedPane tabbedPane;
	JTextField textField;
	Upload up = new Upload();

	public Settings() {
		initialize();

	}

	private void initialize() {
		Static.update();
		WindowsLF.apply();
		frame = new JFrame("Settings");

		frame.setIconImage(Icon.get());
		frame.setVisible(true);
		frame.setBounds(100, 100, 290, 315);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame.dispose();
				Cleaner.gc();
			}
		});
		frame.getContentPane().setLayout(null);
		this.tabbedPane = new JTabbedPane(1);
		this.tabbedPane.setBorder(null);
		this.tabbedPane.setBounds(0, 0, 284, 241);
		frame.getContentPane().add(this.tabbedPane);
		frame.setIconImage(Icon.get());
		JPanel scrshPanel = new JPanel();
		this.tabbedPane.addTab("Screenshots", null, scrshPanel, null);
		scrshPanel.setLayout(null);
		scrshPanel.add(this.chckbxSaveIn);
		this.chckbxSaveIn.setBounds(6, 7, 125, 23);

		this.textField = new JTextField();
		this.textField.setEnabled(false);
		this.textField.setBounds(12, 30, 158, 20);
		scrshPanel.add(this.textField);
		this.textField.setColumns(10);
		this.textField.setText(Static.ssDirectory);
		this.chckbxSaveIn.setSelected(Static.saveSs == 1);
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(Static.appDir));
				chooser.setFileSelectionMode(1);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(Settings.frame) == 0) {
					Settings.this.textField.setText(chooser.getSelectedFile()
							.toString());
				}
			}
		});
		btnNewButton.setBounds(180, 29, 89, 23);
		scrshPanel.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("File type:");
		lblNewLabel.setBounds(10, 61, 46, 14);
		scrshPanel.add(lblNewLabel);
		this.comboBox = new JComboBox<String>();
		this.comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
				"jpg", "png", "gif" }));
		this.comboBox.setBounds(60, 58, 65, 20);
		if (new Reader().settings("ext").equals("png")) {
			this.comboBox.setSelectedItem("png");
		} else if (new Reader().settings("ext").equals("gif")) {
			this.comboBox.setSelectedItem("gif");
		}
		scrshPanel.add(this.comboBox);

		JButton okBtn = new JButton("Ok");
		okBtn.setBounds(87, 252, 89, 23);
		frame.getContentPane().add(okBtn);

		JButton closeBtn = new JButton("Cancel");
		closeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Settings.frame.dispose();
				Cleaner.gc();
			}
		});
		okBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Settings.this.save();
				Settings.frame.setVisible(false);
				Cleaner.gc();
			}
		});
		closeBtn.setBounds(186, 252, 89, 23);
		frame.getContentPane().add(closeBtn);
		this.hk.createPanel(this);
		this.ms.createPanel(this);
		this.up.createPanel(this);
		MainSettings.timeoutInput.setValue(Integer.valueOf(Static.timeout));
	}

	private void save() {
		new Save(this.ms, this, this.up);
	}
}
