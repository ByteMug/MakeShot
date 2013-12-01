package editor.tools;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import editor.Bottom;

public class Arrow {

	public static void paint(Graphics g, int oldX, int oldY, int currentX,
			int currentY, int size) {
		((Graphics2D) g).setStroke(new BasicStroke(size - 3,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		Line2D line2d = new Line2D.Double(new Point(oldX, oldY), new Point(
				currentX, currentY));
		((Graphics2D) g).draw(line2d);

	}

	public static void released(Graphics2D gz, int size, Shape line) {
		gz.setStroke(new BasicStroke(size - 3, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER));
		Line2D shape = (Line2D) line;
		double angle = Math.atan2(shape.getY2() - shape.getY1(), shape.getX2()
				- shape.getX1());

		int arrowHeight = Integer.valueOf((String) Bottom.sizeBox
				.getSelectedItem()) + 2; // change as seen fit
		int halfArrowWidth = Integer.valueOf((String) Bottom.sizeBox
				.getSelectedItem()); // this too

		Point2D end = shape.getP2();
		Point2D aroBase = new Point2D.Double(shape.getX2() - arrowHeight
				* Math.cos(angle), shape.getY2() - arrowHeight
				* Math.sin(angle));
		Point2D end1 = new Point2D.Double(aroBase.getX() - halfArrowWidth
				* Math.cos(angle - Math.PI / 2), aroBase.getY()
				- halfArrowWidth * Math.sin(angle - Math.PI / 2));

		Point2D end2 = new Point2D.Double(aroBase.getX() + halfArrowWidth
				* Math.cos(angle - Math.PI / 2), aroBase.getY()
				+ halfArrowWidth * Math.sin(angle - Math.PI / 2));
		List<Shape> linesList = new ArrayList<Shape>();
		linesList.add(new Line2D.Double(end, end2));
		linesList.add(new Line2D.Double(end, end1));

		for (Shape content : linesList) {
			gz.draw(content);
		}
		gz.draw(line);
	}

}
