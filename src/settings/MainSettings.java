package settings;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class MainSettings {
	class MyDocumentFilter extends DocumentFilter {
		MyDocumentFilter() {
		}

		@Override
		public void insertString(DocumentFilter.FilterBypass fp, int offset,
				String string, AttributeSet aset) throws BadLocationException {
			int len = string.length();
			boolean isValidInteger = true;
			for (int i = 0; i < len; i++) {
				if (!Character.isDigit(string.charAt(i))) {
					isValidInteger = false;
					break;
				}
			}
			if (isValidInteger) {
				super.insertString(fp, offset, string, aset);
			} else {
				Kit.get().beep();
			}
		}

		@Override
		public void replace(DocumentFilter.FilterBypass fp, int offset,
				int length, String string, AttributeSet aset)
				throws BadLocationException {
			int len = string.length();
			boolean isValidInteger = true;
			for (int i = 0; i < len; i++) {
				if (!Character.isDigit(string.charAt(i))) {
					isValidInteger = false;
					break;
				}
			}
			if (isValidInteger) {
				super.replace(fp, offset, length, string, aset);
			} else {
				Kit.get().beep();
			}
		}
	}

	public static JSpinner timeoutInput = new JSpinner(new SpinnerNumberModel(
			3500, 250, 25000, 1));
	JCheckBox chckbxStartWithSystem;
	JCheckBox chckbxSound = new JCheckBox("Sound notification");
	JCheckBox chckbxTooltip;
	JCheckBox chckbxEdit;
	JCheckBox chckbxCopyLink;
	private JLabel lblNotificationTimeout;

	public void createPanel(Settings sett) {
		JPanel prefsPanel = new JPanel();
		sett.tabbedPane.addTab("Program", null, prefsPanel, null);
		prefsPanel.setLayout(null);
		this.chckbxStartWithSystem = new JCheckBox("Start with system");
		this.chckbxStartWithSystem.setBounds(6, 7, 147, 23);
		prefsPanel.add(this.chckbxStartWithSystem);

		this.chckbxSound.setBounds(6, 33, 123, 23);
		this.chckbxTooltip = new JCheckBox("Tooltip notification");
		this.chckbxTooltip.setBounds(6, 59, 123, 23);

		this.chckbxTooltip.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					MainSettings.timeoutInput.setEnabled(true);
				} else if (e.getStateChange() == 2) {
					MainSettings.timeoutInput.setEnabled(false);
				}
			}
		});
		prefsPanel.add(this.chckbxSound);
		prefsPanel.add(this.chckbxTooltip);

		this.chckbxEdit = new JCheckBox("Edit screenshot");
		this.chckbxEdit.setBounds(6, 85, 123, 23);
		prefsPanel.add(this.chckbxEdit);

		this.chckbxCopyLink = new JCheckBox("Link to the clipboard");
		this.chckbxCopyLink.setBounds(6, 111, 163, 23);
		prefsPanel.add(this.chckbxCopyLink);

		timeoutInput.setBounds(121, 164, 86, 20);
		prefsPanel.add(timeoutInput);
		this.lblNotificationTimeout = new JLabel("Tooltip timeout (ms)");
		this.lblNotificationTimeout.setBounds(16, 167, 113, 14);
		prefsPanel.add(this.lblNotificationTimeout);
		if (Static.tooltip == 1) {
			this.chckbxTooltip.setSelected(true);
			timeoutInput.setEnabled(true);
		} else {
			this.chckbxTooltip.setSelected(false);
			timeoutInput.setEnabled(false);
		}
		if (Static.startWithSystem == 1) {
			this.chckbxStartWithSystem.setSelected(true);
		} else {
			this.chckbxStartWithSystem.setSelected(false);
		}
		if (Static.playSound == 1) {
			this.chckbxSound.setSelected(true);
		} else {
			this.chckbxSound.setSelected(false);
		}
		if (Static.copyLink == 1) {
			this.chckbxCopyLink.setSelected(true);
		} else {
			this.chckbxCopyLink.setSelected(false);
		}
		if (Static.editSs == 1) {
			this.chckbxEdit.setSelected(true);
		} else {
			this.chckbxEdit.setSelected(false);
		}
	}
}
