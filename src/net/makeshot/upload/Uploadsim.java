package net.makeshot.upload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.makeshot.ini.Reader;
import net.makeshot.logs.LOG;
import net.makeshot.main.Notifications;
import net.makeshot.sound.Play;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Uploadsim {
	Reader read = new Reader();

	Uploadsim(String pathu, String type) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			URI url = new URI("http://uploads.im/api?format=xml");
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
				getLink(answer, type, pathu);
			} else {
				if (net.makeshot.settings.Static.tooltip == 1)
					Notifications.showNotification(false, "ops :(", pathu);
				if (net.makeshot.settings.Static.playSound == 1)
					Play.error();
			}

		} catch (URISyntaxException | ParseException | IOException e) {
			if (net.makeshot.settings.Static.tooltip == 1)
				Notifications.showNotification(false, "ops :(", pathu);
			if (net.makeshot.settings.Static.playSound == 1)
				Play.error();
			LOG.error(e);
		}
	}

	private static void getLink(String xml, String type, String pathu) {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			// parse XML and obtain link
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(
					new ByteArrayInputStream(xml.getBytes("utf-8"))));
			NodeList nList = doc.getElementsByTagName("data");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					new ToMakeshot(eElement.getElementsByTagName("img_url")
							.item(0).getTextContent(), type, pathu);
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			if (net.makeshot.settings.Static.tooltip == 1)
				Notifications.showNotification(false, "ops :(", pathu);
			if (net.makeshot.settings.Static.playSound == 1)
				Play.error();
			LOG.error(e);
		}

	}
}
