package settings;

import java.awt.Desktop.Action;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class HotKeys {
static JTextField textField_1;
static JTextField textField_2;
static JTextField textField_3;
	public static void createPanel(Settings sett){
		JPanel hotkeyPanel = new JPanel();
		sett.tabbedPane.addTab("Hotkeys", null, hotkeyPanel, null);
		hotkeyPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Capture full screen");
		lblNewLabel_1.setBounds(10, 11, 97, 14);
		hotkeyPanel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(148, 8, 121, 20);
		hotkeyPanel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(148, 33, 121, 20);
		hotkeyPanel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(148, 59, 121, 20);
		hotkeyPanel.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblCaptureActiveWindow = new JLabel("Capture active window");
		lblCaptureActiveWindow.setBounds(10, 62, 121, 14);
		hotkeyPanel.add(lblCaptureActiveWindow);
		
		JLabel lblNewLabel_2 = new JLabel("Capture screen area");
		lblNewLabel_2.setBounds(10, 36, 114, 14);
		hotkeyPanel.add(lblNewLabel_2);

		//
		
		textField_1.addKeyListener(new KeyListener()
		{

			@Override
			public void keyPressed(KeyEvent e) {
				textField_1.setText(e.getKeyText(e.getKeyCode()));
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void doNothing(){
		
	}
}
