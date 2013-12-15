package net.makeshot.imageEditor;

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

import net.makeshot.imageEditor.additional.Arrow;
import net.makeshot.imageEditor.additional.Blur;
import net.makeshot.imageEditor.additional.Emoticons;
import net.makeshot.logs.LogError;
import net.makeshot.main.Cleaner;
import net.makeshot.settings.Static;
import net.makeshot.upload.Start;

public class DrawPanel extends JLayeredPane implements MouseListener,
		MouseMotionListener {
	/*
	 * The whole panel where the graphic is being drawn. Every image
	 * manipulation happens below.
	 */
	static BufferedImage blurredImage;
	static BufferedImage blurredImage2;
	private static BufferedImage finals;
	private static Graphics2D gz, temp;
	public static BufferedImage img, clear;

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
	int size = Integer.valueOf(DrawSettingsToolbar.sizeBox.getSelectedItem()
			.toString());

	BasicStroke stroke = new BasicStroke(
			Integer.valueOf(DrawSettingsToolbar.sizeBox.getSelectedItem()
					.toString()) - 3, BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_ROUND);

	Rectangle toCrop = new Rectangle(1, 1, 1, 1);

	int oldX, currentX, oldY, currentY;

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
			@Override
			public void actionPerformed(ActionEvent e) {
				imageToClipboard();
			}
		});
		actionMap.put("save", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		actionMap.put("close", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Chooser.frame.dispose();
				EditorGUI.frame.dispose();
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
		EditorGUI.frame.removeAll();
		EditorGUI.frame.dispose();
		Chooser.frame.dispose();
		try {
			if (net.makeshot.settings.Static.saveSs == 1) {
				ImageIO.write(finals, net.makeshot.settings.Static.ext, new File(pathu));

				r = new Start(pathu, uploadType);
				new Thread(r).start();

			} else {
				File file = new File(Static.ssDirectory + "/temp"
						+ "/tempfile." + net.makeshot.settings.Static.ext);
				ImageIO.write(finals, net.makeshot.settings.Static.ext, file);
				r = new Start(file.toString(), uploadType);
				new Thread(r).start();

			}
			Cleaner.gc();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Invalid image", "File",
					JOptionPane.ERROR_MESSAGE);
			LogError.get(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (EditorGUI.currentTool == EditorGUI.TEXT) {
			gz.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			gz.setColor(DrawSettingsToolbar.color.getBackground());
			gz.setFont(EditorGUI.getCheckFont());
			gz.drawString(DrawSettingsToolbar.textField.getText(), currentX,
					currentY);
			repaint();
		} else if (EditorGUI.currentTool == EditorGUI.PICKER) {
			int asd = ss.getRGB(arg0.getX(), arg0.getY());
			DrawSettingsToolbar.color.setBackground(new Color(asd));
		} else if (EditorGUI.currentTool == EditorGUI.EMO) {
			gz.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			if (Emoticons.currentEmo.getWidth() == 128) {
				gz.drawImage(Emoticons.currentEmo, arg0.getX() - 64,
						arg0.getY() - 64, null);
			} else {
				gz.drawImage(Emoticons.currentEmo, arg0.getX() - 24,
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
			gz.setColor(DrawSettingsToolbar.color.getBackground());
			if (EditorGUI.currentTool == EditorGUI.BRUSH) {
				gz.setStroke(stroke);
				gz.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER));
				currentX = e.getX();
				currentY = e.getY();
				gz.drawLine(oldX, oldY, currentX, currentY);
				repaint();
				oldX = currentX;
				oldY = currentY;
			} else if (EditorGUI.currentTool == EditorGUI.ERASER) {
				currentX = e.getX();
				currentY = e.getY();
				gz.setStroke(stroke);
				gz.setComposite(AlphaComposite
						.getInstance(AlphaComposite.CLEAR));
				gz.drawOval(currentX - 5, currentY, size, size);
				repaint();
				oldX = currentX;
				oldY = currentY;
			} else if (EditorGUI.currentTool == EditorGUI.LIGHTER) {
				currentX = e.getX();
				currentY = e.getY();
				gz.setStroke(stroke);
				gz.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC,
						0.4f));
				gz.drawLine(oldX, oldY, currentX, currentY);
				repaint();
				oldX = currentX;
				oldY = currentY;
			} else if (EditorGUI.currentTool == EditorGUI.LINE) {
				gz.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER));
				currentX = e.getX();
				currentY = e.getY();
				repaint();
			} else if (EditorGUI.currentTool == EditorGUI.BLUR) {
				currentX = e.getX();
				currentY = e.getY();
				repaint();
			} else if (EditorGUI.currentTool == EditorGUI.ARROW) {
				gz.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER));
				gz.setStroke(stroke);
				currentX = e.getX();
				currentY = e.getY();
				Line2D shape = (Line2D) line;
				shape.setLine(shape.getP1(), e.getPoint());
				repaint();
			} else if (EditorGUI.currentTool == EditorGUI.RECT) {
				gz.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER));
				gz.setStroke(stroke);
				currentX = e.getX();
				currentY = e.getY();
				repaint();
			} else if (EditorGUI.currentTool == EditorGUI.CIRCLE) {
				gz.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER));
				gz.setStroke(stroke);
				currentX = e.getX();
				currentY = e.getY();
				repaint();
			} else if (EditorGUI.currentTool == EditorGUI.CROP) {
				repaint();
				currentX = e.getX();
				currentY = e.getY();
				repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		size = Integer.valueOf(DrawSettingsToolbar.sizeBox.getSelectedItem()
				.toString());
		stroke = new BasicStroke(Integer.valueOf(DrawSettingsToolbar.sizeBox
				.getSelectedItem().toString()) - 3, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);
		currentX = e.getX();
		currentY = e.getY();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		oldX = e.getX();
		oldY = e.getY();
		if (EditorGUI.currentTool == EditorGUI.LINE) {
			gz.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		} else if (EditorGUI.currentTool == EditorGUI.BLUR) {
			oldX = e.getX();
			oldY = e.getY();
			repaint();
			currentX = oldX;
			currentY = oldY;
			repaint();
		} else if (EditorGUI.currentTool == EditorGUI.ARROW) {
			line = new Line2D.Double(e.getPoint(), e.getPoint());
		} else if (EditorGUI.currentTool == EditorGUI.RECT) {
			oldX = e.getX();
			oldY = e.getY();
			repaint();
			currentX = oldX;
			currentY = oldY;
			repaint();
		} else if (EditorGUI.currentTool == EditorGUI.CIRCLE) {
			currentX = e.getX();
			currentY = e.getY();
			repaint();
			oldX = currentX;
			oldY = currentY;
		} else if (EditorGUI.currentTool == EditorGUI.CROP) {
			repaint();
			oldX = e.getX();
			oldY = e.getY();
			repaint();
			currentX = oldX;
			currentY = oldY;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (EditorGUI.currentTool == EditorGUI.LINE) {
			gz.setStroke(stroke);
			Line2D line2d = new Line2D.Double(new Point(oldX, oldY), new Point(
					currentX, currentY));
			gz.draw(line2d);
			oldX = 0;
			oldY = 0;
		} else if (EditorGUI.currentTool == EditorGUI.ARROW) {
			Arrow.released(gz, size, line);
			oldX = 0;
			oldY = 0;
			repaint();
		} else if (EditorGUI.currentTool == EditorGUI.BLUR) {
			if (oldX != 0 && oldY != 0) {
				if (currentX > oldX && currentY > oldY) {
					Blur.dupa(ss, oldX, oldY, Math.abs(currentX - oldX),
							Math.abs(currentY - oldY));
				} else if (oldX > currentX && oldY > currentY) {
					Blur.dupa(ss, currentX, currentY,
							Math.abs(currentX - oldX),
							Math.abs(currentY - oldY));
				} else if (oldY < currentY) {
					Blur.dupa(ss, currentX, oldY, Math.abs(currentX - oldX),
							Math.abs(currentY - oldY));
				} else if (oldY > currentY) {
					Blur.dupa(ss, oldX, currentY, Math.abs(currentX - oldX),
							Math.abs(currentY - oldY));
				}
				oldX = 0;
				oldY = 0;
			}
		} else if (EditorGUI.currentTool == EditorGUI.RECT) {
			gz.setStroke(stroke);
			if (oldX != 0 && oldY != 0) {
				if (DrawSettingsToolbar.chckbxFillRectoval.isSelected()) {
					if (currentX > oldX && currentY > oldY) {
						gz.fillRect(oldX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldX > currentX && oldY > currentY) {
						gz.fillRect(currentX, currentY,
								Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY < currentY) {
						gz.fillRect(currentX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY > currentY) {
						gz.fillRect(oldX, currentY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					}
				} else {
					if (currentX > oldX && currentY > oldY) {
						gz.drawRect(oldX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldX > currentX && oldY > currentY) {
						gz.drawRect(currentX, currentY,
								Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY < currentY) {
						gz.drawRect(currentX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY > currentY) {
						gz.drawRect(oldX, currentY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					}
				}
			}
			oldX = 0;
			oldY = 0;
			repaint();
		} else if (EditorGUI.currentTool == EditorGUI.CIRCLE) {
			gz.setStroke(stroke);
			if (oldX != 0 && oldY != 0) {
				if (DrawSettingsToolbar.chckbxFillRectoval.isSelected()) {
					if (currentX > oldX && currentY > oldY) {
						gz.fillOval(oldX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldX > currentX && oldY > currentY) {
						gz.fillOval(currentX, currentY,
								Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY < currentY) {
						gz.fillOval(currentX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY > currentY) {
						gz.fillOval(oldX, currentY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					}
				} else {
					if (currentX > oldX && currentY > oldY) {
						gz.drawOval(oldX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldX > currentX && oldY > currentY) {
						gz.drawOval(currentX, currentY,
								Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY < currentY) {
						gz.drawOval(currentX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY > currentY) {
						gz.drawOval(oldX, currentY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					}
				}
			}
			oldX = 0;
			oldY = 0;
			repaint();
		} else if (EditorGUI.currentTool == EditorGUI.CROP) {
			crop();
			repaint();
			oldX = 0;
			currentX = 0;
			oldY = 0;
			currentY = 0;
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
		g.setColor(DrawSettingsToolbar.color.getBackground());
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (EditorGUI.currentTool == EditorGUI.BRUSH) {
			((Graphics2D) g).setStroke(stroke);
			g.drawLine(currentX, currentY, currentX, currentY);

		} else if (EditorGUI.currentTool == EditorGUI.LIGHTER) {
			((Graphics2D) g).setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC, 0.4f));
			((Graphics2D) g).setStroke(stroke);

			g.drawLine(currentX, currentY, currentX, currentY);

		} else if (EditorGUI.currentTool == EditorGUI.TEXT) {

			g.setFont(EditorGUI.getCheckFont());
			g.drawString(DrawSettingsToolbar.textField.getText(), currentX,
					currentY);

		} else if (EditorGUI.currentTool == EditorGUI.ERASER) {
			((Graphics2D) g).setStroke(stroke);

			g.setColor(Color.black);
			g.drawOval(currentX - 6, currentY - 1, size, size);
			g.setColor(Color.white);
			g.drawOval(currentX - 5, currentY, size - 2, size - 2);
		} else if (EditorGUI.currentTool == EditorGUI.LINE) {
			if (oldX != 0 && oldY != 0) {
				((Graphics2D) g).setStroke(stroke);
				Line2D line2d = new Line2D.Double(new Point(oldX, oldY),
						new Point(currentX, currentY));
				((Graphics2D) g).draw(line2d);
			}
		} else if (EditorGUI.currentTool == EditorGUI.BLUR) {
			g.setColor(Color.BLACK);
			gz.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			((Graphics2D) g).setStroke(new BasicStroke(1,
					BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			if (oldX != 0 && oldY != 0) {
				if (currentX > oldX && currentY > oldY) {
					g.drawRect(oldX, oldY, Math.abs(currentX - oldX),
							Math.abs(currentY - oldY));
				} else if (oldX > currentX && oldY > currentY) {
					g.drawRect(currentX, currentY, Math.abs(currentX - oldX),
							Math.abs(currentY - oldY));
				} else if (oldY < currentY) {
					g.drawRect(currentX, oldY, Math.abs(currentX - oldX),
							Math.abs(currentY - oldY));
				} else if (oldY > currentY) {
					g.drawRect(oldX, currentY, Math.abs(currentX - oldX),
							Math.abs(currentY - oldY));
				}
			}

		} else if (EditorGUI.currentTool == EditorGUI.ARROW) {
			if (oldX != 0 && oldY != 0) {
				Arrow.paint(g, oldX, oldY, currentX, currentY, size);
			}
		} else if (EditorGUI.currentTool == EditorGUI.RECT) {
			((Graphics2D) g).setStroke(stroke);
			if (oldX != 0 && oldY != 0) {
				if (DrawSettingsToolbar.chckbxFillRectoval.isSelected()) {
					if (currentX > oldX && currentY > oldY) {
						g.fillRect(oldX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldX > currentX && oldY > currentY) {
						g.fillRect(currentX, currentY,
								Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY < currentY) {
						g.fillRect(currentX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY > currentY) {
						g.fillRect(oldX, currentY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					}
				} else {
					if (currentX > oldX && currentY > oldY) {
						g.drawRect(oldX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldX > currentX && oldY > currentY) {
						g.drawRect(currentX, currentY,
								Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY < currentY) {
						g.drawRect(currentX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY > currentY) {
						g.drawRect(oldX, currentY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					}
				}
			}

		} else if (EditorGUI.currentTool == EditorGUI.CIRCLE) {
			((Graphics2D) g).setStroke(stroke);
			if (oldX != 0 && oldY != 0) {
				if (DrawSettingsToolbar.chckbxFillRectoval.isSelected()) {
					if (currentX > oldX && currentY > oldY) {
						g.fillOval(oldX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldX > currentX && oldY > currentY) {
						g.fillOval(currentX, currentY,
								Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY < currentY) {
						g.fillOval(currentX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY > currentY) {
						g.fillOval(oldX, currentY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					}
				} else {
					if (currentX > oldX && currentY > oldY) {
						g.drawOval(oldX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldX > currentX && oldY > currentY) {
						g.drawOval(currentX, currentY,
								Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY < currentY) {
						g.drawOval(currentX, oldY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					} else if (oldY > currentY) {
						g.drawOval(oldX, currentY, Math.abs(currentX - oldX),
								Math.abs(currentY - oldY));
					}
				}

			}
		} else if (EditorGUI.currentTool == EditorGUI.CROP) {
			((Graphics2D) g).draw(toCrop);
			if (oldX != 0 && oldY != 0) {
				if (oldX < currentX && oldY < currentY) {
					toCrop.setBounds(oldX, oldY, Math.abs(currentX - oldX),
							Math.abs(currentY - oldY));

				} else if (oldX > currentX && oldY > currentY) {
					toCrop.setBounds(currentX, currentY,
							Math.abs(currentX - oldX),
							Math.abs(currentY - oldY));

				} else if (oldY < currentY) {
					toCrop.setBounds(currentX, oldY, Math.abs(currentX - oldX),
							Math.abs(currentY - oldY));

				} else if (oldY > currentY) {
					toCrop.setBounds(oldX, currentY, Math.abs(currentX - oldX),
							Math.abs(currentY - oldY));
				}
			}
		} else if (EditorGUI.currentTool == EditorGUI.EMO) {
			if (Emoticons.currentEmo.getWidth() == 128) {
				g.drawImage(Emoticons.currentEmo, currentX - 64, currentY - 64,
						null);
			} else {
				g.drawImage(Emoticons.currentEmo, currentX - 24, currentY - 24,
						null);
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
		int returnVal = fcd.showSaveDialog(EditorGUI.frame);
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
				Cleaner.gc();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
