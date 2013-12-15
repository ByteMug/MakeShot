package net.makeshot.settings;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Upload {
	public static int fsHK = net.makeshot.settings.Static.fullHotkey,
			areaHK = net.makeshot.settings.Static.areaHotkey;
	public static JPanel panel;
	JCheckBox chckbxUpload = new JCheckBox("Upload images");
	JComboBox<String> comboBox = new JComboBox<String>();
	final JLabel lblDbLog = new JLabel("Server:");
	final JLabel lblDbPass = new JLabel("Port:");
	final JLabel lblDirectory = new JLabel("Directory:");
	final JLabel lblLogin = new JLabel("Login:");
	final JLabel lblPass = new JLabel("Password:");
	final JLabel lblPort = new JLabel("Port:");
	final JLabel lblServ = new JLabel("Server:");
	final JLabel lblWebsiteUrl = new JLabel("URL:");
	public JPasswordField txtPass;
	public JTextField txtServ, ftpUrl, txtPort, txtLogin, txtDir;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void createPanel(Settings sett) {
		panel = new JPanel();
		sett.tabbedPane.addTab("Upload", null, panel, null);
		panel.setLayout(null);

		chckbxUpload.setBounds(6, 7, 140, 23);
		panel.add(chckbxUpload);

		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Cloud", "FTP server", "Imgur", "Imm.io", "Uploads.im" }));

		comboBox.setBounds(16, 37, 82, 20);
		panel.add(comboBox);

		lblServ.setBounds(16, 68, 46, 14);
		panel.add(lblServ);

		lblLogin.setBounds(16, 118, 46, 14);
		panel.add(lblLogin);

		lblPass.setBounds(16, 143, 52, 14);
		panel.add(lblPass);

		lblPort.setBounds(16, 93, 46, 14);
		panel.add(lblPort);

		txtServ = new JTextField();
		txtServ.setBounds(72, 65, 86, 20);
		panel.add(txtServ);
		txtServ.setColumns(10);

		txtPort = new JTextField();
		txtPort.setText("21");
		txtPort.setBounds(72, 90, 86, 20);
		panel.add(txtPort);
		txtPort.setColumns(10);

		txtLogin = new JTextField();
		txtLogin.setBounds(72, 115, 86, 20);
		panel.add(txtLogin);
		txtLogin.setColumns(10);

		txtPass = new JPasswordField();
		txtPass.setBounds(72, 140, 86, 20);
		panel.add(txtPass);
		txtPass.setColumns(10);

		lblDirectory.setBounds(16, 168, 52, 14);
		panel.add(lblDirectory);

		txtDir = new JTextField();
		txtDir.setText("/");
		txtDir.setBounds(72, 165, 86, 20);
		panel.add(txtDir);
		txtDir.setColumns(10);

		lblDbLog.setBounds(16, 68, 46, 14);
		panel.add(lblDbLog);

		lblDbPass.setBounds(16, 93, 46, 14);
		panel.add(lblDbPass);

		lblWebsiteUrl.setBounds(16, 193, 52, 14);
		panel.add(lblWebsiteUrl);

		ftpUrl = new JTextField();
		ftpUrl.setBounds(72, 190, 86, 20);
		panel.add(ftpUrl);
		ftpUrl.setColumns(10);

		ftpUrl.setText(Static.ftpUrl);
		txtLogin.setText(Static.ftpLogin);
		txtPass.setText(Static.ftpPass);
		txtServ.setText(Static.ftpServ);
		txtPort.setText(Integer.toString(Static.ftpPort));
		txtDir.setText(Static.ftpDir);

		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Object item = e.getItem();
					if (item.toString().equals("Imgur")) {
						showNone();
					} else if (item.toString().equals("Cloud")) {
						showNone();
					} else if (item.toString().equals("FTP server")) {
						showFTP();
					} else if (item.toString().equals("Imm.io")) {
						showNone();
					}
				}
			}
		});
		chckbxUpload.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					comboBox.setEnabled(true);
					uploadType();
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					comboBox.setEnabled(false);
					showNone();

				}
			}
		});
		if (Static.upload == 1) {
			chckbxUpload.setSelected(true);
		} else {
			chckbxUpload.setSelected(false);
			comboBox.setEnabled(false);
			showNone();
		}
		uploadType();
	}

	private void showFTP() {
		txtPort.setVisible(true);
		txtLogin.setVisible(true);
		txtPass.setVisible(true);
		txtDir.setVisible(true);
		txtServ.setVisible(true);
		ftpUrl.setVisible(true);

		lblDirectory.setVisible(true);
		lblLogin.setVisible(true);
		lblPass.setVisible(true);
		lblPort.setVisible(true);
		lblServ.setVisible(true);
		lblWebsiteUrl.setVisible(true);

		lblDbLog.setVisible(false);
		lblDbPass.setVisible(false);
	}

	private void showNone() {
		txtPort.setVisible(false);
		txtLogin.setVisible(false);
		txtPass.setVisible(false);
		txtDir.setVisible(false);
		txtServ.setVisible(false);
		ftpUrl.setVisible(false);

		lblDirectory.setVisible(false);
		lblLogin.setVisible(false);
		lblPass.setVisible(false);
		lblPort.setVisible(false);
		lblServ.setVisible(false);
		lblDbLog.setVisible(false);
		lblDbPass.setVisible(false);

		lblWebsiteUrl.setVisible(false);
	}

	private void uploadType() {
		if (Static.upload == 1) {
			if (Static.uploadTo.equals("Cloud")) {
				comboBox.setSelectedItem("Cloud");
				showNone();
			} else if (Static.uploadTo.equals("Imgur")) {
				comboBox.setSelectedItem("Imgur");
				showNone();
			} else if (Static.uploadTo.equals("FTP server")) {
				comboBox.setSelectedItem("FTP server");
				showFTP();
			} else if (Static.uploadTo.equals("Imm.io")) {
				comboBox.setSelectedItem("Imm.io");
				showNone();
			} else if (Static.uploadTo.equals("Uploads.im")) {
				comboBox.setSelectedItem("Uploads.im");
				showNone();
			}
		}
	}
}
