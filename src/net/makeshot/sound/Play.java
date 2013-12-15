package net.makeshot.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Play {
	private static URL path;
	private  static AudioInputStream audioIn;
	private  static AudioFormat format;
	private  static DataLine.Info info;
	private  static Clip clip;

	public static void success() {
		try {
			path = new URL("file://"
					+ ClassLoader.getSystemClassLoader().getResource(".")
							.getPath() + "success.wav");
			audioIn = AudioSystem.getAudioInputStream(path);
			format = audioIn.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void error() {
		try {
			path = new URL("file://"
					+ ClassLoader.getSystemClassLoader().getResource(".")
							.getPath() + "error.wav");
			audioIn = AudioSystem.getAudioInputStream(path);
			format = audioIn.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}
}
