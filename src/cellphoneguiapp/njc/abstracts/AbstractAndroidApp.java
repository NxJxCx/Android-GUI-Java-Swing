
package cellphoneguiapp.njc.abstracts;

import cellphoneguiapp.njc.HomePanel;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Neil Jason Canete
 */
public abstract class AbstractAndroidApp {
  protected String appname;
  protected HomePanel android;
  private final JFrame window = new JFrame();
  private JPanel panel;
  
  public JFrame getWindowFrame() {
    return window;
  }

  public String getAppName() {
    return appname;
  }
  
  public HomePanel getAndroidParent() {
    return android;
  }
  
  public void setPanel(JPanel panel) {
    if (this.panel != null) {
      window.remove(this.panel);
    }
    this.panel = panel;
    window.setContentPane(panel);
  }
  
  public JPanel getPanel() {
    return panel;
  }

  public abstract void openApp();
  public abstract void closeApp();
}
