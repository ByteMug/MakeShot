package fastScreen;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class hello {
	public static void main(String[] args) {
		BufferedImage image = null;
		try {
			Dimension dim = new Dimension(1700, 100);
			image = new Robot().createScreenCapture(new Rectangle(90, 100, 150, 150));
			ImageIO.write(image, "png", new File("C:/screenshotsz/1lol.png"));
			System.out.println(Toolkit
					.getDefaultToolkit().getScreenSize());
		} catch (RuntimeException | IOException | AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		Window window = keyboardFocusManager.getActiveWindow();
		System.out.println(window.getAlignmentX());
	}
}
