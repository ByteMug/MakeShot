package net.makeshot.imageEditor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import net.makeshot.logs.LOG;
import net.makeshot.settings.Static;
import net.makeshot.upload.Start;

public class SingleImage {

	public SingleImage(String imagePath) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
		Date date = new Date();
		BufferedImage image = null;
		try {
			if (net.makeshot.settings.Static.saveSs == 1) {

				String current = net.makeshot.settings.Static.ssDirectory + "/"
						+ dateFormat.format(date) + "."
						+ net.makeshot.settings.Static.ext;
				image = ImageIO.read(new File(imagePath));

				ImageIO.write(image, net.makeshot.settings.Static.ext,
						new File(current));

				if (net.makeshot.settings.Static.editSs == 1) {
					new EditorGUI(current);
				} else {
					Runnable r = new Start(current, "");
					new Thread(r).start();
				}

			} else {
				image = ImageIO.read(new File(imagePath));
				File file = new File(Static.ssDirectory + "/temp"
						+ "/tempfile." + net.makeshot.settings.Static.ext);
				ImageIO.write(image, net.makeshot.settings.Static.ext, file);

				if (net.makeshot.settings.Static.editSs == 1) {
					new EditorGUI(file.toString());
				} else {
					Runnable r = new Start(file.toString(), "");
					new Thread(r).start();

					file.delete();
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Invalid image", "File",
					JOptionPane.ERROR_MESSAGE);
			LOG.error(e);
		}

	}

}
