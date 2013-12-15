package net.makeshot.imageEditor;

import java.awt.Image;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.makeshot.settings.Kit;

public class ClipboardImage implements ClipboardOwner {
	/*
	 * copy image to the clipboard
	 */
	private class TransferableImage implements Transferable {
		
		Image i;

		public TransferableImage(Image i) {
			this.i = i;
		}

		@Override
		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			if (flavor.equals(DataFlavor.imageFlavor) && i != null) {
				return i;
			} else {
				throw new UnsupportedFlavorException(flavor);
			}
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			DataFlavor[] flavors = new DataFlavor[1];
			flavors[0] = DataFlavor.imageFlavor;
			return flavors;
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			DataFlavor[] flavors = getTransferDataFlavors();
			for (int i = 0; i < flavors.length; i++) {
				if (flavor.equals(flavors[i])) {
					return true;
				}
			}

			return false;
		}
	}

	public ClipboardImage(BufferedImage i) {
		TransferableImage trans = new TransferableImage(i);
		Clipboard c = Kit.get().getSystemClipboard();
		c.setContents(trans, this);
	}

	@Override
	public void lostOwnership(Clipboard clip, Transferable trans) {
		
	}
}
