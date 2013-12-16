package net.makeshot.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.makeshot.logs.LOG;

public class WindowsLF {
	public static void apply() {

		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			LOG.error(e);
		}

	}
}
