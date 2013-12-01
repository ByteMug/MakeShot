package editor;

import ini.Reader;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import upload.Imgur;

public class zPanel extends JPanel implements MouseListener,
		MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BufferedImage image, tempImage;
	Graphics2D gz;
	static boolean brush, addText;
	int oldX, oldY, currentX, currentY;
	static String p;
	Image img;

	public zPanel(String path) {
		Reader reader = new Reader();
		reader.settings("save");
		p = path;
		setBackground(Color.WHITE);
		try {
			img = ImageIO.read(new File(path));
			image = new BufferedImage(img.getWidth(this), img.getHeight(this),
					BufferedImage.TYPE_INT_ARGB);
			tempImage = new BufferedImage(img.getWidth(this),
					img.getHeight(this), BufferedImage.TYPE_INT_ARGB);
			gz = image.createGraphics();
			gz.drawImage(img, 0, 0, this);
			gz.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			setPreferredSize(new Dimension(img.getWidth(this),
					img.getHeight(this)));

			Paint.okBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Paint.frame.dispose();
					try {
						File file = new File(ClassLoader.getSystemClassLoader()
								.getResource(".").getPath()
								+ "/temp"
								+ "/tempfile"
								+ new Reader().settings("ext"));
						ImageIO.write(image, new Reader().settings("ext"), file);
						new Imgur(file.toString());
						file.delete();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
			Paint.clearBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					gz.clearRect(0, 0, getWidth(), getHeight());
					gz.drawImage(img, 0, 0, null);
					repaint();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addMouseListener(this);
		addMouseMotionListener(this);
		repaint();
	}

	int curr, currY;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
		if (brush) {
			color = (String) Paint.colorBox.getSelectedItem();

			((Graphics2D) g).setStroke(new BasicStroke(Integer
					.valueOf(Paint.spinner.getValue().toString()) / 2, 1, 1));

			if (color.equals("Black")) {
				g.setColor(Color.black);
			} else if (color.equals("White")) {
				g.setColor(Color.white);
			} else if (color.equals("Red")) {
				g.setColor(Color.red);
			} else if (color.equals("Green")) {
				g.setColor(Color.green);
			} else if (color.equals("Blue")) {
				g.setColor(Color.blue);
			} else if (color.equals("Orange")) {
				g.setColor(Color.orange);
			}
			g.drawLine(curr, currY, curr, currY);
		} else if (addText) {
			Font font = new Font("Serif", Font.PLAIN,
					Integer.valueOf(Paint.spinner.getValue().toString()));
			g.setFont(font);

			color = (String) Paint.colorBox.getSelectedItem();

			if (color.equals("Black")) {
				g.setColor(Color.black);
			} else if (color.equals("White")) {
				g.setColor(Color.white);
			} else if (color.equals("Red")) {
				g.setColor(Color.red);
			} else if (color.equals("Green")) {
				g.setColor(Color.green);
			} else if (color.equals("Blue")) {
				g.setColor(Color.blue);
			} else if (color.equals("Orange")) {
				g.setColor(Color.orange);
			}

			g.drawString(Paint.textField.getText(), curr, currY);
		}
	}

	String color;

	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println(brush);
		if (brush) {
			currentX = e.getX();
			currentY = e.getY();
			color = (String) Paint.colorBox.getSelectedItem();
			gz.setStroke(new BasicStroke(Integer.valueOf(Paint.spinner
					.getValue().toString()) / 2, 1, 1));

			if (color.equals("Black")) {
				gz.setColor(Color.black);
			} else if (color.equals("White")) {
				gz.setColor(Color.white);
			} else if (color.equals("Red")) {
				gz.setColor(Color.red);
			} else if (color.equals("Green")) {
				gz.setColor(Color.green);
			} else if (color.equals("Blue")) {
				gz.setColor(Color.blue);
			} else if (color.equals("Orange")) {
				gz.setColor(Color.orange);
			}

			gz.drawLine(oldX, oldY, currentX, currentY);
			repaint();
			oldX = currentX;
			oldY = currentY;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		curr = e.getX();
		currY = e.getY();
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

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
	public void mousePressed(MouseEvent e) {
		oldX = e.getX();
		oldY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (addText) {
			Font font = new Font("Serif", Font.PLAIN,
					Integer.valueOf(Paint.spinner.getValue().toString()));
			gz.setFont(font);

			color = (String) Paint.colorBox.getSelectedItem();

			if (color.equals("Black")) {
				gz.setColor(Color.black);
			} else if (color.equals("White")) {
				gz.setColor(Color.white);
			} else if (color.equals("Red")) {
				gz.setColor(Color.red);
			} else if (color.equals("Green")) {
				gz.setColor(Color.green);
			} else if (color.equals("Blue")) {
				gz.setColor(Color.blue);
			} else if (color.equals("Orange")) {
				gz.setColor(Color.orange);
			}

			gz.drawString(Paint.textField.getText(), arg0.getX(), arg0.getY());
			repaint();
		}
	}
}
