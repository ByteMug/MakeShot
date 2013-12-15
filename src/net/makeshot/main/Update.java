package net.makeshot.main;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;

import net.makeshot.logs.LogError;
import net.makeshot.settings.Static;

public class Update
  implements Runnable
{
  public static boolean auto;
  static File file = new File(System.getProperty("user.home") + 
    File.separator + ".MakeShot" + File.separator + "update.ini");
  private static String message = "There is a new version of MakeShot available. \r\nDo you want to update?";
  private static URL url;
  static final String VERSION = "1.1";
  
  @Override
public void run()
  {
    try
    {
      url = new URL("http://makeshot.net/version.txt");
      Scanner s = new Scanner(url.openStream());
      String version = s.nextLine();
      if (!version.equals("1.1"))
      {
        int dialogResult = JOptionPane.showConfirmDialog(null, message, 
          "MakeShot", 0);
        if (dialogResult == 0) {
          Runtime.getRuntime().exec(
            "java -jar " + Static.appDir.substring(1) + 
            "/update/update.dll " + "1.1");
        }
      }
      else if ((version.equals("1.1")) && (!auto))
      {
        JOptionPane.showMessageDialog(null, 
          "Your version is up to date");
      }
      s.close();
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog(null, "Cannot check for updates");
      LogError.get(e);
    }
  }
}
