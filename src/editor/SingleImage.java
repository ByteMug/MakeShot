package editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import logs.LogError;
import settings.Static;
import upload.Start;

public class SingleImage {

	public SingleImage(String imagePath) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
		Date date = new Date();
		BufferedImage image = null;
		try {
			if (settings.Static.saveSs == 1) {

				String current = settings.Static.ssDirectory + "/"
						+ dateFormat.format(date) + "." + settings.Static.ext;
				image = ImageIO.read(new File(imagePath));
				ImageIO.write(image, settings.Static.ext, new File(current));

				if (settings.Static.editSs == 1) {
					new Paint(current);
				} else {
					Runnable r = new Start(current, "");
					new Thread(r).start();
				}

			} else {
				image = ImageIO.read(new File(imagePath));
				File file = new File(Static.ssDirectory + "/temp"
						+ "/tempfile." + settings.Static.ext);
				ImageIO.write(image, settings.Static.ext, file);

				if (settings.Static.editSs == 1) {
					new Paint(file.toString());
				} else {
					Runnable r = new Start(file.toString(), "");
					new Thread(r).start();

					file.delete();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid image", "File",
					JOptionPane.ERROR_MESSAGE);
			LogError.get(e);
		}
	}

}
