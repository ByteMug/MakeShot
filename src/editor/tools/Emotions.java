package editor.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import editor.Paint;
import editor.Toolbox;

public class Emotions implements ActionListener {
	public static final ImageIcon angry = new ImageIcon(
			Toolbox.class.getResource("/editor/tools/emos/angry.png")),
			blush = new ImageIcon(
					Toolbox.class.getResource("/editor/tools/emos/blush.png")),
			cry = new ImageIcon(
					Toolbox.class.getResource("/editor/tools/emos/cry.png")),
			eye = new ImageIcon(
					Toolbox.class.getResource("/editor/tools/emos/eye.png")),
			happy = new ImageIcon(
					Toolbox.class.getResource("/editor/tools/emos/happy.png")),
			macho = new ImageIcon(
					Toolbox.class.getResource("/editor/tools/emos/macho.png")),
			sad = new ImageIcon(
					Toolbox.class.getResource("/editor/tools/emos/sad.png")),
			smile = new ImageIcon(
					Toolbox.class.getResource("/editor/tools/emos/smile.png")),
			tongue = new ImageIcon(
					Toolbox.class.getResource("/editor/tools/emos/tongue.png")),
			wow = new ImageIcon(
					Toolbox.class.getResource("/editor/tools/emos/wow.png"));

	public static final JMenuItem angry32 = new JMenuItem("Angry", angry),
			blush32 = new JMenuItem("Blush", blush), cry32 = new JMenuItem(
					"Cry", cry), eye32 = new JMenuItem("Wink", eye),
			happy32 = new JMenuItem("Happy", happy), macho32 = new JMenuItem(
					"Macho", macho), sad32 = new JMenuItem("Sad", sad),
			smile32 = new JMenuItem("Smile", smile), tongue32 = new JMenuItem(
					"Tongue", tongue), wow32 = new JMenuItem("Wow", wow),
			angry64 = new JMenuItem("Angry", angry), blush64 = new JMenuItem(
					"Blush", blush), cry64 = new JMenuItem("Cry", cry),
			eye64 = new JMenuItem("Wink", eye), happy64 = new JMenuItem(
					"Happy", happy), macho64 = new JMenuItem("Macho", macho),
			sad64 = new JMenuItem("Sad", sad), smile64 = new JMenuItem("Smile",
					smile), tongue64 = new JMenuItem("Tongue", tongue),
			wow64 = new JMenuItem("Wow", wow);

	public static JPopupMenu asd = new JPopupMenu();
	public static JMenu big = new JMenu("Big");
	public static BufferedImage currentEmo;
	public static JMenu small = new JMenu("Small");

	public Emotions() {
		angry32.addActionListener(this);
		blush32.addActionListener(this);
		cry32.addActionListener(this);
		eye32.addActionListener(this);
		happy32.addActionListener(this);
		macho32.addActionListener(this);
		smile32.addActionListener(this);
		tongue32.addActionListener(this);
		sad32.addActionListener(this);
		wow32.addActionListener(this);

		angry64.addActionListener(this);
		blush64.addActionListener(this);
		cry64.addActionListener(this);
		eye64.addActionListener(this);
		happy64.addActionListener(this);
		macho64.addActionListener(this);
		smile64.addActionListener(this);
		sad64.addActionListener(this);
		wow64.addActionListener(this);
		tongue64.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Paint.currentTool = Paint.EMO;
		try {
			if (e.getSource() == angry32) {

				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/angry32.png"));

			} else if (e.getSource() == blush32) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/blush32.png"));
			} else if (e.getSource() == cry32) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/cry32.png"));
			} else if (e.getSource() == eye32) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/eye32.png"));
			} else if (e.getSource() == happy32) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/happy32.png"));
			} else if (e.getSource() == macho32) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/macho32.png"));
			} else if (e.getSource() == smile32) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/smile32.png"));
			} else if (e.getSource() == tongue32) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/tongue32.png"));
			} else if (e.getSource() == sad32) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/sad32.png"));
			} else if (e.getSource() == wow32) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/wow32.png"));
			}
			//
			//
			//
			else if (e.getSource() == angry64) {

				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/angry64.png"));

			} else if (e.getSource() == blush64) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/blush64.png"));
			} else if (e.getSource() == cry64) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/cry64.png"));
			} else if (e.getSource() == eye64) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/eye64.png"));
			} else if (e.getSource() == happy64) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/happy64.png"));
			} else if (e.getSource() == macho64) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/macho64.png"));
			} else if (e.getSource() == smile64) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/smile64.png"));
			} else if (e.getSource() == tongue64) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/tongue64.png"));
			} else if (e.getSource() == sad64) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/sad64.png"));
			} else if (e.getSource() == wow64) {
				currentEmo = ImageIO.read(Toolbox.class
						.getResource("/editor/tools/emos/wow64.png"));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
