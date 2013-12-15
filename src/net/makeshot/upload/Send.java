package net.makeshot.upload;

import java.io.File;
import java.net.URI;

import net.makeshot.main.Notifications;
import net.makeshot.settings.Static;
import net.makeshot.sound.Play;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Send {
	public void doSend(String pathu, String type) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			URI url = new URI("http://szinek.2ap.pl/uply.php");
			HttpPost httppost = new HttpPost(url);

			FileBody bin = new FileBody(new File(pathu));
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("file_name", bin);
			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				String answer = EntityUtils.toString(responseEntity);
				new ToMakeshot(answer, type, pathu);
			} else {
				error(pathu);
			}
		} catch (Exception e) {
			error(pathu);
		}
	}

	private void error(String pathu) {
		if (Static.tooltip == 1) {
			Notifications.showNotification(false, "ops :(", pathu);
		}
		if (Static.playSound == 1) {
			Play.error();
		}
	}
}
