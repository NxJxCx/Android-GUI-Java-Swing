
package cellphoneguiapp.njc;

import cellphoneguiapp.njc.abstracts.AbstractAndroidApp;
import cellphoneguiapp.njc.Apps.*;
import cellphoneguiapp.njc.utils.Helper;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Neil Jason Canete
 */
public class AndroidApp extends AbstractAndroidApp {
  
  private boolean isopen = false;

  public AndroidApp(String app_name, HomePanel parent_android) throws AndroidAppError {
    
    switch (app_name) {
      case "Call":
        setPanel(new Call());
        break;
      case "Messages":
        setPanel(new Messages());
        break;
      case "Contacts":
        setPanel(new Contacts());
        break;
      case "Facebook":
        setPanel(new Facebook());
        break;
      case "Gallery":
        setPanel(new Gallery());
        break;
      case "Camera":
        setPanel(new Camera());
        break;
      default:
        throw new AndroidAppError("App named '" + app_name + "' is not a registered app.");
    }
    this.appname = app_name;
    this.android = parent_android;
    JFrame frame = getWindowFrame();
    frame.setTitle(app_name);
    frame.setIconImage(Helper.iconToImage(new ImageIcon(Helper.getImage(app_name + ".png"))));
    frame.setSize(AndroidGUI.fixSize);
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.setBackground(Color.black);
    frame.addWindowListener(new WindowListener() {
      @Override
      public void windowOpened(WindowEvent e) {}

      @Override
      public void windowClosing(WindowEvent e) {
        android.getApps().closeApp(appname);
      }

      @Override
      public void windowClosed(WindowEvent e) {}

      @Override
      public void windowIconified(WindowEvent e) {}

      @Override
      public void windowDeiconified(WindowEvent e) {}

      @Override
      public void windowActivated(WindowEvent e) {}

      @Override
      public void windowDeactivated(WindowEvent e) {}
      
    });
    frame.setResizable(false);
    frame.getContentPane().setBackground(Color.black);
    frame.setAlwaysOnTop(true);
    frame.setLocationRelativeTo(null);
    frame.setLocationByPlatform(true);
  }
  
  public boolean isOpen() {
    return isopen;
  }
  
  @Override
  public void openApp() {
    JFrame frame = getWindowFrame();
    if (!frame.isShowing()) {
      getWindowFrame().setVisible(true);
      isopen = true;
    }
  }

  @Override
  public void closeApp() {
    JFrame frame = getWindowFrame();
    if (frame.isShowing()) {
      getWindowFrame().dispose();
      isopen = false;
    }
  }
  
}
