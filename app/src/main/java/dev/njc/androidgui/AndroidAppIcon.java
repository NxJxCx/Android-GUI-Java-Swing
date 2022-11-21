package dev.njc.androidgui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;

public class AndroidAppIcon extends JButton {
    private AndroidApp app;
    private HomeScreen home;
    private ImageIcon my_image_icon;
    public AndroidAppIcon(AndroidApp app, HomeScreen home) {
        super();
        this.app = app;
        this.home = home;
        setName(app.getAppName()+"_icon");
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        this.addActionListener(this.app);
    }
    public AndroidAppIcon(String appName, HomeScreen home) {
        super();
        this.home = home;
        setName(appName + "_icon");
        setText(appName);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }
    public AndroidApp getAndroidApp() {
        return this.app;
    }
    public HomeScreen getHomeScreen() {
        return this.home;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.app != null) {
            if (this.my_image_icon == null) {
                ImageIcon orig = app.getAppImageIcon();
                float ratio = orig.getIconHeight()/orig.getIconWidth();
                Dimension resized = new Dimension((int)(this.getWidth()*0.75), (int)(this.getWidth()*0.75*ratio));
                Image img = orig.getImage().getScaledInstance((int)resized.getWidth(), (int)resized.getHeight(), Image.SCALE_SMOOTH);
                this.my_image_icon = new ImageIcon(img);
            }
            ImageIcon oorig = new ImageIcon(BackgroundImagePaths.NumberOverlayImage.loadImage());
            float oratio = oorig.getIconHeight()/oorig.getIconWidth();
            Dimension oresized = new Dimension(this.getWidth(), (int)(this.getWidth()*oratio));
            Image ooimg = oorig.getImage().getScaledInstance((int)oresized.getWidth(), (int)oresized.getHeight(), Image.SCALE_FAST);
            g.drawImage(this.my_image_icon.getImage(), (int)((this.getWidth()/2)-(this.my_image_icon.getIconWidth()/2)), (int)((this.getHeight()/2)-(this.my_image_icon.getIconHeight()/2)), this);
            g.drawImage(ooimg, (int)((this.getWidth()/2)-(oresized.getWidth()/2)), (int)((this.getHeight()/2)-(oresized.getHeight()/2)), this);
            
        }
    }
}
