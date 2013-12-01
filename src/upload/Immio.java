package upload;

import ini.Reader;

import java.io.File;
import java.net.URI;

import logs.LogError;
import makeshot.Notifications;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import sound.Play;

public class Immio {
	Reader read = new Reader();

	Immio(String pathu, String type) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			URI url = new URI("http://imm.io/store/");
			HttpPost httppost = new HttpPost(url);
			FileBody bin = new FileBody(new File(pathu));
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("image", bin);
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity responseEntity = response.getEntity();
			String answer;
			if (responseEntity != null) {
				answer = EntityUtils.toString(responseEntity);
				parse(answer, type, pathu);
			} else {
				if (settings.Static.tooltip == 1)
					Notifications.showNotification(false, "ops :(", pathu);
				if (settings.Static.playSound == 1)
					Play.error();
			}

		} catch (Exception e) {
			if (settings.Static.tooltip == 1)
				Notifications.showNotification(false, "ops :(", pathu);
			if (settings.Static.playSound == 1)
				Play.error();
			LogError.get(e);
		}
	}

	private void parse(String s, String type, String pathu) {
		new ToMakeshot(new JSONObject(s.substring(26).replaceAll("}}", "}"))
				.get("uri").toString(), type, pathu);
	}
}
