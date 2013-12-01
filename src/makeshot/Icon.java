package makeshot;

import java.awt.Image;

import settings.Kit;

public class Icon
{
  static Image image;
  
  public static Image get()
  {
    image = Kit.get().getImage(
      Icon.class.getResource("icon.png"));
    
    return image;
  }
}
