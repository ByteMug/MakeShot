package fastScreen;

import java.awt.TrayIcon;
import java.awt.event.KeyEvent;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import printScreen.Area;

//import xml.Reader;
//import xml.Writer;

public class HotKeyListener implements NativeKeyListener {
	public void nativeKeyPressed(NativeKeyEvent e) {
	}

	Area asd = new Area();

	public void nativeKeyReleased(NativeKeyEvent e) {
		if (e.getKeyCode() == new ini.Reader().hotkeys("fullScreen")) {
			System.out.println("FULL SCREEN PHOTO TAKIN'");
			new printScreen.FullScreen();
		} else if (e.getKeyCode() == new ini.Reader().hotkeys("area")) {
			System.out.println("AREA PHOTO TAKIN'");
			asd.create();
			asd.isVisible = 1;
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && asd.isVisible == 1) {
			asd.dispos2e();
		}
	}

	public void nativeKeyTyped(NativeKeyEvent e) {
	}

	public static void main(String[] args) {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.out
					.println("There was a problem registering the native hook.");
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		GlobalScreen.getInstance().addNativeKeyListener(new HotKeyListener());
	}
}