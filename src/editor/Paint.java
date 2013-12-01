package editor;

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

import logs.LogError;
import makeshot.Icon;
import settings.Fix;
import editor.tools.Emotions;

public class Paint {
	static final String BRUSH = "brush";
	static final String ERASER = "eraser";
	static final String LIGHTER = "highlighter";
	static final String LINE = "line";
	static final String TEXT = "text";
	static final String PICKER = "picker";
	static final String ARROW = "arrow";
	static final String CIRCLE = "circle";
	static final String RECT = "rectangle";
	static final String CROP = "crop";
	static final String CLOCKWISE = "clockwise";
	static final String ANTICLOCKWISE = "anti";
	static final String PASTE = "paste";
	static final String BLUR = "blur";
	public static final String EMO = "EMO";
	public static String currentTool = "brush";
	public static DrawPanel drawPanel;
	static Font font;
	public static JFrame frame;
	public static String path;

	public static Font getCheckFont() {
		font = new Font(Bottom.fontChooser.getSelectedItem().toString(), 0,
				Integer.valueOf(Bottom.sizeBox.getSelectedItem().toString())
						.intValue() + 10);
		return font;
	}

	private Bottom bottomBox = new Bottom();
	private JPanel mainPanel = new JPanel();
	private final JScrollPane scrollPane = new JScrollPane();
	Toolbox toolbox = new Toolbox();
	private Upper topBox = new Upper();

	public Paint(String p) {
		try {
			try {
				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				currentTool = "brush";
			} catch (Exception e) {
				LogError.get(e);
			}
			Fix.ssDir();
			path = p;
			createGui();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "asdInvalid image", "Error", 0);
			LogError.get(e);
		}
	}

	private void createGui() {
		new Emotions();
		frame = new JFrame("Screenshot editor");
		frame.setIconImage(Icon.get());
		frame.setLocationRelativeTo(null);
		frame.setSize(830, 460);
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(830, 460));
		frame.getContentPane().setLayout(null);
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
		this.mainPanel.add(this.topBox, "North");

		this.mainPanel.add(this.bottomBox, "South");

		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (Chooser.frame.isVisible()) {
					Chooser.frame.dispose();
				}
			}
		});
		this.toolbox.brushBtn.requestFocusInWindow();
	}
}
