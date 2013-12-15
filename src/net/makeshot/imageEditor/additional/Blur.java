package net.makeshot.imageEditor.additional;

/*
 ** Copyright 2005 Huxtable.com. All rights reserved.
 */
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;

import net.makeshot.imageEditor.DrawSettingsToolbar;
import net.makeshot.imageEditor.EditorGUI;

public class Blur {
	static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static void dupa(BufferedImage ss, int x, int y, int w, int h) {
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		if (x + w > ss.getWidth()) {
			w += ss.getWidth() - (x + w);
		}
		if (y + h > ss.getHeight()) {
			h += ss.getHeight() - (y + h);
		}
		BufferedImage bufferedImage = ss.getSubimage(x, y, w, h);

		Kernel kernel = new Kernel(3, 3, new float[] { 0.111f, 0.111f, 0.111f,
				0.111f, 0.111f, 0.111f, 0.111f, 0.111f, 0.111f });
		BufferedImageOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		for (int i = 0; i < Integer.valueOf((String) DrawSettingsToolbar.sizeBox
				.getSelectedItem()) + 2; i++) {
			bufferedImage = op.filter(bufferedImage, null);

		}
		EditorGUI.drawPanel.asd(bufferedImage, x, y, w, h);

	}

	public static BufferedImage pixelate(BufferedImage im, int x, int y, int w,
			int h, int block) {
		int width = im.getWidth();
		int height = im.getHeight();
		int hw = w >> 1;
		int hh = h >> 1;
		int[] pixels = im.getRGB(0, 0, width, height, null, 0, width);
		int[] temp = pixels.clone();
		int block2 = block * block;

		for (int yy = -hh; yy < hh; yy += block) {
			for (int xx = -hw; xx < hw; xx += block) {
				int r = 0;
				int g = 0;
				int b = 0;
				for (int i = 0; i < block; i++) {
					int yd = yy + y + i;
					if (yd < 0 || yd >= height)
						continue;
					for (int j = 0; j < block; j++) {
						int xd = xx + x + j;
						if (xd < 0 || xd >= width)
							continue;
						r += (pixels[yd * width + xd] >> 16) & 0xff;
						g += (pixels[yd * width + xd] >> 8) & 0xff;
						b += pixels[yd * width + xd] & 0xff;
					}
				}
				if (yy + y < 0 || yy + y >= height || xx + x < 0
						|| xx + x >= width)
					continue;
				int col = ((r / block2) << 16) | ((g / block2) << 8)
						| (b / block2);

				for (int i = 0; i < block; i++) {
					int yd = yy + y + i;
					if (yd < 0 || yd >= height)
						continue;
					for (int j = 0; j < block; j++) {
						int xd = xx + x + j;
						if (xd < 0 || xd >= width)
							continue;
						temp[yd * width + xd] = col;
					}
				}
			}
		}

		im.setRGB(0, 0, width, height, temp, 0, width);

		return im;
	}
}