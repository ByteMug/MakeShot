package editor;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import editor.tools.Emotions;
import editor.tools.PrintImage;

class ActionListener extends AbstractAction implements MouseListener {

	String command;

	public ActionListener(ImageIcon imageIcon, String command) {
		super(null, imageIcon);
		this.command = command;
		// addMouseListener();
	}

	public void actionPerformed(ActionEvent e) {
		Paint.drawPanel.setCursor(Cursor
				.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		if (command.equals("brush")) {
			Paint.currentTool = Paint.BRUSH;
		} else if (command.equals("highlighter")) {
			Paint.currentTool = Paint.LIGHTER;
		} else if (command.equals("text")) {
			Paint.currentTool = Paint.TEXT;
		} else if (command.equals("blur")) {
			Paint.currentTool = Paint.BLUR;
			Paint.drawPanel.x1 = 0;
			Paint.drawPanel.y1 = 0;
		} else if (command.equals("line")) {
			Paint.currentTool = Paint.LINE;
			Paint.drawPanel.x1 = 0;
			Paint.drawPanel.y1 = 0;
		} else if (command.equals("arrow")) {
			Paint.currentTool = Paint.ARROW;
			Paint.drawPanel.x1 = 0;
			Paint.drawPanel.y1 = 0;
		} else if (command.equals("rectangle")) {
			Paint.currentTool = Paint.RECT;
			Paint.drawPanel.x1 = 0;
			Paint.drawPanel.y1 = 0;
		} else if (command.equals("circle")) {
			Paint.currentTool = Paint.CIRCLE;
			Paint.drawPanel.x1 = 0;
			Paint.drawPanel.y1 = 0;
			// bottom
		} else if (command.equals("pipette")) {
			Paint.currentTool = Paint.PICKER;
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
			Paint.currentTool = Paint.ERASER;

		} else if (command.equals("emo")) {
			Emotions.asd.add(Emotions.small);
			Emotions.asd.add(Emotions.big);

			Emotions.small.add(Emotions.angry32);
			Emotions.small.add(Emotions.blush32);
			Emotions.small.add(Emotions.cry32);
			Emotions.small.add(Emotions.eye32);
			Emotions.small.add(Emotions.happy32);
			Emotions.small.add(Emotions.macho32);
			Emotions.small.add(Emotions.sad32);
			Emotions.small.add(Emotions.smile32);
			Emotions.small.add(Emotions.tongue32);
			Emotions.small.add(Emotions.wow32);

			Emotions.big.add(Emotions.angry64);
			Emotions.big.add(Emotions.blush64);
			Emotions.big.add(Emotions.cry64);
			Emotions.big.add(Emotions.eye64);
			Emotions.big.add(Emotions.happy64);
			Emotions.big.add(Emotions.macho64);
			Emotions.big.add(Emotions.sad64);
			Emotions.big.add(Emotions.smile64);
			Emotions.big.add(Emotions.tongue64);
			Emotions.big.add(Emotions.wow64);
			Emotions.asd.show(Paint.frame, 34, 294);
		} else if (command.equals("crop")) {
			Paint.currentTool = Paint.CROP;
			Paint.drawPanel.setCursor(Cursor
					.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			Paint.drawPanel.x1 = 0;
			Paint.drawPanel.y1 = 0;
		} else if (command.equals("clockwise")) {
			DrawPanel.rotate90ToRight(DrawPanel.ss);
		} else if (command.equals("anticlockwise")) {
			DrawPanel.rotate90ToLeft(DrawPanel.ss);
		} else if (command.equals("reddit")) {
			Paint.drawPanel.mergeAndSend("reddit");
		} else if (command.equals("fb")) {
			Paint.drawPanel.mergeAndSend("facebook");
		} else if (command.equals("twitter")) {
			Paint.drawPanel.mergeAndSend("twitter");
		} else if (command.equals("g+")) {
			Paint.drawPanel.mergeAndSend("google");
		} else if (command.equals("upload")) {
			Paint.drawPanel.mergeAndSend("upload");
		} else if (command.equals("print")) {
			print();
		} else if (command.equals("save")) {
			Paint.drawPanel.save();
		} else if (command.equals("copy")) {
			Paint.drawPanel.imageToClipboard();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
