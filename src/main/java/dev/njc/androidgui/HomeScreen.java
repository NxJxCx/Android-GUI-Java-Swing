package dev.njc.androidgui;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class HomeScreen extends JPanel {
    private BufferedImage bg_image_buffer;
    private JFrame parent_frame;
    private AndroidApp[] installed_apps;

    public HomeScreen(JFrame parentFrame, String backgroundImagePath) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(backgroundImagePath));
        } catch (IOException e) {
            e.printStackTrace();
            img = null;
        }
        this.bg_image_buffer = img;
        this.parent_frame = parentFrame;
        this.installed_apps = new AndroidApp[12]; // limit apps installs to 12 apps
    }

    public HomeScreen(JFrame parentFrame, BufferedImage backgroundImageBuffer) {
        this.bg_image_buffer = backgroundImageBuffer;
        this.parent_frame = parentFrame;
        this.installed_apps = new AndroidApp[12]; // limit apps installs to 12 apps
    }

    // methods
    /** install an AndroidApp JFrame */
    public void installApp(AndroidApp app) {
        for (AndroidApp installed : this.installed_apps) {
            if (app.getAppName().equals(installed.getAppName())) {
                // error install: already exist app
                System.out.println("Unable to install " + app.getAppName() + " app. App already installed.");
                return;
            }
        }
        app.install(this);
    }

    // getter
    public JFrame getParentFrame() {
        return this.parent_frame;
    }
    public BufferedImage getBackgroundImageBuffer() {
        return this.bg_image_buffer;
    }
}
