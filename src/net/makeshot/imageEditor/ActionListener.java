package net.makeshot.imageEditor;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import net.makeshot.imageEditor.additional.Emoticons;
import net.makeshot.imageEditor.additional.PrintImage;

class ActionListener extends AbstractAction implements MouseListener {

	String command;

	/*
	 * creates new AbstractAction and assign ImageIcon and String to it
	 * 
	 * @param imageIcon - icon shown in the GUI
	 * 
	 * @param command - name of the tool
	 */
	public ActionListener(ImageIcon imageIcon, String command) {
		super(null, imageIcon);
		this.command = command;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EditorGUI.drawPanel.setCursor(Cursor
				.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		if (command.equals("brush")) {
			EditorGUI.currentTool = EditorGUI.BRUSH;
		} else if (command.equals("highlighter")) {
			EditorGUI.currentTool = EditorGUI.LIGHTER;
		} else if (command.equals("text")) {
			EditorGUI.currentTool = EditorGUI.TEXT;
		} else if (command.equals("blur")) {
			EditorGUI.currentTool = EditorGUI.BLUR;
			EditorGUI.drawPanel.oldX = 0;
			EditorGUI.drawPanel.oldY = 0;
		} else if (command.equals("line")) {
			EditorGUI.currentTool = EditorGUI.LINE;
			EditorGUI.drawPanel.oldX = 0;
			EditorGUI.drawPanel.oldY = 0;
		} else if (command.equals("arrow")) {
			EditorGUI.currentTool = EditorGUI.ARROW;
			EditorGUI.drawPanel.oldX = 0;
			EditorGUI.drawPanel.oldY = 0;
		} else if (command.equals("rectangle")) {
			EditorGUI.currentTool = EditorGUI.RECT;
			EditorGUI.drawPanel.oldX = 0;
			EditorGUI.drawPanel.oldY = 0;
		} else if (command.equals("circle")) {
			EditorGUI.currentTool = EditorGUI.CIRCLE;
			EditorGUI.drawPanel.oldX = 0;
			EditorGUI.drawPanel.oldY = 0;
			// bottom
		} else if (command.equals("pipette")) {
			EditorGUI.currentTool = EditorGUI.PICKER;
		} else if (command.equals("palette")) {
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
		} else if (command.equals("eraser")) {
			EditorGUI.currentTool = EditorGUI.ERASER;

		} else if (command.equals("emo")) {
			Emoticons.asd.add(Emoticons.small);
			Emoticons.asd.add(Emoticons.big);

			Emoticons.small.add(Emoticons.angry32);
			Emoticons.small.add(Emoticons.blush32);
			Emoticons.small.add(Emoticons.cry32);
			Emoticons.small.add(Emoticons.eye32);
			Emoticons.small.add(Emoticons.happy32);
			Emoticons.small.add(Emoticons.macho32);
			Emoticons.small.add(Emoticons.sad32);
			Emoticons.small.add(Emoticons.smile32);
			Emoticons.small.add(Emoticons.tongue32);
			Emoticons.small.add(Emoticons.wow32);

			Emoticons.big.add(Emoticons.angry64);
			Emoticons.big.add(Emoticons.blush64);
			Emoticons.big.add(Emoticons.cry64);
			Emoticons.big.add(Emoticons.eye64);
			Emoticons.big.add(Emoticons.happy64);
			Emoticons.big.add(Emoticons.macho64);
			Emoticons.big.add(Emoticons.sad64);
			Emoticons.big.add(Emoticons.smile64);
			Emoticons.big.add(Emoticons.tongue64);
			Emoticons.big.add(Emoticons.wow64);
			Emoticons.asd.show(EditorGUI.frame, 34, 294);
		} else if (command.equals("crop")) {
			EditorGUI.currentTool = EditorGUI.CROP;
			EditorGUI.drawPanel.setCursor(Cursor
					.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			EditorGUI.drawPanel.oldX = 0;
			EditorGUI.drawPanel.oldY = 0;
		} else if (command.equals("clockwise")) {
			DrawPanel.rotate90ToRight(DrawPanel.ss);
		} else if (command.equals("anticlockwise")) {
			DrawPanel.rotate90ToLeft(DrawPanel.ss);
		} else if (command.equals("reddit")) {
			EditorGUI.drawPanel.mergeAndSend("reddit");
		} else if (command.equals("fb")) {
			EditorGUI.drawPanel.mergeAndSend("facebook");
		} else if (command.equals("twitter")) {
			EditorGUI.drawPanel.mergeAndSend("twitter");
		} else if (command.equals("g+")) {
			EditorGUI.drawPanel.mergeAndSend("google");
		} else if (command.equals("upload")) {
			EditorGUI.drawPanel.mergeAndSend("upload");
		} else if (command.equals("print")) {
			print();
		} else if (command.equals("save")) {
			EditorGUI.drawPanel.save();
		} else if (command.equals("copy")) {
			EditorGUI.drawPanel.imageToClipboard();
		}

	}

	private void print() {
		new PrintImage();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
}
