package fastScreen;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.StringSelection;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class LinksList extends JScrollPane {
	static DefaultListModel listModel = new DefaultListModel();
	JList list = new JList(listModel);

	public Component create() {
		setBounds(10, 85, 214, 160);
		setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(listModel);
		list.setLayoutOrientation(JList.VERTICAL);
		return this;
	}

	public static void addLink(String url) {
		listModel.add(0, url);

	}

	public void copyLink() {
		try {
			Toolkit.getDefaultToolkit()
					.getSystemClipboard()
					.setContents(
							new StringSelection(list.getSelectedValue()
									.toString()), null);
		} catch (NullPointerException e) {
			System.out.println("Choose something");
		}
	}
}
