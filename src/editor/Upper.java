package editor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.border.MatteBorder;

public class Upper extends JToolBar {

	ActionListener copyAction = new ActionListener(new ImageIcon(
			Upper.class.getResource("/editor/tools/copy.png")), "copy");
	ActionListener faceAction = new ActionListener(new ImageIcon(
			Upper.class.getResource("/editor/tools/facebook.png")), "fb");
	ActionListener gplusAction = new ActionListener(new ImageIcon(
			Upper.class.getResource("/editor/tools/google.png")), "g+");
	ActionListener printAction = new ActionListener(new ImageIcon(
			Upper.class.getResource("/editor/tools/print.png")), "print");

	ActionListener redditAction = new ActionListener(new ImageIcon(
			Upper.class.getResource("/editor/tools/reddit.png")), "reddit");
	ActionListener saveAction = new ActionListener(new ImageIcon(
			Upper.class.getResource("/editor/tools/save.png")), "save");
	ActionListener twittAction = new ActionListener(new ImageIcon(
			Upper.class.getResource("/editor/tools/twitter.png")), "twitter");
	ActionListener undoAction = new ActionListener(new ImageIcon(
			Upper.class.getResource("/editor/tools/upload.png")), "upload");

	public Upper() {
		setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		setSize(460, 24);
		setLayout(null);
		setMinimumSize(new Dimension(460, 25));
		setPreferredSize(new Dimension(460, 25));
		final JButton saveBtn = new JButton("");
		saveBtn.setToolTipText("Save as");
		saveBtn.setFocusable(true);
		saveBtn.setBounds(12, 1, 22, 22);
		add(saveBtn);
		saveBtn.setAction(saveAction);

		JButton copyBtn = new JButton("");
		copyBtn.setToolTipText("Copy");
		copyBtn.setBounds(35, 1, 22, 22);
		copyBtn.setFocusable(false);
		add(copyBtn);
		copyBtn.setAction(copyAction);

		JButton printBtn = new JButton("");
		printBtn.setToolTipText("Print");
		printBtn.setBounds(58, 1, 22, 22);
		printBtn.setFocusable(false);
		add(printBtn);
		printBtn.setAction(printAction);

		JButton undoBtn = new JButton("");
		undoBtn.setToolTipText("Close & upload");

		undoBtn.setBounds(113, 1, 22, 22);
		undoBtn.setFocusable(false);
		add(undoBtn);
		undoBtn.setAction(undoAction);

		JButton facebook = new JButton("");
		facebook.setToolTipText("Close, upload & share on Facebook");
		facebook.setBounds(171, 1, 22, 22);
		facebook.setFocusable(false);
		add(facebook);
		facebook.setAction(faceAction);

		JButton twitter = new JButton("");
		twitter.setToolTipText("Close, upload & twitt");
		twitter.setBounds(194, 1, 22, 22);
		twitter.setFocusable(false);
		add(twitter);
		twitter.setAction(twittAction);

		JButton gplus = new JButton("");
		gplus.setToolTipText("Close, upload & share on Google+");
		gplus.setBounds(217, 1, 22, 22);
		gplus.setFocusable(false);
		add(gplus);
		gplus.setAction(gplusAction);

		JButton reddit = new JButton("");
		reddit.setToolTipText("Close, upload & share on Reddit");
		reddit.setBounds(240, 1, 22, 22);
		reddit.setFocusable(false);
		add(reddit);
		reddit.setAction(redditAction);

	}
}
