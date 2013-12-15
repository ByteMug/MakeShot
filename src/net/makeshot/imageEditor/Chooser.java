package net.makeshot.imageEditor;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.makeshot.logs.LogError;
import net.makeshot.main.Icon;
import net.makeshot.main.WindowsLF;

public class Chooser extends JPanel implements ChangeListener {

	static Color color = Color.RED;
	public static JFrame frame = new JFrame("Choose color");
	public static boolean isOn = false;

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	public static void createAndShowGUI() {
		// Create and set up the window.
		isOn = true;
		frame.setIconImage(Icon.get());
		frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		frame.setResizable(false);
		// Create and set up the content pane.
		JComponent newContentPane = new Chooser();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static Color getColor() {
		return color;
	}

	protected JColorChooser tcc;

	public Chooser() {
		super(new BorderLayout());
		WindowsLF.apply();
		tcc = new JColorChooser(Color.RED);
		tcc.getSelectionModel().addChangeListener(this);
		tcc.setBorder(BorderFactory.createTitledBorder("Choose color"));

		add(tcc, BorderLayout.PAGE_END);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		color = tcc.getColor();
		DrawSettingsToolbar.color.setBackground(tcc.getColor());
		}

}
