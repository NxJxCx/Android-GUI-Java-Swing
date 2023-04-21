/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cellphoneguiapp.njc;

import cellphoneguiapp.njc.interfaces.AndroidHomeOperations;
import java.util.ArrayList;

/**
 *
 * @author canet
 */
public class AndroidAppCollection implements AndroidHomeOperations {
  private final HomePanel parentPanel;
  private final ArrayList<AndroidApp> apps = new ArrayList<AndroidApp>();
  private final ArrayList<String> openApps = new ArrayList<String>();
  
  public AndroidAppCollection(HomePanel parent_android) {
    parentPanel = parent_android;
  }
  
  public HomePanel getParentPanel() {
    return parentPanel;
  }
  
  public void addApp(String appname) throws AndroidAppError {
    for (AndroidApp app: apps) {
      if (app.getAppName().equals(appname)) {
        throw new AndroidAppError("App '" + appname + "' is already installed.");
      }
    }
    apps.add(new AndroidApp(appname, parentPanel));
  }
  
  public AndroidApp getApp(String name) {
    for (AndroidApp app: apps) {
      if (app.getAppName().equals(name)) {
        return app;
      }
    }
    return null;
  }
    
  public void removeApp(String appname) {
    AndroidApp app = getApp(appname);
    if (app != null) {
      if (app.getAppName().equals(appname)) {
        app.closeApp();
        apps.remove(app);
      }
    } 
  }
  
  public void closeRecentApp() {
    if (!openApps.isEmpty()) {
      closeApp(openApps.get(openApps.size()-1));
    }
  }
  
  public void closeAllApps() {
    while (!openApps.isEmpty()) {
      closeRecentApp();
    }
  }

  @Override
  public void openApp(String appname) {
    AndroidApp app = getApp(appname);
    if (app != null) {
      if (!app.isOpen()) {
        app.openApp();
        openApps.add(app.getAppName());
      }
    }
  }

  @Override
  public void closeApp(String appname) {
    AndroidApp app = getApp(appname);
    if (app != null) {
      if (app.isOpen()) {
        app.closeApp();
        openApps.remove(appname);
      }
    }
  }

}
