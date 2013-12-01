package settings;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class asd {
	JCheckBox chckbxStartWithSystem, chckbxSound = new JCheckBox(
			"Sound notification"), chckbxTooltip;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void createPanel(Settings sett) {
		JPanel prefsPanel = new JPanel();
		sett.tabbedPane.addTab("Program", null, prefsPanel, null);
		prefsPanel.setLayout(null);

		chckbxStartWithSystem = new JCheckBox("Start with system");
		chckbxStartWithSystem.setBounds(6, 7, 147, 23);
		prefsPanel.add(chckbxStartWithSystem);

		chckbxSound.setBounds(6, 33, 123, 23);
		chckbxTooltip = new JCheckBox("Tooltip notification");
		chckbxTooltip.setBounds(6, 59, 123, 23);

		JButton btnNewButton_3 = new JButton("Uninstall");
		btnNewButton_3
				.setToolTipText("FastScreen will be uninstalled from this computer");
		btnNewButton_3.setMnemonic('a');
		btnNewButton_3.setBounds(180, 157, 89, 23);
		prefsPanel.add(chckbxSound);
		prefsPanel.add(chckbxTooltip);
		prefsPanel.add(btnNewButton_3);
		
		JCheckBox chckbxEdit = new JCheckBox("Edit screenshot");
		chckbxEdit.setBounds(6, 85, 123, 23);
		prefsPanel.add(chckbxEdit);
		
		JCheckBox chckbxCopyLink = new JCheckBox("Link to the clipboard");
		chckbxCopyLink.setBounds(6, 111, 163, 23);
		prefsPanel.add(chckbxCopyLink);
		if (new ini.Reader().settings("tooltip").equals("1")) {
			chckbxTooltip.setSelected(true);
		} else {
			chckbxTooltip.setSelected(false);
		}
		if (new ini.Reader().settings("start").equals("1")) {
			chckbxStartWithSystem.setSelected(true);
		} else {
			chckbxStartWithSystem.setSelected(false);
		}
		if (new ini.Reader().settings("sound").equals("1")) {
			chckbxSound.setSelected(true);
		} else {
			chckbxSound.setSelected(false);
		}

	}
}
