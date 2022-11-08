package dev.njc.androidgui;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class MainAndroidApp extends JFrame implements Runnable {
    private HomeScreen homepanel;
    private LockScreen lockpanel;
    private AndroidHomeLockPanels current_panel;
    public static int fixWidth = 480, fixHeight = 956;
    public static Dimension fixSize = new Dimension(MainAndroidApp.fixWidth, MainAndroidApp.fixHeight);

    // constructor
    public MainAndroidApp(String ownerName, String passLock) {
        this.homepanel = new HomeScreen(this, BackgroundImagePaths.HomeScreenImage.loadImage());
        this.lockpanel = new LockScreen(this, normalizeString(ownerName), normalizeString(passLock), BackgroundImagePaths.LockScreenImage.loadImage());
        setTitle("Android GUI Imitator by NJC - " + normalizeString(ownerName));
        setSize(fixSize);
        setMinimumSize(fixSize);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.switch_to_panel(AndroidHomeLockPanels.LockPanel);
    }

    // private methods
    /** switch to a home or lock panel of JFrame **/
    private void switch_to_panel(AndroidHomeLockPanels currentPanel) {
        if (currentPanel == this.current_panel)
            return;
        this.getContentPane().removeAll();
        this.current_panel = currentPanel; 
        JPanel panel = currentPanel==AndroidHomeLockPanels.HomePanel ? this.homepanel : this.lockpanel;
        this.setContentPane(panel);  // change content JPanel of this JFrame
        if (currentPanel == AndroidHomeLockPanels.LockPanel)
            this.lockpanel.resetLockDisplay();
        this.homepanel.resetHomeDisplay(currentPanel == AndroidHomeLockPanels.HomePanel); 
    }

    // static methods
    /** converting all special characters to UTF-8 charset **/
    public static String normalizeString(String string) {
        String str;
        try {
            str = new String(string.getBytes("ISO-8859-15"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Setting ownerName parameter of MainAndroidApp to its orginal '" + string + "'");
            str = string;
        }
        return str;
    }

    // return the time now in HH:mm:ss (24 Hour) format
    public static String timerClock() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }
    // return the time now in HH:mm AM/PM (12 Hour) format
    public static String timerClock12H() {
        String[] timenow = MainAndroidApp.timerClock().split(":");
        int hour = Integer.parseInt(timenow[0]);
        if (hour > 12) {
            timenow[0] = "" + ((int)hour-12);
            timenow[2] = "PM";
        } else {
            timenow[2] = "AM";
        }
        String formattedDate = String.join(":", timenow[0], String.join(" ", timenow[1], timenow[2]));
        return formattedDate;
    }

    // public methods
    /** show GUI. **/
    public void showGUI() {
        setResizable(false);
        //setLocationRelativeTo(null);
        setVisible(true);
    }

    /** switch panels between home and lock screen **/
    public void switch_panel() {
        if (this.current_panel == AndroidHomeLockPanels.LockPanel)
            this.switch_to_panel(AndroidHomeLockPanels.HomePanel);
        else
            this.switch_to_panel(AndroidHomeLockPanels.LockPanel);
    }

    // getter
    public JPanel homeScreenPanel() {
        return this.homepanel;
    }

    public JPanel lockScreenPanel() {
        return this.lockpanel;
    }
    // setter

    // overrides
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        this.homepanel.setSize(width, height);
        this.lockpanel.setSize(width, height);
    }

    // invoked on the event dispatching thread
    public void run() {
        showGUI();
    }
    
    // main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MainAndroidApp("Neil Jason Cañete", "0701"));
    }

    public void shutdownAndroid() {
        System.out.println("shutting down Android...");
        JPanel j = new JPanel();
        JLabel h = new JLabel("Goodbye!");
        j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
        j.add(h);
        this.setContentPane(j);
        this.repaint();
    }

}

enum AndroidHomeLockPanels {
    HomePanel, LockPanel
}

enum BackgroundImagePaths {
    
    HomeScreenImage("./res/images/homescreen.jpg"),
    LockScreenImage("./res/images/lockscreen.jpg"),
    LockOverlayImage("./res/images/lockoverlay.png"),
    NumberOverlayImage("./res/images/numoverlay.png");

    private String pathname;
    private BackgroundImagePaths(String path) {
        this.pathname = path;
    }
    public String getValue() {
        return this.pathname;
    }
    public String toString() {
        return this.pathname;
    }
    public BufferedImage loadImage() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(this.pathname));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot read file image: '"+this.pathname+"'");
            img = null;
        }
        return img;
    }

}
