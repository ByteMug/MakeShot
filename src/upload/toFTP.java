package upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import makeshot.Notifications;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import settings.Static;
import sound.Play;

/**
 * A program that demonstrates how to upload files from local computer to a
 * remote FTP server using Apache Commons Net API.
 * 
 * @author www.codejava.net
 */
public class toFTP {

	public toFTP(String file, String type) {
		String server = Static.ftpServ;
		int port = Static.ftpPort;
		String user = Static.ftpLogin;
		String pass = Static.ftpPass;

		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			// APPROACH #1: uploads first file using an InputStream
			File firstLocalFile = new File(file);

			String firstRemoteFile = Static.ftpDir + "/"
					+ firstLocalFile.getName();
			InputStream inputStream = new FileInputStream(firstLocalFile);

			boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
			inputStream.close();
			if (done) {
				new ToMakeshot(Static.ftpUrl + "/" + firstLocalFile.getName(),
						type, file);
			}

		} catch (IOException ex) {
			if (settings.Static.tooltip == 1)
				Notifications.showNotification(false, "FTP", file);
			if (settings.Static.playSound == 1)
				Play.error();
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();

			}
		}
	}

}
