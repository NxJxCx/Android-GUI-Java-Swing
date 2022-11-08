package dev.njc.androidgui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AndroidApp extends JFrame implements ActionListener {
    private String app_name;
    private BufferedImage app_icon_buffer;
    private ImageIcon app_image_icon;

    public AndroidApp(String appName, String appIconPath) {
        super();
        this.app_name = appName;
        this.app_icon_buffer = loadImage(appIconPath);
        this.app_image_icon = new ImageIcon(loadImage(appIconPath));
        setTitle(appName);
        setSize(1200, 540);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public AndroidApp(String appName, BufferedImage appIconBuffer) {
        super();
        this.app_name = appName;
        this.app_icon_buffer = appIconBuffer;
        this.app_image_icon = new ImageIcon(appIconBuffer);
        setTitle(appName);
        setSize(1200, 540);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

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
    public void install(JPanel homepanel) {
        // TODO: display an icon in homepanel of this app
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

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
