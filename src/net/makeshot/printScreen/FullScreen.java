package net.makeshot.printScreen;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import net.makeshot.imageEditor.EditorGUI;
import net.makeshot.logs.LogError;
import net.makeshot.logs.Logging;
import net.makeshot.settings.Fix;
import net.makeshot.settings.Kit;
import net.makeshot.settings.Static;
import net.makeshot.sound.Play;
import net.makeshot.upload.Start;
import makeshot.ini.Reader;

public class FullScreen
{
  BufferedImage image;
  Runnable r;
  Reader read = new Reader();
  
  public FullScreen()
  {
    Fix.ssDir();
    Dimension screenSize = Kit.get().getScreenSize();
    Rectangle screen = new Rectangle(screenSize);
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
    Date date = new Date();
    BufferedImage image = null;
    try
    {
      if (Static.saveSs == 1)
      {
        BufferedImage image2 = new Robot().createScreenCapture(screen);
        image = new BufferedImage(image2.getWidth(), 
          image2.getHeight(), 5);
        String current = Static.ssDirectory + "/" + 
          dateFormat.format(date) + "." + Static.ext;
        image = image2;
        ImageIO.write(image, Static.ext, new File(current));
        if (Static.editSs == 1)
        {
          new EditorGUI(current);
        }
        else
        {
          this.r = new Start(current, "");
          new Thread(this.r).start();
          
          Logging.logger.info("Upload started");
        }
      }
      else
      {
        image = new Robot().createScreenCapture(screen);
        File file = new File(Static.ssDirectory + "/temp" + 
          "/tempfile." + Static.ext);
        ImageIO.write(image, Static.ext, file);
        if (Static.editSs == 1)
        {
          new EditorGUI(file.toString());
        }
        else
        {
          this.r = new Start(file.toString(), "");
          new Thread(this.r).start();
          Logging.logger.info("Upload started");
          file.delete();
        }
      }
    }
    catch (Exception e)
    {
      Play.error();
      LogError.get(e);
    }
  }
}
