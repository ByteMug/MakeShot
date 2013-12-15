package net.makeshot.main;

import net.makeshot.logs.LogError;
import net.makeshot.printScreen.Area;
import net.makeshot.printScreen.FullScreen;
import net.makeshot.settings.HotKeys;
import net.makeshot.settings.Static;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class HotKeyListener implements NativeKeyListener {
	static Area areaPrintScreen = new Area();

	public static void register() {
		if (Static.hotkeys == 1) {
			try {
				GlobalScreen.registerNativeHook();
			} catch (NativeHookException ex) {
				LogError.get(ex);
				System.exit(1);
			}
			GlobalScreen.getInstance().addNativeKeyListener(
					new HotKeyListener());
		} else if (GlobalScreen.isNativeHookRegistered()) {
			GlobalScreen.unregisterNativeHook();
		}
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		if (((HotKeys.panel == null) || (!HotKeys.panel.isShowing()))
				&& (Static.hotkeys == 1)) {
			if (e.getKeyCode() == Static.fullHotkey) {
				new FullScreen();
			} else if (e.getKeyCode() == Static.areaHotkey) {
				areaPrintScreen.create();
			} else if ((e.getKeyCode() == 27) && (areaPrintScreen.isVisible)) {
				areaPrintScreen.dispose(2);
			}
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}
}
