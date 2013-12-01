package makeshot;

import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import logs.LogError;
import settings.Kit;
import settings.Static;

public class LinksList extends JScrollPane {
	static File listFile = new File(System.getProperty("user.home")
			+ File.separator + ".MakeShot" + File.separator + "/links list.txt");
	static DefaultListModel<String> model = new DefaultListModel();
	static JList<String> list = new JList(model);
	String imgurUrl;
	ToolTipPanel panel;

	public static void addLink(String url) {
		model.add(0, url);
		exportList();
	}

	public static void deleteImage() {
		try {
			URL url = new URL("http://makeshot.net/delimg.php");

			URLConnection conn = url.openConnection();

			conn.setDoOutput(true);
			String link = ((String) list.getSelectedValue()).substring(22);
			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream());

			writer.write("url=" + link + "&mobo=" + Static.mobo);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {

				System.out.println(line);
				if (line.contains("Success")) {
					model.removeElementAt(list.getSelectedIndex());
					exportList();
				} else {
					JOptionPane.showMessageDialog(null, "Deleting error",
							"Oops!", 0);
				}
			}
		} catch (Exception e) {
			LogError.get(e);
		}
	}

	public static void exportList() {
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(listFile), "UTF-8"));
			try {
				int len = model.getSize();
				for (int i = 0; i < len; i++) {
					pw.println(((String) model.get(i)).toString());
				}
			} finally {
				pw.close();
			}
		} catch (Exception e) {
			LogError.get(e);
		}
	}

	public static void importList() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			if (!listFile.exists()) {
				listFile.createNewFile();
			}
			archivo = listFile;
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {

				model.addElement(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public void copyLink() {
		try {
			Kit.get()
					.getSystemClipboard()
					.setContents(
							new StringSelection(
									((String) list.getSelectedValue())
											.toString()), null);
		} catch (NullPointerException localNullPointerException) {
		}
	}

	public Component create() {
		setBounds(10, 85, 214, 160);
		setViewportView(list);
		list.setSelectionMode(0);
		list.setModel(model);
		list.setLayoutOrientation(0);
		this.panel = new ToolTipPanel();
		thumbOnClick();
		return this;
	}

	public void getImage(String link) {
		try {
			URL url = new URL("http://makeshot.net/getimg.php");

			URLConnection conn = url.openConnection();

			conn.setDoOutput(true);

			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream());

			writer.write("img=" + link);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while (reader.readLine() != null) {
				String line = reader.readLine();
				this.imgurUrl = line;
			}
		} catch (Exception localException) {
		}
	}

	public void removeLink() {
		model.removeElementAt(list.getSelectedIndex());
		exportList();
	}

	public void thumbOnClick() {
		list.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Desktop desktop = Desktop.isDesktopSupported() ? Desktop
							.getDesktop() : null;
					if ((desktop != null)
							&& (desktop.isSupported(Desktop.Action.BROWSE))) {
						try {
							desktop.browse(URI.create(((String) LinksList.list
									.getSelectedValue()).toString()));
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
				if (LinksList.list.getSelectedIndex() >= 0) {
					String link = ((String) LinksList.list.getSelectedValue())
							.substring(22);
					LinksList.this.getImage(link);
					LinksList.this.panel.showPanel(LinksList.list.getParent()
							.getLocationOnScreen().x, LinksList.list
							.getParent().getLocationOnScreen().y,
							LinksList.this.imgurUrl);
				}
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent m) {
				LinksList.this.panel.exit();
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		});
	}
}
