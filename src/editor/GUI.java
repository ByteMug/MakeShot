package editor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import settings.Kit;

public class GUI extends JFrame {
	final static String BRUSH = "brush", ERASER = "eraser",
			LIGHTER = "highlighter", LINE = "line", TEXT = "text",
			PICKER = "picker";
	public static JButton brushBtn = new JButton(""), hlBtn = new JButton(""),
			lineBtn = new JButton(""), txtBtn = new JButton(""),
			eraseBtn = new JButton(""), clearBtn = new JButton("");
	public static JButton cancelBtn = new JButton("");
	public static JButton colorBtn = new JButton("");
	public static String currentTool = "brush";
	static Font font;
	static JFrame frame;
	static JButton okBtn = new JButton("");
	public static String path;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JPanel contentPane;

	private DrawPanel panel_1;

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 830, 460);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.NORTH);

		JToolBar toolBar = new JToolBar();
		toolBar.setOrientation(SwingConstants.VERTICAL);
		contentPane.add(toolBar, BorderLayout.WEST);

		brushBtn = new JButton("");
		brushBtn.setToolTipText("Brush");
		brushBtn.setIcon(new ImageIcon(Paint.class
				.getResource("/editor/tools/brush.png")));
		brushBtn.setBounds(10, 5, 22, 22);
		toolBar.add(brushBtn);
		brushBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool = BRUSH;
				panel_1.setCursor(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			}
		});
		hlBtn = new JButton("");
		hlBtn.setToolTipText("Highlighter");
		hlBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool = LIGHTER;
				panel_1.setCursor(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			}
		});
		hlBtn.setIcon(new ImageIcon(Paint.class
				.getResource("/editor/tools/hlighter.png")));
		hlBtn.setBounds(43, 5, 22, 22);
		toolBar.add(hlBtn);

		lineBtn = new JButton("");
		lineBtn.setToolTipText("Line");
		lineBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel_1.x1 = 0;
				panel_1.y1 = 0;
				currentTool = LINE;
				panel_1.setCursor(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			}
		});
		lineBtn.setIcon(new ImageIcon(Paint.class
				.getResource("/editor/tools/line.png")));
		lineBtn.setBounds(43, 28, 22, 22);
		toolBar.add(lineBtn);

		txtBtn = new JButton("");
		txtBtn.setToolTipText("Text");
		txtBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool = TEXT;
				panel_1.setCursor(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			}
		});
		txtBtn.setIcon(new ImageIcon(Paint.class
				.getResource("/editor/tools/text.png")));
		txtBtn.setBounds(10, 28, 22, 22);
		toolBar.add(txtBtn);

		eraseBtn = new JButton("");
		eraseBtn.setToolTipText("Eraser");
		eraseBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool = ERASER;
				panel_1.setCursor(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			}
		});
		eraseBtn.setIcon(new ImageIcon(Paint.class
				.getResource("/editor/tools/eraser.png")));
		eraseBtn.setBounds(43, 51, 22, 22);
		toolBar.add(eraseBtn);

		clearBtn.setToolTipText("Restore screenshot");
		clearBtn.setIcon(new ImageIcon(Paint.class
				.getResource("/editor/tools/clear all.png")));
		clearBtn.setBounds(10, 51, 22, 22);
		toolBar.add(clearBtn);

		okBtn.setBounds(3, 375, 68, 23);
		toolBar.add(okBtn);

		cancelBtn.setBounds(3, 345, 68, 23);
		toolBar.add(cancelBtn);

		colorBtn = new JButton("");
		colorBtn.setToolTipText("Color chooser");
		colorBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!Chooser.frame.isVisible()) {
					if (Chooser.isOn) {
						Chooser.frame.setVisible(true);
					} else {
						Chooser.createAndShowGUI();
					}

				} else {
					Chooser.frame.setState(java.awt.Frame.NORMAL);
					Chooser.frame.toFront();

				}
			}
		});
		colorBtn.setIcon(new ImageIcon(Paint.class
				.getResource("/editor/tools/color.png")));
		colorBtn.setBounds(10, 236, 22, 22);
		toolBar.add(colorBtn);

		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAllFonts();
		String[] hej = new String[fonts.length];
		for (int i = 0; i < fonts.length; i++)
			hej[i] = fonts[i].getName();

		JButton pickBtn = new JButton("");
		pickBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentTool = PICKER;
				Cursor picker = Kit
						.get()
						.createCustomCursor(
								Kit.get()
										.getImage(
												Paint.class
														.getResource("/editor/tools/color_picker.png")),
								new Point(2, 15), "img");
				panel_1.setCursor(picker);

			}
		});
		pickBtn.setIcon(new ImageIcon(Paint.class
				.getResource("/editor/tools/pick.png")));
		pickBtn.setBounds(43, 236, 22, 22);
		toolBar.add(pickBtn);

	}

}
