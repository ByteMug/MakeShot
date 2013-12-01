package makeshot;

import ini.Writer;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import logs.LogError;
import logs.Logging;
import org.jnativehook.GlobalScreen;
import printScreen.Area;
import printScreen.FullScreen;
import settings.Fix;
import settings.Kit;
import settings.Settings;
import settings.Static;

public class Tray
{
  public static TrayIcon trayIcon;
  private static MenuItem windowItem;
  private static MenuItem openSettings;
  private static MenuItem areaShot;
  private static MenuItem fullShot;
  private static MenuItem hotkeysOff;
  private static MenuItem aboutItem;
  private static MenuItem exitItem;
  
  public static void main(String[] args)
  {
    try
    {
      if (new RandomAccessFile(System.getProperty("java.io.tmpdir") + 
        "makeshot.tmp", "rw").getChannel().tryLock() == null)
      {
        JOptionPane.showMessageDialog(null, "App is already running!");
      }
      else
      {
        Writer.firstRun();
        new Tray().create();
        new Fix();
        
        LinksList.importList();
      }
    }
    catch (Exception e)
    {
      LogError.get(e);
    }
  }
  
  Image image = Kit.get().getImage(getClass().getResource("MakeShot.png"));
  private final PopupMenu popup = new PopupMenu();
  Settings settings;
  private final SystemTray tray = SystemTray.getSystemTray();
  UserWindow usr;
  
  private void create()
  {
    
    try
    {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    }
    catch (Exception e1)
    {
      LogError.get(e1);
    }
    if (SystemTray.isSupported())
    {
      windowItem = new MenuItem("Show");
      openSettings = new MenuItem("Settings");
      areaShot = new MenuItem("Take area screenshot");
      fullShot = new MenuItem("Take screenshot");
      hotkeysOff = new MenuItem("Turn hotkeys off");
      aboutItem = new MenuItem("About");
      exitItem = new MenuItem("Exit");
      this.popup.add(windowItem);
      this.popup.add(openSettings);
      this.popup.addSeparator();
      this.popup.add(areaShot);
      this.popup.add(fullShot);
      this.popup.add(hotkeysOff);
      
      this.popup.add(aboutItem);
      this.popup.add(exitItem);
      trayIcon = new TrayIcon(Icon.get(), "MakeShot", this.popup);
      trayIcon.setImageAutoSize(true);
      trayIcon.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e2)
        {
          if (Tray.this.usr == null) {
            Tray.this.usr = new UserWindow();
          }
          if (Tray.this.usr.frame.isVisible()) {
            Tray.this.usr.frame.toFront();
          } else if (!Tray.this.usr.frame.isVisible()) {
            Tray.this.usr.frame.setVisible(true);
          }
        }
      });
      exitItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          GlobalScreen.unregisterNativeHook();
          System.exit(0);
        }
      });
      fullShot.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          new FullScreen();
        }
      });
      areaShot.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          HotKeyListener.asd.create();
        }
      });
      openSettings.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (Tray.this.settings == null) {
            Tray.this.settings = new Settings();
          }
          if (Settings.frame.isVisible()) {
            Settings.frame.toFront();
          } else if (!Settings.frame.isVisible()) {
            Settings.frame.setVisible(true);
          }
        }
      });
      windowItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (Tray.this.usr == null) {
            Tray.this.usr = new UserWindow();
          }
          if (Tray.this.usr.frame.isVisible()) {
            Tray.this.usr.frame.toFront();
          } else if (!Tray.this.usr.frame.isVisible()) {
            Tray.this.usr.frame.setVisible(true);
          }
        }
      });
      aboutItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          new About();
        }
      });
      hotkeysOff.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (Tray.hotkeysOff.getLabel().equals("Turn hotkeys off"))
          {
            Static.hotkeys = 2;
            Tray.hotkeysOff.setLabel("Turn hotkeys on");
          }
          else if (Tray.hotkeysOff.getLabel().equals("Turn hotkeys on"))
          {
            Static.hotkeys = 1;
            Tray.hotkeysOff.setLabel("Turn hotkeys off");
          }
        }
      });
      try
      {
        this.tray.add(trayIcon);
      }
      catch (AWTException e)
      {
        LogError.get(e);
      }
    }
    else
    {
      Logging.logger.info("Tray unavailable");
    }
  }
}
