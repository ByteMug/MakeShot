package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.MatteBorder;

public class Bottom extends JToolBar {
	public static JTextField color;
	public final static JComboBox<String> fontChooser = new JComboBox<String>();
	public final static JComboBox<String> sizeBox = new JComboBox<String>();
	public static JTextField textField;
	ActionListener colorAction = new ActionListener(new ImageIcon(
			Bottom.class.getResource("/editor/tools/palette.png")), "palette");
	ActionListener eraseAction = new ActionListener(new ImageIcon(
			Bottom.class.getResource("/editor/tools/box/eraser.png")), "eraser");
	ActionListener paletteAction = new ActionListener(new ImageIcon(
			Bottom.class.getResource("/editor/tools/pipette.png")), "pipette");
	ActionListener text = new ActionListener(null, "textField");
	public final static JCheckBox chckbxFillRectoval = new JCheckBox(
			"Fill rect/oval");

	public Bottom() {
		GraphicsEnvironment e = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Font[] fonts = e.getAllFonts(); // Get the fonts
		for (Font f : fonts) {
			fontChooser.addItem(f.getFontName());
		}
		setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		setSize(830, 25);
		setLayout(null);
		setMinimumSize(new Dimension(830, 25));
		setPreferredSize(new Dimension(830, 25));

		JLabel lblNewLabel = new JLabel("Size:");
		lblNewLabel.setBounds(10, 5, 29, 14);
		add(lblNewLabel);

		sizeBox.setModel(new DefaultComboBoxModel<String>(new String[] { "8",
				"9", "10", "11", "12", "14", "16", "18", "20", "22", "24",
				"26", "28", "36", "48", "72" }));
		sizeBox.setBounds(37, 2, 41, 20);
		sizeBox.setFocusable(false);
		add(sizeBox);
		JButton colorBtn = new JButton();
		colorBtn.setToolTipText("Color palette");
		colorBtn.setAction(colorAction);
		colorBtn.setBounds(112, 1, 22, 22);
		colorBtn.setFocusable(false);
		add(colorBtn);

		JButton pickerBtn = new JButton("");
		pickerBtn.setToolTipText("Color picker");
		pickerBtn.setIcon(new ImageIcon(Bottom.class
				.getResource("/editor/tools/pipette.png")));
		pickerBtn.setFocusPainted(false);
		pickerBtn.setAction(paletteAction);
		pickerBtn.setBounds(135, 1, 22, 22);
		add(pickerBtn);
		color = new JTextField();
		color.setBorder(null);
		color.setEnabled(false);
		color.setEditable(false);
		color.setBackground(Color.RED);
		color.setBounds(160, 2, 29, 20);
		add(color);
		color.setColumns(10);

		JLabel lblFont = new JLabel("Font:");
		lblFont.setBounds(199, 5, 29, 14);
		add(lblFont);

		fontChooser.setBounds(228, 2, 94, 20);
		fontChooser.setFocusable(false);
		add(fontChooser);

		JLabel lblText = new JLabel("Text:");
		lblText.setBounds(332, 5, 29, 14);
		add(lblText);

		textField = new JTextField();
		textField.setBounds(359, 2, 110, 20);
		add(textField);
		textField.setColumns(10);

		JButton eraserBtn = new JButton();
		eraserBtn.setToolTipText("Eraser");
		eraserBtn.setFocusable(true);
		eraserBtn.setBounds(89, 1, 22, 22);
		eraserBtn.setAction(eraseAction);
		add(eraserBtn);

		chckbxFillRectoval.setBounds(475, 1, 97, 23);
		add(chckbxFillRectoval);

	}
}
