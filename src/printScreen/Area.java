package printScreen;

import editor.Paint;
import ini.Reader;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;

import makeshot.Icon;
import settings.Fix;
import settings.Kit;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import logs.LogError;
import logs.Logging;
import settings.Static;
import sound.Play;
import upload.Start;

public class Area extends JDialog {
	Rectangle areaRect = new Rectangle(1, 1, 1, 1);
	public boolean isVisible;

	public void create() {
		this.isVisible = true;
		Fix.ssDir();
		setIconImage(Icon.get());

		setDefaultCloseOperation(2);
		ScreenScraper obrazek = new ScreenScraper(this);
		getContentPane().add(obrazek);
		int height = (int) Kit.get().getScreenSize().getHeight();
		int width = (int) Kit.get().getScreenSize().getWidth();
		setBounds(0, 0, width, height);
		setResizable(false);
		dispose();
		setUndecorated(true);
		setOpacity(0.5F);

		Point cursorHotSpot = new Point(15, 15);
		Cursor customCursor = Kit.get().createCustomCursor(
				Kit.get().getImage(
						Paint.class.getResource("/printScreen/cursor.png")),
				cursorHotSpot, "Cursor");
		setCursor(customCursor);
		getContentPane().setBackground(Color.black);
		setVisible(true);
		toFront();
		setAlwaysOnTop(true);
		setAutoRequestFocus(true);
	}

	public void dispose(int way) {
		if (way == 1) {
			dispose();
			this.isVisible = true;
		} else {
			ScreenScraper.x1 = 0;
			ScreenScraper.x2 = 0;
			ScreenScraper.y1 = 0;
			ScreenScraper.y2 = 0;
			dispose();
			this.isVisible = false;
		}
	}
}

class ScreenScraper extends JPanel implements MouseListener,
		MouseMotionListener {
	static int x1;
	static int x2;
	static int y1;
	static int y2;
	Area area;
	Reader read = new Reader();

	ScreenScraper(Area area) {
		area.areaRect = new Rectangle(1, 1, 1, 1);
		addMouseListener(this);
		addMouseMotionListener(this);
		this.area = area;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		repaint();
		x2 = e.getX();
		y2 = e.getY();
		repaint();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
		repaint();
		x1 = e.getX();
		y1 = e.getY();
		x2 = x1;
		y2 = y1;
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		this.area.dispose(1);
		take();
		x1 = 0;
		x2 = 0;
		y1 = 0;
		y2 = 0;
		repaint();
	}

	public void paintComponent(Graphics th) {
		super.paintComponent(th);
		Graphics2D g = (Graphics2D) th;
		setBackground(Color.BLACK);
		g.setColor(Color.WHITE);
		g.setComposite(AlphaComposite.getInstance(3, 0.3F));
		g.fill(this.area.areaRect);
		g.setComposite(AlphaComposite.getInstance(3, 1.0F));
		g.drawString((int) this.area.areaRect.getWidth() + "x"
				+ (int) this.area.areaRect.getHeight(),
				(int) this.area.areaRect.getMaxX() - 50,
				(int) this.area.areaRect.getMaxY() + 11);
		if ((x1 < x2) && (y1 < y2)) {
			this.area.areaRect.setBounds(x1, y1, Math.abs(x2 - x1),
					Math.abs(y2 - y1));
		} else if ((x1 > x2) && (y1 > y2)) {
			this.area.areaRect.setBounds(x2, y2, Math.abs(x2 - x1),
					Math.abs(y2 - y1));
		} else if (y1 < y2) {
			this.area.areaRect.setBounds(x2, y1, Math.abs(x2 - x1),
					Math.abs(y2 - y1));
		} else if (y1 > y2) {
			this.area.areaRect.setBounds(x1, y2, Math.abs(x2 - x1),
					Math.abs(y2 - y1));
		}
	}

	private void take() {
		if ((this.area.areaRect.getWidth() >= 1.0D)
				&& (this.area.areaRect.getHeight() >= 1.0D)) {
			DateFormat dateFormat = new SimpleDateFormat("yy.MM.dd-HH.mm.ss");
			Date date = new Date();
			try {
				if (Static.saveSs == 1) {
					BufferedImage image = new Robot()
							.createScreenCapture(this.area.areaRect);

					String current = Static.ssDirectory
							+ "\\"
							+ dateFormat.format(date)
							+ "-"
							+ new BigInteger(32, new SecureRandom())
									.toString(32) + "." + Static.ext;
					ImageIO.write(image, Static.ext, new File(current));
					if (Static.editSs == 1) {
						new Paint(current);
					} else {
						Runnable r = new Start(current, "");
						new Thread(r).start();
						Logging.logger.info("Upload started");
					}
				} else {
					BufferedImage image = new Robot()
							.createScreenCapture(this.area.areaRect);
					File file = new File(Static.ssDirectory + "/temp"
							+ "/tempfile." + Static.ext);
					ImageIO.write(image, Static.ext, file);
					if (Static.editSs == 1) {
						new Paint(file.toString());
					} else {
						Runnable r = new Start(file.toString(), "");
						new Thread(r).start();
						Logging.logger.info("Upload started");
					}
				}
			} catch (Exception e) {
				Play.error();
				LogError.get(e);
			}
		}
	}
}
