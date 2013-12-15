package net.makeshot.upload;

import java.awt.Desktop;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import net.makeshot.ini.Reader;
import net.makeshot.logs.LogError;
import net.makeshot.logs.Logging;
import net.makeshot.main.LinksList;
import net.makeshot.main.Notifications;
import net.makeshot.settings.Kit;
import net.makeshot.settings.Static;
import net.makeshot.sound.Play;

public class ToMakeshot {
	public static String getUrl(String addFileResponse) {
		if (addFileResponse.contains("success")) {
			String result2 = addFileResponse.substring(
					addFileResponse.lastIndexOf("|") + 1).trim();
			return result2;
		}
		return "error";
	}

	Reader read = new Reader();

	public ToMakeshot(String path, String type, String pathu) {
		String urlParameters = "name=" + path + "&mobo=" + Static.mobo;
		try {
			URL url = new URL("http://makeshot.net/upload/");

			URLConnection conn = url.openConnection();

			conn.setDoOutput(true);

			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream());

			writer.write(urlParameters);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				Logging.logger.info(line);
				String url2 = getUrl(line);
				if (url2.contains("makeshot")) {
					if (this.read.settings("sound").equals("1")) {
						Play.success();
					}
					if (this.read.settings("copyLink").equals("1")) {
						Kit.get().getSystemClipboard()
								.setContents(new StringSelection(url2), null);
					}
					if (this.read.settings("tooltip").equals("1")) {
						Notifications.showNotification(true, url2, path);
					}
					LinksList.addLink(url2);
					if (type.equals("reddit")) {
						openWWW("http://www.reddit.com/submit?url=" + url2);
					} else if (type.equals("facebook")) {
						openWWW("https://www.facebook.com/sharer/sharer.php?u="
								+ url2);
					} else if (type.equals("twitter")) {
						openWWW("https://twitter.com/intent/tweet?text=Take%20a%20look!&url="
								+ url2);
					} else if (type.equals("google")) {
						openWWW("https://plus.google.com/share?url=" + url2);
					}
				} else {
					if (this.read.settings("tooltip").equals("1")) {
						Notifications.showNotification(false, "ops :(", path);
					}
					if (this.read.settings("sound").equals("1")) {
						Play.error();
					}
				}
			}
			writer.close();
			reader.close();
			Thread.currentThread().interrupt();
			return;
		} catch (Exception e) {
			if (this.read.settings("tooltip").equals("1")) {
				Notifications.showNotification(false, "ops :(", path);
			}
			if (this.read.settings("sound").equals("1")) {
				Play.error();
			}
			LogError.get(e);
		}
	}

	private void openWWW(String link) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop()
				: null;
		if ((desktop != null) && (desktop.isSupported(Desktop.Action.BROWSE))) {
			try {
				desktop.browse(URI.create(link));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
