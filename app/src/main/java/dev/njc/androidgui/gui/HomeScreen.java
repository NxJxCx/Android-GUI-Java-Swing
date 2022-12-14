package dev.njc.androidgui.gui;

import dev.njc.androidgui.utils.IconToImage;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class HomeScreen extends JPanel implements ActionListener, MouseListener {
    private BufferedImage bg_image_buffer;
    private MainAndroidApp parent_frame;
    private ArrayList<AndroidApp> installed_apps;
    private ImageIcon bgImage;
    private Dimension resized_size;
    private Image bg_resized_orig;
    private boolean enable_actions;
    private HomeHeadPanel headPanel;
    private HomeBodyPanel bodyPanel;
    private long timeClicked;
    private AtomicInteger counterClicked;
    private JPanel screenContainer;

    public HomeScreen(MainAndroidApp parentFrame, String backgroundImagePath) {
        super();
        BufferedImage img;
        try {
            img = ImageIO.read(new File(backgroundImagePath));
        } catch (IOException e) {
            e.printStackTrace();
            img = null;
        }
        this.bg_image_buffer = img;
        this.parent_frame = parentFrame;
        this.installed_apps = new ArrayList<AndroidApp>();
        this.bgImage = new ImageIcon(this.bg_image_buffer);
        Image original = new IconToImage(this.bgImage).getImage();
        float ratio = (float) this.bgImage.getIconWidth()/this.bgImage.getIconHeight();
        this.resized_size = new Dimension((int)(MainAndroidApp.fixHeight*ratio), MainAndroidApp.fixHeight);
        this.bg_resized_orig = original.getScaledInstance((int)this.resized_size.getWidth(), (int)this.resized_size.getHeight(), Image.SCALE_SMOOTH);
        this.headPanel = new HomeHeadPanel(this);
        this.bodyPanel = new HomeBodyPanel(this);
        this.screenContainer = new JPanel(new BorderLayout());
        this.screenContainer.setOpaque(false);
        this.timeClicked = 0;
        this.counterClicked = new AtomicInteger(0);
        this.setOpaque(false);        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.screenContainer.add(this.headPanel);
        this.screenContainer.add(this.bodyPanel);
        // install apps
        AndroidApp phoneApp = new AndroidApp("Phone", BackgroundImagePaths.PhoneIcon.loadImage());
        AndroidApp messagesApp = new AndroidApp("Messages", BackgroundImagePaths.MessagesIcon.loadImage());
        this.installApp(phoneApp);
        this.installApp(messagesApp);
    }

    public HomeScreen(MainAndroidApp parentFrame, BufferedImage backgroundImageBuffer) {
        super();
        this.bg_image_buffer = backgroundImageBuffer;
        this.parent_frame = parentFrame;
        this.installed_apps = new ArrayList<AndroidApp>();
        this.bgImage = new ImageIcon(this.bg_image_buffer);
        Image original = new IconToImage(this.bgImage).getImage();
        float ratio = (float) this.bgImage.getIconWidth()/this.bgImage.getIconHeight();
        this.resized_size = new Dimension((int)(MainAndroidApp.fixHeight*ratio), MainAndroidApp.fixHeight);
        this.bg_resized_orig = original.getScaledInstance((int)this.resized_size.getWidth(), (int)this.resized_size.getHeight(), Image.SCALE_SMOOTH);
        this.headPanel = new HomeHeadPanel(this);
        this.bodyPanel = new HomeBodyPanel(this);
        this.screenContainer = new JPanel(new BorderLayout());
        this.screenContainer.setOpaque(false);
        this.timeClicked = 0;
        this.counterClicked = new AtomicInteger(0);
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.screenContainer.add(this.headPanel, BorderLayout.BEFORE_FIRST_LINE);
        this.screenContainer.add(this.bodyPanel, BorderLayout.CENTER);
        // install apps
        AndroidApp phoneApp = new AndroidApp("Phone", BackgroundImagePaths.PhoneIcon.loadImage());
        AndroidApp messagesApp = new AndroidApp("Messages", BackgroundImagePaths.MessagesIcon.loadImage());
        this.installApp(phoneApp);
        this.installApp(messagesApp);
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
        this.bodyPanel.installApp(app, this);
    }

    public void resetHomeDisplay(boolean actionEnabled) {
        if (actionEnabled) {
            if (this.getComponentCount() == 0) {
                this.add(this.screenContainer);
            }
        }
        this.setActionsEnabled(actionEnabled);
    }

    public void setWallPaper(String backgroundImagePath) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(backgroundImagePath));
        } catch (IOException e) {
            e.printStackTrace();
            img = null;
        }
        this.bg_image_buffer = img;
        this.bgImage = new ImageIcon(this.bg_image_buffer);
        Image original = new IconToImage(this.bgImage).getImage();
        float ratio = (float) this.bgImage.getIconWidth()/this.bgImage.getIconHeight();
        this.resized_size = new Dimension((int)(MainAndroidApp.fixHeight*ratio), MainAndroidApp.fixHeight);
        this.bg_resized_orig = original.getScaledInstance((int)this.resized_size.getWidth(), (int)this.resized_size.getHeight(), Image.SCALE_SMOOTH);
    }

    // static method
    public static long getTimeStampMillis() {
        LocalDateTime myDateObj = LocalDateTime.now();
        long timestamp = myDateObj.getNano() / 1000000 + myDateObj.getSecond() * 1000 + myDateObj.getMinute() * 1000 * 60 + myDateObj.getHour() * 1000 * 60 * 24 + myDateObj.getDayOfMonth() * 1000 * 60 * 24 * 30;
        return timestamp;
    }

    // getter
    public MainAndroidApp getParentFrame() {
        return this.parent_frame;
    }
    public BufferedImage getBackgroundImageBuffer() {
        return this.bg_image_buffer;
    }
    public boolean isActionsEnabled() {
        return this.enable_actions;
    }
    public ArrayList<AndroidApp> getInstalledApps() {
        return this.installed_apps;
    }

    // setter
    public void setActionsEnabled(boolean enable) {
        if (this.enable_actions != enable) {
            this.enable_actions = enable;
            if (enable == true) {
                this.addMouseListener(this);
            } else {
                this.removeMouseListener(this);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.bg_resized_orig, (MainAndroidApp.fixWidth/2)-((int)(this.resized_size.getWidth()/2)),(MainAndroidApp.fixHeight/2)-((int)(this.resized_size.getHeight()/2)),this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String objName = ((Component)e.getSource()).getName();
        objName.toString();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (getTimeStampMillis() - this.timeClicked < 200) {
            int cc = this.counterClicked.incrementAndGet();
            if (cc == 2) {
                // Double-Click mouse event
                this.getParentFrame().switch_panel(); // switch to lock screen display panel
            }
        } else {
            this.counterClicked.set(1);
        }
        this.timeClicked = getTimeStampMillis();
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    
}


class HomeHeadPanel extends JPanel {
    private JLabel timenow, notifs, internet, batteryStatus;
    private HomeScreen my_parent;
    public HomeHeadPanel(HomeScreen parent) {
        super();
        setBackground(new Color(100,100,100));
        this.my_parent = parent;
        this.timenow = new JLabel("12:00 AM") {
            private Timer myTimer;
            {
                this.myTimer = new Timer(500, new MyActionListener(this));
                this.myTimer.setInitialDelay(35);
                this.myTimer.start();
                this.setAlignmentX(LEFT_ALIGNMENT);
                this.setHorizontalAlignment(SwingConstants.LEFT);
                this.setHorizontalTextPosition(SwingConstants.LEFT);
            }
            class MyActionListener implements ActionListener {
                private JLabel myParent;
                MyActionListener(JLabel parent) {
                    this.myParent = parent;
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (HomeHeadPanel.this.my_parent.isActionsEnabled()) {
                        if (!this.myParent.getText().equals(MainAndroidApp.timerClock12H())) {
                            this.myParent.setText(MainAndroidApp.timerClock12H());
                        }
                    }
                }
            }
        };
        this.notifs = new JLabel("A new message from Neil Jason Canete: \"Hello World!\"");
        this.internet = new JLabel("NO INTERNET") {
            {
                this.setAlignmentX(RIGHT_ALIGNMENT);
                this.setHorizontalAlignment(SwingConstants.RIGHT);
                this.setHorizontalTextPosition(SwingConstants.RIGHT);
            }
        };
        this.batteryStatus = new JLabel("100%  ") {
            private Timer myTimer;
            {
                this.myTimer = new Timer(30000, new MyActionListener(this)); // delay is 30 seconds
                this.myTimer.setInitialDelay(30000);
                this.myTimer.start();
                this.setAlignmentX(LEFT_ALIGNMENT);
                this.setHorizontalAlignment(SwingConstants.LEFT);
                this.setHorizontalTextPosition(SwingConstants.LEFT);
            }
            class MyActionListener implements ActionListener {
                private JLabel myParent;
                private AtomicInteger percent;
                MyActionListener(JLabel parent) {
                    this.myParent = parent;
                    this.percent = new AtomicInteger(100);
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (this.percent.get() == 0) {
                        myTimer.removeActionListener(this);
                        myTimer.stop();
                        HomeHeadPanel.this.my_parent.getParentFrame().shutdownAndroid(); // turn off android (black screen or goodbye screen)
                        return; // skip setText
                    }
                    this.myParent.setText("" + this.percent.decrementAndGet() + "%  ");
                }
            }
        };
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        this.setSize(new Dimension(MainAndroidApp.fixWidth, MainAndroidApp.fixHeight/20));
        timenow.setMinimumSize(new Dimension(30, this.getHeight()-30));
        timenow.setForeground(new Color(255, 255, 255));
        notifs.setAlignmentX(LEFT_ALIGNMENT);
        notifs.setHorizontalAlignment(SwingConstants.LEFT);
        notifs.setHorizontalTextPosition(SwingConstants.LEFT);
        notifs.setMinimumSize(new Dimension(((int)(this.getWidth()/2.5)), this.getHeight()-30));
        notifs.setForeground(new Color(255, 255, 255));
        internet.setMinimumSize(new Dimension(70, this.getHeight()-30));
        internet.setForeground(new Color(255, 255, 255));
        batteryStatus.setAlignmentX(RIGHT_ALIGNMENT);
        batteryStatus.setHorizontalAlignment(SwingConstants.RIGHT);
        batteryStatus.setHorizontalTextPosition(SwingConstants.RIGHT);
        batteryStatus.setForeground(new Color(255, 255, 255));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0,10,0,10);
        this.add(timenow, gbc);
        gbc.anchor = GridBagConstraints.WEST;
        this.add(notifs, gbc);
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        this.add(internet, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        this.add(batteryStatus);
    }
}

class HomeBodyPanel extends JPanel {
    HomeScreen my_parent;
    JPanel timerPanel, gridPanel;
    JLabel timerLabel;
    JPanel[] myContainer, gridCards;
    AndroidAppIcon[] emptySpaces;
    public HomeBodyPanel(HomeScreen parent) {
        super();
        this.my_parent = parent;
        setOpaque(false);
        setLayout(new GridLayout(2, 1));
        this.timerPanel = new JPanel();
        this.gridPanel = new JPanel();
        this.timerPanel.setLayout(new GridLayout(0, 1));
        this.gridPanel.setLayout(new BoxLayout(this.gridPanel, BoxLayout.Y_AXIS));
        this.timerPanel.setOpaque(false);
        this.gridPanel.setOpaque(false);
        this.add(this.timerPanel);
        this.add(this.gridPanel);
        this.timerPanel.add(new JLabel("00:00 AM") {
            private Timer myTimer;
            {   
                setOpaque(false);
                this.myTimer = new Timer(500, new MyActionListener(this));
                this.myTimer.setInitialDelay(35);
                this.myTimer.start();
                this.setAlignmentX(CENTER_ALIGNMENT);
                this.setHorizontalAlignment(SwingConstants.CENTER);
                this.setHorizontalTextPosition(SwingConstants.CENTER);
                this.setFont(new Font("Consolas", Font.BOLD, 72));
                this.setForeground(new Color(0,20,50));
            }
            class MyActionListener implements ActionListener {
                private JLabel myParent;
                MyActionListener(JLabel parent) {
                    this.myParent = parent;
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (HomeBodyPanel.this.my_parent.isActionsEnabled()) {
                        if (!this.myParent.getText().equals(MainAndroidApp.timerClock12H())) {
                            this.myParent.setText(MainAndroidApp.timerClock12H());
                        } else {
                            this.myParent.setText(String.join(" ", MainAndroidApp.timerClock12H().split(":")));
                        }
                    }
                }
            }
        });
        int thiscols = 5;
        int thisrows = (MainAndroidApp.fixHeight/2)/(MainAndroidApp.fixWidth/thiscols);
        this.emptySpaces = new AndroidAppIcon[thisrows*thiscols];
        this.gridCards = new JPanel[thisrows*thiscols];
        this.myContainer = new JPanel[thisrows];
        for (int i = 0; i < thisrows; i++) {
            this.myContainer[i] = new JPanel();
            this.myContainer[i].setLayout(new GridLayout(1,thiscols));
            this.myContainer[i].setOpaque(false);
            for (int j = 0; j < thiscols; j++) {
                this.gridCards[(thiscols*i)+j] = new JPanel() {
                    {
                        setLayout(new CardLayout());
                        setOpaque(false);
                    }
                };
                this.emptySpaces[(thiscols*i)+j] = new AndroidAppIcon(" ", this.my_parent);
                this.gridCards[(thiscols*i)+j].add(this.emptySpaces[(thiscols*i)+j]);
                this.myContainer[i].add(this.gridCards[(thiscols*i)+j]);
            }
        }
        for (JPanel p : this.myContainer) {
            this.gridPanel.add(p);
        }
    }

    public void installApp(AndroidApp app, HomeScreen home) {
        int indx = this.my_parent.getInstalledApps().size();
        this.gridCards[indx].add(app.install(home));
        ((CardLayout)this.gridCards[indx].getLayout()).next(this.gridCards[indx]);
        
    }
}