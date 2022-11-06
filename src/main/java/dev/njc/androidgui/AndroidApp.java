package dev.njc.androidgui;

import javax.swing.*;
import java.awt.event.*;

public class AndroidApp extends JFrame implements ActionListener {
    private String app_name;
    private String app_icon_path;

    public AndroidApp(String appName, String appIconPath) {
        this.app_name = appName;
        this.app_icon_path = appIconPath;
        setTitle(appName);
        setSize(1200, 540);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // methods
    public void install(JPanel homepanel) {
        // TODO: display an icon in homepanel of this app
    }
    // getter
    public String getAppName() {
        return this.app_name;
    }
    public String getAppIconPath() {
        return this.app_icon_path;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
