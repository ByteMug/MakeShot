package net.makeshot.upload;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.makeshot.ini.Reader;
import net.makeshot.logs.LogError;
import net.makeshot.main.Notifications;
import net.makeshot.settings.Static;
import net.makeshot.sound.Play;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Imgur {
	static Reader read = new Reader();

	private static void getLink(String xml, String type, String pathu) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(
					new ByteArrayInputStream(xml.getBytes("utf-8"))));
			NodeList nList = doc.getElementsByTagName("data");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == 1) {
					Element eElement = (Element) nNode;
					new ToMakeshot("http://i.imgur.com/"
							+ eElement.getElementsByTagName("id").item(0)
									.getTextContent() + ".png", type, pathu);
				}
			}
		} catch (Exception e) {
			if (Static.tooltip == 1) {
				Notifications.showNotification(false, "ops :(", pathu);
			}
			if (Static.playSound == 1) {
				Play.error();
			}
			LogError.get(e);
		}
	}

	public Imgur(String pathu, String type) {
		read = new Reader();
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("https://api.imgur.com/3/upload.xml");
		try {
			List<NameValuePair> nameValuePairs = new ArrayList();
			post.addHeader("Authorization", "Client-ID 8945a38e13b7591");
			String encod = Base64.encodeBytes(FileUtils
					.readFileToByteArray(new File(pathu)));
			nameValuePairs.add(new BasicNameValuePair("image", encod));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			while (rd.readLine() != null) {
				line = rd.readLine();
				getLink(line, type, pathu);
			}
		} catch (IOException e) {
			if (Static.tooltip == 1) {
				Notifications.showNotification(false, "ops :(", pathu);
			}
			if (Static.playSound == 1) {
				Play.error();
			}
			LogError.get(e);
		}
	}
}
