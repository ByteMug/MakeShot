package net.makeshot.imageEditor.additional;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JOptionPane;

import net.makeshot.imageEditor.DrawPanel;
import net.makeshot.imageEditor.EditorGUI;

public class PrintImage implements Printable {
	public PrintImage() {
		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setPrintable(this);
		if (pj.printDialog()) {
			try {
				pj.print();
			} catch (PrinterException e) {
				JOptionPane.showMessageDialog(EditorGUI.frame,
						"Check your printer", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex) {
		if (pageIndex != 0)
			return NO_SUCH_PAGE;
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(DrawPanel.ss, 0, 0, DrawPanel.ss.getWidth(),
				DrawPanel.ss.getHeight(), null);
		return PAGE_EXISTS;
	}

}