package dev.njc.androidgui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;

public class AndroidAppIcon extends JButton {
    private AndroidApp app;
    private HomeScreen home;
    public AndroidAppIcon(AndroidApp app, HomeScreen home) {
        super();
        //setIcon(app.getAppImageIcon());
        setText(app.getAppName());
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }
    public AndroidAppIcon(String appName, HomeScreen home) {
        super();
        setText(appName);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.app != null) {
            ImageIcon orig = app.getAppImageIcon();
            Image img_o = orig.getImage();
            float ratio = orig.getIconHeight()/orig.getIconWidth();
            Dimension resized = new Dimension((this.getWidth()/2), (int)((this.getWidth()/2)*ratio));
            Image img = img_o.getScaledInstance((int)resized.getWidth(), (int)resized.getHeight(), Image.SCALE_FAST);
            g.drawImage(img, (int)((this.getWidth()/2)-(resized.getWidth()/2)), (int)((this.getHeight()/2)-(resized.getHeight()/2)), this);
        }
    }
}
