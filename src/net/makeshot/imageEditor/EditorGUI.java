package net.makeshot.imageEditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import net.makeshot.imageEditor.additional.Emoticons;
import net.makeshot.logs.LogError;
import net.makeshot.settings.Fix;
import makeshot.Cleaner;
import makeshot.Icon;

public class EditorGUI {
	public final static String BRUSH = "brush";
	public final static String ERASER = "eraser";
	public final static String LIGHTER = "highlighter";
	public final static String LINE = "line";
	public final static String TEXT = "text";
	public final static String PICKER = "picker";
	public final static String ARROW = "arrow";
	public final static String CIRCLE = "circle";
	public final static String RECT = "rectangle";
	public final static String CROP = "crop";
	public final static String CLOCKWISE = "clockwise";
	public final static String ANTICLOCKWISE = "anti";
	public final static String PASTE = "paste";
	public final static String BLUR = "blur";
	public static final String EMO = "EMO";
	public static String currentTool = "brush";
	public static DrawPanel drawPanel;
	public static Font font;
	public static JFrame frame;
	public static String path;

	public static Font getCheckFont() {
		font = new Font(DrawSettingsToolbar.fontChooser.getSelectedItem()
				.toString(), 0, Integer.valueOf(
				DrawSettingsToolbar.sizeBox.getSelectedItem().toString())
				.intValue() + 10);
		return font;
	}

	private DrawSettingsToolbar bottomBox = new DrawSettingsToolbar();
	private JPanel mainPanel = new JPanel();
	private final JScrollPane scrollPane = new JScrollPane();
	ToolsToolbar toolbox = new ToolsToolbar();
	private ExportToolbar exportToolbar = new ExportToolbar();

	public EditorGUI(String p) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			currentTool = "brush";

			Fix.ssDir();
			path = p;
			createGui();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid image", "Error", 0);
			LogError.get(e);
		}
	}

	private void createGui() {
		new Emoticons();
		frame = new JFrame("Screenshot editor");
		frame.setIconImage(Icon.get());
		frame.setSize(830, 460);
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(830, 460));
		frame.getContentPane().setLayout(new BorderLayout());

		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAllFonts();
		String[] hej = new String[fonts.length];
		for (int i = 0; i < fonts.length; i++) {
			hej[i] = fonts[i].getName();
		}
		frame.getContentPane().add(this.toolbox, "West");
		this.scrollPane.setBounds(25, 50, 789, 371);

		frame.getContentPane().add(this.scrollPane);

		drawPanel = new DrawPanel(path);
		this.scrollPane.setViewportView(drawPanel);
		drawPanel.setBorder(null);

		this.mainPanel.setSize(814, 40);
		frame.getContentPane().add(this.mainPanel, "North");
		this.mainPanel.setLayout(new BorderLayout(0, 0));
		this.mainPanel.add(this.exportToolbar, "North");

		this.mainPanel.add(this.bottomBox, "South");

		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (Chooser.frame.isVisible()) {
					Chooser.frame.dispose();
					Cleaner.gc();
				}
			}
		});
		this.toolbox.brushBtn.requestFocusInWindow();
	}
}
