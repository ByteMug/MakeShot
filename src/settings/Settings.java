package settings;

import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class Settings {

	public JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						// Set System L&F
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					} catch (UnsupportedLookAndFeelException e) {
						// handle exception
					} catch (ClassNotFoundException e) {
						// handle exception
					} catch (InstantiationException e) {
						// handle exception
					} catch (IllegalAccessException e) {
						// handle exception
					}
					Settings window = new Settings();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Settings() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	JTabbedPane tabbedPane;
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(null);
		tabbedPane.setBounds(0, 0, 284, 219);
		frame.getContentPane().add(tabbedPane);
		
		JPanel scrshPanel = new JPanel();
		tabbedPane.addTab("Screenshots", null, scrshPanel, null);
		scrshPanel.setLayout(null);
		
		JCheckBox chckbxSaveIn = new JCheckBox("Save screenshots in:");
		chckbxSaveIn.setBounds(6, 7, 125, 23);
		scrshPanel.add(chckbxSaveIn);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(12, 30, 158, 20);
		scrshPanel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.setBounds(180, 29, 89, 23);
		scrshPanel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("File type:");
		lblNewLabel.setBounds(10, 61, 46, 14);
		scrshPanel.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"jpg", "png", "gif"}));
		comboBox.setBounds(60, 58, 65, 20);
		scrshPanel.add(comboBox);
		
		JButton btnNewButton_1 = new JButton("Ok");
		btnNewButton_1.setBounds(87, 230, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Cancel");
		btnNewButton_2.setBounds(186, 230, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		new HotKeys().createPanel(this);
		new MainSettings().createPanel(this);
	}
}
