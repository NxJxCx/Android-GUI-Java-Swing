
package cellphoneguiapp.njc.utils;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * 
 * @author Neil Jason Canete
 */
public class Helper {
  
  private static Helper myHelperObject;
  /**
   * 
   * @param resourceFilename
   * @return 
   */
  public static java.net.URL getImage(String resourceFilename) {
    if (myHelperObject == null) {
      myHelperObject = new Helper();
    }
    return myHelperObject.getClass().getResource("/cellphoneguiapp/njc/resources/" + resourceFilename);
  }

  /**
   * @return String - the time now in "HH:mm:ss" (24 Hour) format
   */
  public static String getClock24H() {
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);
    return formattedDate;
  }
  
  /**
   * @param withColon
   * @return String - the time now in "HH:mm AM/PM" (12 Hour) format
   */
  public static String getClock12H(boolean withColon) {
    String[] timenow = getClock24H().split(":");
    int hour = Integer.parseInt(timenow[0]);
    if (hour > 12) {
      int hr = (hour-12);
      timenow[0] = "" + hr;
      timenow[2] = "PM";
    } else {
      timenow[0] = "" + (hour > 9 ? hour : "0" + hour);
      if (hour == 0) {
        timenow[0] = "12";
      }
      timenow[2] = "AM";
    }
    String formattedDate = String.join(withColon ? ":" : " ", timenow[0], String.join(" ", timenow[1], timenow[2]));
    return formattedDate;
  }
  
  /**
   * resizeIcon
   * @param icon
   * @param resizedWidth
   * @param resizedHeight
   * @return ImageIcon resized image
   */
  public static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
    Image img = icon.getImage();  
    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
    return new ImageIcon(resizedImage);
  }
  
  public static Image iconToImage(Icon icon) { 
    if (icon instanceof ImageIcon) { 
      return ((ImageIcon)icon).getImage(); 
    } else {
      int w = icon.getIconWidth(); 
      int h = icon.getIconHeight(); 
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
      GraphicsDevice gd = ge.getDefaultScreenDevice(); 
      GraphicsConfiguration gc = gd.getDefaultConfiguration(); 
      BufferedImage image = gc.createCompatibleImage(w, h); 
      Graphics2D g = image.createGraphics(); 
      icon.paintIcon(null, g, 0, 0); 
      g.dispose(); 
      return image; 
    } 
  }
  
}
