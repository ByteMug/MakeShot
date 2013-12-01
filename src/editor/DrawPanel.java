package editor;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import logs.LogError;
import settings.Static;
import upload.Start;
import editor.tools.Arrow;
import editor.tools.Blur;
import editor.tools.Emotions;

public class DrawPanel extends JLayeredPane implements MouseListener,
		MouseMotionListener {
	static BufferedImage blurredImage;
	static BufferedImage blurredImage2;
	private static BufferedImage finals;
	private static Graphics2D gz, temp;
	public static BufferedImage img, clear;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static BufferedImage ss;

	public static void rotate90ToLeft(BufferedImage inputImage) {

		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		BufferedImage returnImage = new BufferedImage(height, width,
				inputImage.getType());

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				returnImage.setRGB(y, width - x - 1, inputImage.getRGB(x, y));

			}
		}
		ss = returnImage;

	}

	public static void rotate90ToRight(BufferedImage inputImage) {
		int width = ss.getWidth();
		int height = ss.getHeight();
		BufferedImage returnImage = new BufferedImage(height, width,
				ss.getType());
		BufferedImage returnImage2 = new BufferedImage(height, width,
				img.getType());

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				returnImage.setRGB(height - y - 1, x, ss.getRGB(x, y));
				returnImage2.setRGB(height - y - 1, x, img.getRGB(x, y));
			}
		}
		ss = returnImage;
		img = returnImage2;

		gz = (Graphics2D) img.getGraphics();
		gz.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}

	Rectangle cropRect = new Rectangle(0, 0, 0, 0);

	private Shape line = null;

	private String pathu;

	private Runnable r;
	int size = Integer.valueOf(Bottom.sizeBox.getSelectedItem().toString());

	BasicStroke stroke = new BasicStroke(Integer.valueOf(Bottom.sizeBox
			.getSelectedItem().toString()) - 3, BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_ROUND);

	Rectangle toCrop = new Rectangle(1, 1, 1, 1);

	int x1, x2, y1, y2;

	public DrawPanel() {

	}

	public DrawPanel(final String path) {
		finals = null;
		temp = null;
		pathu = path;
		ss = null;
		img = null;
		gz = null;
		try {
			ss = ImageIO.read(new File(path));
			img = new BufferedImage(ss.getWidth(), ss.getHeight(), 2);
			clear = img;
			gz = img.createGraphics();
			gz.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			setPreferredSize(new Dimension(img.getWidth(this),
					img.getHeight(this)));

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Invalid image", "File",
					JOptionPane.ERROR_MESSAGE);
			LogError.get(e);
		}

		addMouseListener(this);
		addMouseMotionListener(this);
		repaint();

		keyStrokesHandler();

	}

	private void crop() {
		if (toCrop.x + toCrop.width > ss.getWidth()) {
			toCrop.width -= ss.getWidth() - toCrop.width;
		}
		ss = ss.getSubimage(toCrop.x, toCrop.y, toCrop.width, toCrop.height);
		img = img.getSubimage(toCrop.x, toCrop.y, toCrop.width, toCrop.height);
		gz = (Graphics2D) img.getGraphics();
		setPreferredSize(new Dimension(img.getWidth(this), img.getHeight(this)));
		repaint();
	}

	public void imageToClipboard() {
		finals = null;
		temp = null;
		finals = new BufferedImage(img.getWidth(), img.getHeight(),
				Static.imageType);
		temp = (Graphics2D) finals.getGraphics();
		temp.drawImage(ss, 0, 0, null);
		temp.drawImage(img, 0, 0, null);
		new ClipboardImage(finals);
	}

	private void keyStrokesHandler() {
		ActionMap actionMap = this.getActionMap();
		actionMap.put("copy", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				imageToClipboard();
			}
		});
		actionMap.put("save", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		actionMap.put("close", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Chooser.frame.dispose();
				Paint.frame.dispose();
			}
		});
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(
				KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK),
				"copy");

		inputMap.put(
				KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK),
				"save");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");

	}

	public void mergeAndSend(String uploadType) {
		finals = null;
		temp = null;
		finals = new BufferedImage(img.getWidth(), img.getHeight(),
				Static.imageType);

		temp = (Graphics2D) finals.getGraphics();
		temp.drawImage(ss, 0, 0, this);
		temp.drawImage(img, 0, 0, this);
		this.removeAll();
		Paint.frame.removeAll();
		Paint.frame.dispose();
		System.gc();
		Runtime.getRuntime().gc();
		try {
			if (settings.Static.saveSs == 1) {
				ImageIO.write(finals, settings.Static.ext, new File(pathu));

				r = new Start(pathu, uploadType);
				new Thread(r).start();

			} else {
				File file = new File(Static.ssDirectory + "/temp"
						+ "/tempfile." + settings.Static.ext);
				ImageIO.write(finals, settings.Static.ext, file);
				r = new Start(file.toString(), uploadType);
				new Thread(r).start();

			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Invalid image", "File",
					JOptionPane.ERROR_MESSAGE);
			LogError.get(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (Paint.currentTool == Paint.TEXT) {
			gz.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			gz.setColor(Bottom.color.getBackground());
			gz.setFont(Paint.getCheckFont());
			gz.drawString(Bottom.textField.getText(), x2, y2);
			repaint();
		} else if (Paint.currentTool == Paint.PICKER) {
			int asd = ((BufferedImage) ss).getRGB(arg0.getX(), arg0.getY());
			Bottom.color.setBackground(new Color(asd));
		} else if (Paint.currentTool == Paint.EMO) {
			gz.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			if (Emotions.currentEmo.getWidth() == 128) {
				gz.drawImage(Emotions.currentEmo, arg0.getX() - 64,
						arg0.getY() - 64, null);
			} else {
				gz.drawImage(Emotions.currentEmo, arg0.getX() - 24,
						arg0.getY() - 24, null);
			}
			repaint();
		}
	}

	public void asd(BufferedImage asd, int x, int y, int w, int h) {
		gz.drawImage(asd, x, y, w, h, null);
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			gz.setColor(Bottom.color.getBackground());
			if (Paint.currentTool == Paint.BRUSH) {
				gz.setStroke(stroke);
				gz.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER));
				x2 = e.getX();
				y2 = e.getY();
				gz.drawLine(x1, y1, x2, y2);
				repaint();
				x1 = x2;
				y1 = y2;
			} else if (Paint.currentTool == Paint.ERASER) {
				x2 = e.getX();
				y2 = e.getY();
				gz.setStroke(stroke);
				gz.setComposite(AlphaComposite
						.getInstance(AlphaComposite.CLEAR));
				gz.drawOval(x2 - 5, y2, size, size);
				repaint();
				x1 = x2;
				y1 = y2;
			} else if (Paint.currentTool == Paint.LIGHTER) {
				x2 = e.getX();
				y2 = e.getY();
				gz.setStroke(stroke);
				gz.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC,
						0.4f));
				gz.drawLine(x1, y1, x2, y2);
				repaint();
				x1 = x2;
				y1 = y2;
			} else if (Paint.currentTool == Paint.LINE) {
				gz.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER));
				x2 = e.getX();
				y2 = e.getY();
				repaint();
			} else if (Paint.currentTool == Paint.BLUR) {
				x2 = e.getX();
				y2 = e.getY();
				repaint();
			} else if (Paint.currentTool == Paint.ARROW) {
				gz.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER));
				gz.setStroke(stroke);
				x2 = e.getX();
				y2 = e.getY();
				Line2D shape = (Line2D) line;
				shape.setLine(shape.getP1(), e.getPoint());
				repaint();
			} else if (Paint.currentTool == Paint.RECT) {
				gz.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER));
				gz.setStroke(stroke);
				x2 = e.getX();
				y2 = e.getY();
				repaint();
			} else if (Paint.currentTool == Paint.CIRCLE) {
				gz.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER));
				gz.setStroke(stroke);
				x2 = e.getX();
				y2 = e.getY();
				repaint();
			} else if (Paint.currentTool == Paint.CROP) {
				repaint();
				x2 = e.getX();
				y2 = e.getY();
				repaint();
			}
		}
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
	public void mouseMoved(MouseEvent e) {
		size = Integer.valueOf(Bottom.sizeBox.getSelectedItem().toString());
		stroke = new BasicStroke(Integer.valueOf(Bottom.sizeBox
				.getSelectedItem().toString()) - 3, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);
		x2 = e.getX();
		y2 = e.getY();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		if (Paint.currentTool == Paint.LINE) {
			gz.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		} else if (Paint.currentTool == Paint.BLUR) {
			x1 = e.getX();
			y1 = e.getY();
			repaint();
			x2 = x1;
			y2 = y1;
			repaint();
		} else if (Paint.currentTool == Paint.ARROW) {
			line = new Line2D.Double(e.getPoint(), e.getPoint());
		} else if (Paint.currentTool == Paint.RECT) {
			x1 = e.getX();
			y1 = e.getY();
			repaint();
			x2 = x1;
			y2 = y1;
			repaint();
		} else if (Paint.currentTool == Paint.CIRCLE) {
			x2 = e.getX();
			y2 = e.getY();
			repaint();
			x1 = x2;
			y1 = y2;
		} else if (Paint.currentTool == Paint.CROP) {
			repaint();
			x1 = e.getX();
			y1 = e.getY();
			repaint();
			x2 = x1;
			y2 = y1;

		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (Paint.currentTool == Paint.LINE) {
			gz.setStroke(stroke);
			Line2D line2d = new Line2D.Double(new Point(x1, y1), new Point(x2,
					y2));
			gz.draw(line2d);
			x1 = 0;
			y1 = 0;
		} else if (Paint.currentTool == Paint.ARROW) {
			Arrow.released(gz, size, line);
			x1 = 0;
			y1 = 0;
			repaint();
		} else if (Paint.currentTool == Paint.BLUR) {
			if (x1 != 0 && y1 != 0) {
				if (x2 > x1 && y2 > y1) {
					Blur.dupa(ss, x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
				} else if (x1 > x2 && y1 > y2) {
					Blur.dupa(ss, x2, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
				} else if (y1 < y2) {
					Blur.dupa(ss, x2, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
				} else if (y1 > y2) {
					Blur.dupa(ss, x1, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
				}
				x1 = 0;
				y1 = 0;
			}
		} else if (Paint.currentTool == Paint.RECT) {
			gz.setStroke(stroke);
			if (x1 != 0 && y1 != 0) {
				if (Bottom.chckbxFillRectoval.isSelected()) {
					if (x2 > x1 && y2 > y1) {
						gz.fillRect(x1, y1, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					} else if (x1 > x2 && y1 > y2) {
						gz.fillRect(x2, y2, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					} else if (y1 < y2) {
						gz.fillRect(x2, y1, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					} else if (y1 > y2) {
						gz.fillRect(x1, y2, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					}
				} else {
					if (x2 > x1 && y2 > y1) {
						gz.drawRect(x1, y1, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					} else if (x1 > x2 && y1 > y2) {
						gz.drawRect(x2, y2, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					} else if (y1 < y2) {
						gz.drawRect(x2, y1, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					} else if (y1 > y2) {
						gz.drawRect(x1, y2, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					}
				}
			}
			x1 = 0;
			y1 = 0;
			repaint();
		} else if (Paint.currentTool == Paint.CIRCLE) {
			gz.setStroke(stroke);
			if (x1 != 0 && y1 != 0) {
				if (Bottom.chckbxFillRectoval.isSelected()) {
					if (x2 > x1 && y2 > y1) {
						gz.fillOval(x1, y1, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					} else if (x1 > x2 && y1 > y2) {
						gz.fillOval(x2, y2, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					} else if (y1 < y2) {
						gz.fillOval(x2, y1, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					} else if (y1 > y2) {
						gz.fillOval(x1, y2, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					}
				} else {
					if (x2 > x1 && y2 > y1) {
						gz.drawOval(x1, y1, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					} else if (x1 > x2 && y1 > y2) {
						gz.drawOval(x2, y2, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					} else if (y1 < y2) {
						gz.drawOval(x2, y1, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					} else if (y1 > y2) {
						gz.drawOval(x1, y2, Math.abs(x2 - x1),
								Math.abs(y2 - y1));
					}
				}
			}
			x1 = 0;
			y1 = 0;
			repaint();
		} else if (Paint.currentTool == Paint.CROP) {
			crop();
			repaint();
			x1 = 0;
			x2 = 0;
			y1 = 0;
			y2 = 0;
			toCrop.setBounds(0, 0, 0, 0);

		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(ss, 0, 0, this);
		g.drawImage(img, 0, 0, this);
		((Graphics2D) g).setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OVER));
		g.setColor(Bottom.color.getBackground());
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (Paint.currentTool == Paint.BRUSH) {
			((Graphics2D) g).setStroke(stroke);
			g.drawLine(x2, y2, x2, y2);

		} else if (Paint.currentTool == Paint.LIGHTER) {
			((Graphics2D) g).setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC, 0.4f));
			((Graphics2D) g).setStroke(stroke);

			g.drawLine(x2, y2, x2, y2);

		} else if (Paint.currentTool == Paint.TEXT) {

			g.setFont(Paint.getCheckFont());
			g.drawString(Bottom.textField.getText(), x2, y2);

		} else if (Paint.currentTool == Paint.ERASER) {
			((Graphics2D) g).setStroke(stroke);

			g.setColor(Color.black);
			g.drawOval(x2 - 6, y2 - 1, size, size);
			g.setColor(Color.white);
			g.drawOval(x2 - 5, y2, size - 2, size - 2);
		} else if (Paint.currentTool == Paint.LINE) {
			if (x1 != 0 && y1 != 0) {
				((Graphics2D) g).setStroke(stroke);
				Line2D line2d = new Line2D.Double(new Point(x1, y1), new Point(
						x2, y2));
				((Graphics2D) g).draw(line2d);
			}
		} else if (Paint.currentTool == Paint.BLUR) {
			g.setColor(Color.BLACK);
			gz.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			((Graphics2D) g).setStroke(new BasicStroke(1,
					BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			if (x1 != 0 && y1 != 0) {
				if (x2 > x1 && y2 > y1) {
					g.drawRect(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
				} else if (x1 > x2 && y1 > y2) {
					g.drawRect(x2, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
				} else if (y1 < y2) {
					g.drawRect(x2, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
				} else if (y1 > y2) {
					g.drawRect(x1, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
				}
			}

		} else if (Paint.currentTool == Paint.ARROW) {
			if (x1 != 0 && y1 != 0) {
				Arrow.paint(g, x1, y1, x2, y2, size);
			}
		} else if (Paint.currentTool == Paint.RECT) {
			((Graphics2D) g).setStroke(stroke);
			if (x1 != 0 && y1 != 0) {
				if (Bottom.chckbxFillRectoval.isSelected()) {
					if (x2 > x1 && y2 > y1) {
						g.fillRect(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
					} else if (x1 > x2 && y1 > y2) {
						g.fillRect(x2, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
					} else if (y1 < y2) {
						g.fillRect(x2, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
					} else if (y1 > y2) {
						g.fillRect(x1, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
					}
				} else {
					if (x2 > x1 && y2 > y1) {
						g.drawRect(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
					} else if (x1 > x2 && y1 > y2) {
						g.drawRect(x2, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
					} else if (y1 < y2) {
						g.drawRect(x2, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
					} else if (y1 > y2) {
						g.drawRect(x1, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
					}
				}
			}

		} else if (Paint.currentTool == Paint.CIRCLE) {
			((Graphics2D) g).setStroke(stroke);
			if (x1 != 0 && y1 != 0) {
				if (Bottom.chckbxFillRectoval.isSelected()) {
					if (x2 > x1 && y2 > y1) {
						g.fillOval(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
					} else if (x1 > x2 && y1 > y2) {
						g.fillOval(x2, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
					} else if (y1 < y2) {
						g.fillOval(x2, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
					} else if (y1 > y2) {
						g.fillOval(x1, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
					}
				} else {
					if (x2 > x1 && y2 > y1) {
						g.drawOval(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
					} else if (x1 > x2 && y1 > y2) {
						g.drawOval(x2, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
					} else if (y1 < y2) {
						g.drawOval(x2, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
					} else if (y1 > y2) {
						g.drawOval(x1, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
					}
				}

			}
		} else if (Paint.currentTool == Paint.CROP) {
			((Graphics2D) g).draw(toCrop);
			if (x1 != 0 && y1 != 0) {
				if (x1 < x2 && y1 < y2) {
					toCrop.setBounds(x1, y1, Math.abs(x2 - x1),
							Math.abs(y2 - y1));

				} else if (x1 > x2 && y1 > y2) {
					toCrop.setBounds(x2, y2, Math.abs(x2 - x1),
							Math.abs(y2 - y1));

				} else if (y1 < y2) {
					toCrop.setBounds(x2, y1, Math.abs(x2 - x1),
							Math.abs(y2 - y1));

				} else if (y1 > y2) {
					toCrop.setBounds(x1, y2, Math.abs(x2 - x1),
							Math.abs(y2 - y1));
				}
			}
		} else if (Paint.currentTool == Paint.EMO) {
			if (Emotions.currentEmo.getWidth() == 128) {
				g.drawImage(Emotions.currentEmo, x2 - 64, y2 - 64, null);
			} else {
				g.drawImage(Emotions.currentEmo, x2 - 24, y2 - 24, null);
			}
			repaint();
		}

	}

	public void save() {
		JFileChooser fcd = new JFileChooser();
		fcd.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileFilter ff = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
		FileFilter ff2 = new FileNameExtensionFilter("PNG file", "png");
		FileFilter ff3 = new FileNameExtensionFilter("GIF file", "gif");

		fcd.addChoosableFileFilter(ff);
		fcd.addChoosableFileFilter(ff2);
		fcd.addChoosableFileFilter(ff3);
		fcd.setFileFilter(ff2);
		int returnVal = fcd.showSaveDialog(Paint.frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fcd.getSelectedFile();
			finals = null;
			temp = null;
			try {

				if (fcd.getFileFilter().getDescription().contains("JPEG")) {

					finals = new BufferedImage(img.getWidth(), img.getHeight(),
							BufferedImage.TYPE_3BYTE_BGR);

					temp = (Graphics2D) finals.getGraphics();
					temp.drawImage(ss, 0, 0, this);
					temp.drawImage(img, 0, 0, this);
					ImageIO.write(finals, "jpg",
							new File(file.getAbsolutePath() + ".jpg"));

				} else if (fcd.getFileFilter().getDescription().contains("PNG")) {
					finals = new BufferedImage(img.getWidth(), img.getHeight(),
							Static.imageType);

					temp = (Graphics2D) finals.getGraphics();
					temp.drawImage(ss, 0, 0, this);
					temp.drawImage(img, 0, 0, this);
					ImageIO.write(finals, "png",
							new File(file.getAbsolutePath() + ".png"));
				} else if (fcd.getFileFilter().getDescription().contains("GIF")) {
					finals = new BufferedImage(img.getWidth(), img.getHeight(),
							Static.imageType);

					temp = (Graphics2D) finals.getGraphics();
					temp.drawImage(ss, 0, 0, this);
					temp.drawImage(img, 0, 0, this);
					ImageIO.write(finals, "gif",
							new File(file.getAbsolutePath() + ".gif"));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
