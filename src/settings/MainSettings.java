package settings;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class MainSettings {
	public static void createPanel(Settings sett) {
		JPanel prefsPanel = new JPanel();
		sett.tabbedPane.addTab("Program", null, prefsPanel, null);
		prefsPanel.setLayout(null);

		JCheckBox chckbxStartWithSystem = new JCheckBox("Start with system");
		chckbxStartWithSystem.setBounds(6, 7, 147, 23);
		prefsPanel.add(chckbxStartWithSystem);

		JCheckBox chckbxSound = new JCheckBox("Sound notification");
		chckbxSound.setBounds(6, 33, 123, 23);
		prefsPanel.add(chckbxSound);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Tooltip notification");
		chckbxNewCheckBox.setBounds(6, 59, 123, 23);
		prefsPanel.add(chckbxNewCheckBox);

		JButton btnNewButton_3 = new JButton("Uninstall");
		btnNewButton_3
				.setToolTipText("FastScreen will be uninstalled from this computer");
		btnNewButton_3.setMnemonic('a');
		btnNewButton_3.setBounds(180, 157, 89, 23);
		prefsPanel.add(btnNewButton_3);
	}

}
