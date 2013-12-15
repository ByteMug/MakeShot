package makeshot;

import javax.swing.UIManager;

import net.makeshot.logs.LogError;

public class WindowsLF {
	public static void apply() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			LogError.get(e);
		}
	}
}
