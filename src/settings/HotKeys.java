package settings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class HotKeys {
	static JTextField areaBox;
	static JCheckBox chckbxEnableHotkeys = new JCheckBox("Enable hotkeys");
	static JTextField fsBox;
	public static int fsHK = Static.fullHotkey;
	public static int areaHK = Static.areaHotkey;
	public static JPanel panel;

	public void createPanel(Settings sett) {
		panel = new JPanel();
		sett.tabbedPane.addTab("Hotkeys", null, panel, null);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Capture full screen");
		lblNewLabel_1.setBounds(10, 11, 97, 14);
		panel.add(lblNewLabel_1);

		fsBox = new JTextField();
		fsBox.setText(KeyEvent.getKeyText(Static.fullHotkey));
		fsBox.setBounds(148, 8, 121, 20);
		panel.add(fsBox);
		fsBox.setColumns(10);

		areaBox = new JTextField();
		areaBox.setBounds(148, 33, 121, 20);
		panel.add(areaBox);
		areaBox.setColumns(10);
		areaBox.setText(KeyEvent.getKeyText(Static.areaHotkey));

		JLabel lblNewLabel_2 = new JLabel("Capture screen area");
		lblNewLabel_2.setBounds(10, 36, 114, 14);
		panel.add(lblNewLabel_2);

		chckbxEnableHotkeys.setBounds(6, 57, 140, 23);
		panel.add(chckbxEnableHotkeys);
		if (Static.hotkeys == 1) {
			chckbxEnableHotkeys.setSelected(true);
		} else {
			chckbxEnableHotkeys.setSelected(false);
		}
		fsBox.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				HotKeys.fsBox.setText(KeyEvent.getKeyText(e.getKeyCode()));
				HotKeys.fsHK = e.getKeyCode();
			}

			public void keyTyped(KeyEvent e) {
			}
		});
		areaBox.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				HotKeys.areaBox.setText(KeyEvent.getKeyText(e.getKeyCode()));
				HotKeys.areaHK = e.getKeyCode();
			}

			public void keyTyped(KeyEvent e) {
			}
		});
	}
}
