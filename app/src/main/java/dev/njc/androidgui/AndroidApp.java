package dev.njc.androidgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AndroidApp extends JFrame implements ActionListener {
    private String app_name;
    private BufferedImage app_icon_buffer;
    private ImageIcon app_image_icon;
    private AndroidAppIcon my_app_icon;
    private HomeScreen my_home;
    private boolean installed;

    public AndroidApp(String appName, String appIconPath) {
        super();
        this.app_name = appName;
        this.app_icon_buffer = loadImage(appIconPath);
        this.app_image_icon = new ImageIcon(loadImage(appIconPath));
        setTitle(appName);
        setSize(MainAndroidApp.fixSize);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public AndroidApp(String appName, BufferedImage appIconBuffer) {
        super();
        this.app_name = appName;
        this.app_icon_buffer = appIconBuffer;
        this.app_image_icon = new ImageIcon(appIconBuffer);
        setTitle(appName);
        setSize(MainAndroidApp.fixSize);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // static methods
    public static BufferedImage loadImage(String pathname) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(pathname));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot read file image: '"+pathname+"'");
            img = null;
        }
        return img;
    }

    // methods
    public AndroidAppIcon install(HomeScreen homescreen) {
        this.my_home = homescreen;
        this.my_app_icon = new AndroidAppIcon(this, homescreen);
        this.installed = true;
        homescreen.getInstalledApps().add(this);
        return this.my_app_icon;
    }

    // getter
    public String getAppName() {
        return this.app_name;
    }
    public BufferedImage getAppIconBuffer() {
        return this.app_icon_buffer;
    }
    public ImageIcon getAppImageIcon() {
        return this.app_image_icon;
    }
    public AndroidAppIcon getAndroidAppIcon() {
        return this.installed ? this.my_app_icon : null;
    }
    public HomeScreen getHomeScreen() {
        return this.installed ? this.my_home : null;
    }
    public boolean isAppInstalled() {
        return this.installed;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (((Component)e.getSource()).getName().equals(this.app_name+"_icon")) {
            System.out.println("Opening the app : " + this.app_name + " ...");
            this.setLocation(this.my_home.getParentFrame().getX()+this.my_home.getParentFrame().getWidth(), this.my_home.getParentFrame().getY());
            this.setVisible(true);
        }
    }
}
