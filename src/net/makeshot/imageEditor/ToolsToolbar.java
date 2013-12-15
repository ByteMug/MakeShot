package net.makeshot.imageEditor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.border.MatteBorder;

public class ToolsToolbar extends JToolBar {
	/*
	 * every button has its own action
	 */
	ActionListener anticlockwiseAction = new ActionListener(
			new ImageIcon(ToolsToolbar.class
					.getResource("/net/makeshot/imageEditor/tools/box/rotate anticlockwise.png")),
			"anticlockwise");
	JButton anticlockwiseBtn = new JButton();
	ActionListener arrowAction = new ActionListener(new ImageIcon(
			ToolsToolbar.class.getResource("/net/makeshot/imageEditor/tools/box/arrow.png")),
			"arrow");
	JButton arrowBtn = new JButton();
	ActionListener blurAction = new ActionListener(new ImageIcon(
			ToolsToolbar.class.getResource("/net/makeshot/imageEditor/tools/box/blur.png")),
			"blur");
	JButton blurBtn = new JButton();
	ActionListener brushAction = new ActionListener(new ImageIcon(
			ToolsToolbar.class.getResource("/net/makeshot/imageEditor/tools/box/brush.png")),
			"brush");
	public final JButton brushBtn = new JButton();
	ActionListener circleAction = new ActionListener(new ImageIcon(
			ToolsToolbar.class.getResource("/net/makeshot/imageEditor/tools/box/circle.png")),
			"circle");
	JButton circleBtn = new JButton();
	ActionListener clockwiseAction = new ActionListener(new ImageIcon(
			ToolsToolbar.class
					.getResource("/net/makeshot/imageEditor/tools/box/rotate clockwise.png")),
			"clockwise");
	JButton clockwiseBtn = new JButton();

	ActionListener cropAction = new ActionListener(
			new ImageIcon(
					ToolsToolbar.class
							.getResource("/net/makeshot/imageEditor/tools/box/image-crop.png")),
			"crop");
	JButton cropBtn = new JButton();
	ActionListener highlightAction = new ActionListener(
			new ImageIcon(
					ToolsToolbar.class
							.getResource("/net/makeshot/imageEditor/tools/box/highlighter.png")),
			"highlighter");
	JButton highlightBtn = new JButton();
	ActionListener lineAction = new ActionListener(new ImageIcon(
			ToolsToolbar.class.getResource("/net/makeshot/imageEditor/tools/box/line.png")),
			"line");
	JButton lineBtn = new JButton();
	ActionListener emoAction = new ActionListener(new ImageIcon(
			ToolsToolbar.class.getResource("/net/makeshot/imageEditor/tools/box/aicon.png")),
			"emo");
	JButton emoBtn = new JButton();
	ActionListener rectAction = new ActionListener(new ImageIcon(
			ToolsToolbar.class.getResource("/net/makeshot/imageEditor/tools/box/rectangle.png")),
			"rectangle");

	JButton rectBrn = new JButton();
	ActionListener textAction = new ActionListener(new ImageIcon(
			ToolsToolbar.class.getResource("/net/makeshot/imageEditor/tools/box/draw-text.png")),
			"text");
	JButton textBtn = new JButton();

	public ToolsToolbar() {
		setSize(25, 410);
		setFloatable(false);
		setBorder(new MatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
		setMaximumSize(new Dimension(25, 410));
		setPreferredSize(new Dimension(25, 410));
		setLayout(null);
		// brush button:
		brushBtn.setToolTipText("Brush");
		brushBtn.setBounds(1, 10, 22, 22);
		brushBtn.setFocusPainted(false);
		add(brushBtn);
		brushBtn.setAction(brushAction);
		highlightBtn.setToolTipText("Highlighter");
		// highlighter button:
		highlightBtn.setBounds(1, 33, 22, 22);
		highlightBtn.setFocusPainted(false);
		add(highlightBtn);
		highlightBtn.setAction(highlightAction);
		textBtn.setToolTipText("Text");
		// text button:
		textBtn.setFocusPainted(false);
		textBtn.setBounds(1, 56, 22, 22);
		add(textBtn);
		textBtn.setAction(textAction);
		blurBtn.setToolTipText("Blur");
		// blur button:
		blurBtn.setBounds(1, 79, 22, 22);
		blurBtn.setFocusPainted(false);
		add(blurBtn);
		blurBtn.setAction(blurAction);
		lineBtn.setToolTipText("Line");
		// line button:
		lineBtn.setBounds(1, 112, 22, 22);
		lineBtn.setFocusPainted(false);
		add(lineBtn);
		lineBtn.setAction(lineAction);
		// arrow button:
		arrowBtn.setToolTipText("Arrow");
		arrowBtn.setBounds(1, 135, 22, 22);
		arrowBtn.setFocusPainted(false);
		add(arrowBtn);
		arrowBtn.setAction(arrowAction);
		// rectangle button:
		rectBrn.setToolTipText("Rectangle");
		rectBrn.setBounds(1, 158, 22, 22);
		rectBrn.setFocusPainted(false);
		add(rectBrn);
		rectBrn.setAction(rectAction);
		// circle button:
		circleBtn.setToolTipText("Oval");
		circleBtn.setBounds(1, 181, 22, 22);
		circleBtn.setFocusPainted(false);
		add(circleBtn);
		circleBtn.setAction(circleAction);
		// crop button:
		cropBtn.setToolTipText("Crop");
		cropBtn.setBounds(1, 237, 22, 22);
		cropBtn.setFocusPainted(false);
		add(cropBtn);
		cropBtn.setAction(cropAction);
		// rotate clockwise button:
		clockwiseBtn.setToolTipText("Rotate clockwise");
		clockwiseBtn.setBounds(1, 260, 22, 22);
		clockwiseBtn.setFocusable(false);
		add(clockwiseBtn);
		clockwiseBtn.setAction(clockwiseAction);
		// rotate anticlockwise button:
		anticlockwiseBtn.setToolTipText("Rotate anticlockwise");
		anticlockwiseBtn.setBounds(1, 283, 22, 22);
		anticlockwiseBtn.setFocusable(false);
		add(anticlockwiseBtn);
		anticlockwiseBtn.setAction(anticlockwiseAction);
		// emoticons button:
		emoBtn.setToolTipText("Emoticons");
		emoBtn.setBounds(1, 214, 22, 22);
		add(emoBtn);
		emoBtn.setAction(emoAction);
	}
}
