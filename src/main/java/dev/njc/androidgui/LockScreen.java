package dev.njc.androidgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;


public class LockScreen extends JPanel implements ActionListener {
    private String owner_name, pass_lock;
    private BufferedImage bg_image_buffer;
    private MainAndroidApp parent_frame;
    private DisplayScreen display_panel;
    private PassLockScreen passlock_panel;
    private JPanel gridcontainerDisp, gridcontainerPass;
    private ImageIcon bgImage;
    private Dimension resized_size;
    private Image bg_resized_orig, bg_resized_overlay;

    // constructor
    public LockScreen(MainAndroidApp parentFrame, String ownerName, String passLock, String backgroundImagePath) {
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
        this.owner_name = ownerName;
        this.pass_lock = passLock;
        this.bgImage = new ImageIcon(this.bg_image_buffer);
        Image original = new IconToImage(this.bgImage).getImage();
        Image overlayimg = BackgroundImagePaths.LockOverlayImage.loadImage();
        float ratio = (float) this.bgImage.getIconWidth()/this.bgImage.getIconHeight();
        this.resized_size = new Dimension((int)(MainAndroidApp.fixHeight*ratio), MainAndroidApp.fixHeight);
        this.bg_resized_orig = original.getScaledInstance((int)this.resized_size.getWidth(), (int)this.resized_size.getHeight(), Image.SCALE_SMOOTH);
        this.bg_resized_overlay = overlayimg.getScaledInstance(MainAndroidApp.fixWidth, MainAndroidApp.fixHeight, Image.SCALE_SMOOTH);
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPanels();
    }

    // constructor 
    public LockScreen(MainAndroidApp parentFrame, String ownerName, String passLock, BufferedImage backgroundImageBuffer) {
        super();
        this.bg_image_buffer = backgroundImageBuffer;
        this.parent_frame = parentFrame;
        this.owner_name = ownerName;
        this.pass_lock = passLock;
        this.bgImage = new ImageIcon(this.bg_image_buffer);
        Image original = new IconToImage(this.bgImage).getImage();
        Image overlayimg = BackgroundImagePaths.LockOverlayImage.loadImage();
        float ratio = (float) this.bgImage.getIconWidth()/this.bgImage.getIconHeight();
        this.resized_size = new Dimension((int)(MainAndroidApp.fixHeight*ratio), MainAndroidApp.fixHeight);
        this.bg_resized_orig = original.getScaledInstance((int)this.resized_size.getWidth(), (int)this.resized_size.getHeight(), Image.SCALE_SMOOTH);
        this.bg_resized_overlay = overlayimg.getScaledInstance(MainAndroidApp.fixWidth, MainAndroidApp.fixHeight, Image.SCALE_SMOOTH);
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPanels();
    }

    // private methods
    private void setPanels() {
        this.gridcontainerDisp = new JPanel();
        this.gridcontainerPass = new JPanel();
        this.gridcontainerDisp.setLayout(new GridLayout(1,1));
        this.gridcontainerPass.setLayout(new GridLayout(1,1));
        this.gridcontainerDisp.setOpaque(false);
        this.gridcontainerPass.setOpaque(false);
        this.gridcontainerDisp.setSize(MainAndroidApp.fixSize);
        this.gridcontainerPass.setSize(MainAndroidApp.fixSize);
        this.gridcontainerDisp.setPreferredSize(MainAndroidApp.fixSize);
        this.gridcontainerPass.setPreferredSize(MainAndroidApp.fixSize);
        this.gridcontainerDisp.setMinimumSize(MainAndroidApp.fixSize);
        this.gridcontainerPass.setMinimumSize(MainAndroidApp.fixSize);
        this.gridcontainerDisp.setMaximumSize(MainAndroidApp.fixSize);
        this.gridcontainerPass.setMaximumSize(MainAndroidApp.fixSize);
        this.display_panel = new DisplayScreen(this);
        this.display_panel.setName("Display Panel");
        this.passlock_panel = new PassLockScreen(this.getParentFrame(), this);
        this.passlock_panel.setName("Passlock Panel");
        this.gridcontainerDisp.setVisible(false);
        this.gridcontainerPass.setVisible(false);
        this.gridcontainerDisp.add(this.display_panel);
        this.gridcontainerPass.add(this.passlock_panel);
        this.add(this.gridcontainerDisp);
        this.add(this.gridcontainerPass);
        this.switch_to_display();
    }

    private void switch_to_display() {
        this.passlock_panel.removeActionListener(this);
        this.passlock_panel.setActionsEnabled(false);
        this.gridcontainerPass.setVisible(false);
        this.gridcontainerDisp.setVisible(true);
        this.display_panel.setActionsEnabled(true);
        this.display_panel.addActionListener(this);
    }

    private void switch_to_passlock() {
        Timer animation = new Timer(15, new ActionListener() {
            private int rounds;
            private int lockY;
            {this.rounds = 10; this.lockY = LockScreen.this.display_panel.getY();}
            @Override
            public void actionPerformed(ActionEvent e) {
                this.rounds--;
                this.lockY -= 20;
                if (this.rounds == 0) {
                    final_switch_to_passlock(((Timer)e.getSource()), this);
                } else if (this.rounds > 0) {
                    gridcontainerDisp.setLocation(gridcontainerDisp.getX(), this.lockY);
                }
            }
        });
        animation.start();
    }

    private void final_switch_to_passlock(Timer source, ActionListener action) {
        source.stop();
        source.removeActionListener(action);
        this.display_panel.removeActionListener(this);
        this.display_panel.setActionsEnabled(false);
        this.gridcontainerDisp.setVisible(false);
        this.gridcontainerDisp.setLocation(0, 0);
        this.gridcontainerPass.setVisible(true);
        this.passlock_panel.setActionsEnabled(true);
        this.passlock_panel.addActionListener(this);
    }

    // public methods
    public String timerClock() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
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

    // getter
    public MainAndroidApp getParentFrame() {
        return this.parent_frame;
    }
    public String getOwnerName() {
        return this.owner_name;
    }
    public String getPassLock() {
        return this.pass_lock;
    }
    public BufferedImage getBackgroundImageBuffer() {
        return this.bg_image_buffer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.bg_resized_orig, (this.getParentFrame().getWidth()/2)-((int)(this.resized_size.getWidth()/2)),(this.getParentFrame().getHeight()/2)-((int)(this.resized_size.getHeight()/2)),this);
        g.drawImage(this.bg_resized_overlay, 0, 0, this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.display_panel.isFocusOwner()) {
            this.switch_to_passlock();
        } else {
            this.switch_to_display();
        }
    }
}


class PassLockScreen extends JButton {
    private JPanel topBox, centerBox, bottomBox, topPanel, centerPanel, bottomPanel;
    private JLabel topspacing, space;
    private JPasswordField inputTextBox;
    private JButton[] numberBtns;
    private Document doc_pwd;
    private DocumentListener doc_listen;
    private ActionListener number_listen;
    private MainAndroidApp app;
    private LockScreen lockscreen;
    private boolean enable_actions;

    public PassLockScreen(MainAndroidApp mainapp, LockScreen lockscreenpanel) {
        super();
        this.app = mainapp;
        this.lockscreen = lockscreenpanel;
        this.enable_actions = false;
        setLayout(new BorderLayout());
        setMargin(new Insets(0,0,0,0));
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setSize(MainAndroidApp.fixSize);
        this.topBox = new JPanel();
        this.centerBox = new JPanel();
        this.bottomBox = new JPanel();
        this.topPanel = new JPanel();
        this.centerPanel = new JPanel();
        this.bottomPanel = new JPanel();
        this.topspacing = new JLabel(" ");
        this.space = new JLabel(" ");
        this.inputTextBox = new JPasswordField();
        this.numberBtns = new JButton[11];
        ImageIcon oicon = new ImageIcon(BackgroundImagePaths.NumberOverlayImage.loadImage());
        for (int i = 0; i < 10; i++) {
            this.numberBtns[i] = new NumberButtons("" + i, new IconToImage(oicon).getImage(), (float) oicon.getIconWidth()/oicon.getIconHeight());
            this.numberBtns[i].setFont(new Font("Cambria", Font.BOLD+Font.TRUETYPE_FONT, 50));
            this.numberBtns[i].setName("" + i);
            this.numberBtns[i].setForeground(new Color(50, 220, 50));
            this.numberBtns[i].setBorderPainted(false);
            this.numberBtns[i].setContentAreaFilled(false);
            this.numberBtns[i].setFocusPainted(false);
        }
        this.numberBtns[10] = new NumberButtons("<X]", new IconToImage(oicon).getImage(), (float) oicon.getIconWidth()/oicon.getIconHeight());
        this.numberBtns[10].setFont(new Font("Courier New", Font.BOLD+Font.TRUETYPE_FONT, 20));
        this.numberBtns[10].setName("delete");
        this.numberBtns[10].setForeground(new Color(50, 220, 50));
        this.numberBtns[10].setBorderPainted(false);
        this.numberBtns[10].setContentAreaFilled(false);
        this.numberBtns[10].setFocusPainted(false);
        this.topBox.setOpaque(false);
        this.centerBox.setOpaque(false);
        this.bottomBox.setOpaque(false);
        this.topBox.setLayout(new BoxLayout(this.topBox, BoxLayout.Y_AXIS));
        this.centerBox.setLayout(new BoxLayout(this.centerBox, BoxLayout.Y_AXIS));
        this.bottomBox.setLayout(new BoxLayout(this.bottomBox, BoxLayout.Y_AXIS));
        this.topBox.setPreferredSize(new Dimension(MainAndroidApp.fixWidth, (int)(MainAndroidApp.fixHeight/4)));
        this.centerBox.setPreferredSize(new Dimension(MainAndroidApp.fixWidth, (int)(MainAndroidApp.fixHeight/2)));
        this.bottomBox.setPreferredSize(new Dimension(MainAndroidApp.fixWidth, (int)(MainAndroidApp.fixHeight/8)));
        this.topPanel.setLayout(new BorderLayout());
        this.centerPanel.setLayout(new GridLayout(4, 3));
        this.bottomPanel.setLayout(new BorderLayout());
        this.topPanel.setOpaque(false);
        this.centerPanel.setOpaque(false);
        this.bottomPanel.setOpaque(false);
        this.topspacing.setFont(new Font("Arial Black", Font.BOLD, 100));
        this.topspacing.setMinimumSize(new Dimension(MainAndroidApp.fixWidth, MainAndroidApp.fixHeight/16));
        this.topspacing.setMaximumSize(new Dimension(MainAndroidApp.fixWidth, MainAndroidApp.fixHeight/16));
        this.inputTextBox.setOpaque(false);
        this.inputTextBox.setBorder(null);
        this.inputTextBox.setFont(new Font("Cambria", Font.BOLD, 40));
        this.inputTextBox.setForeground(new Color(50, 100, 255));
        this.inputTextBox.setMargin(new Insets(0,100,0,100));
        this.inputTextBox.setMinimumSize(new Dimension(MainAndroidApp.fixWidth, MainAndroidApp.fixHeight/16));
        this.inputTextBox.setMaximumSize(new Dimension(MainAndroidApp.fixWidth, MainAndroidApp.fixHeight/16));
        this.inputTextBox.setHorizontalAlignment(JTextField.CENTER);
        this.doc_pwd = this.inputTextBox.getDocument();
        this.doc_listen = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                this.process_input(inputTextBox);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                this.process_input(inputTextBox);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                // nothing to do here
            }
            void process_input(JPasswordField pwd) {
                if (enable_actions == true) {
                    String mypass = new String(pwd.getPassword());
                    if (mypass.equals(lockscreen.getPassLock())) {
                        app.switch_panel();
                    }
                }
            }
        };
        this.number_listen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enable_actions == true) {
                    JButton myNumBtn = (JButton)e.getSource();
                    if (myNumBtn.getName().equals("delete")) {
                        char[] passcode = inputTextBox.getPassword();
                        if (passcode.length>0) {
                            char[] newpasscode = new char[passcode.length-1];
                            for (int i = 0; i < passcode.length-1; i++) {
                                newpasscode[i] = passcode[i];
                            }
                            inputTextBox.setText(newpasscode.length > 0 ? new String(newpasscode) : "");
                        }
                    } else {
                        String thepass = new String(inputTextBox.getPassword());
                        inputTextBox.setText(thepass + myNumBtn.getName());
                    }
                    inputTextBox.requestFocus();
                }
            }
        };
        this.topPanel.add(this.inputTextBox, BorderLayout.PAGE_END);
        for (int i = 1; i < 11; i++) {
            if (i < 10) {
                this.centerPanel.add(this.numberBtns[i]);
            } else {
                this.centerPanel.add(new JLabel(" "));
                this.centerPanel.add(this.numberBtns[0]);
                this.centerPanel.add(this.numberBtns[10]);
            }
        }
        this.bottomBox.add(this.space);
        this.centerBox.add(this.centerPanel);
        this.topBox.add(this.topPanel);
        this.add(this.topBox, BorderLayout.NORTH);
        this.add(this.centerBox, BorderLayout.CENTER);
        this.add(this.bottomBox, BorderLayout.SOUTH);
    }

    class NumberButtons extends JButton {
        private Image myImageScaled;
        private Image original;
        private float ratio;
        NumberButtons(String num, Image orig, float ratio) {
            super(num);
            this.myImageScaled = null;
            this.original = orig;
            this.ratio = ratio;
        }

        @Override
        protected void paintComponent(Graphics gg) {
            if (this.myImageScaled == null) {
                this.myImageScaled = original.getScaledInstance((int)(this.getHeight()*ratio), (int)(this.getHeight()), Image.SCALE_SMOOTH);
                gg.drawImage(this.myImageScaled, this.getParent().getX(), this.getParent().getY(),this);
            } else {
                gg.drawImage(this.myImageScaled, this.getParent().getX(), this.getParent().getY(),this);
            }
            super.paintComponent(gg);
        }
    }

    public boolean isActionsEnabled() {
        return this.enable_actions;
    }

    public void setActionsEnabled(boolean enable) {
        this.enable_actions = enable;
        if (enable == false) {
            this.doc_pwd.removeDocumentListener(this.doc_listen);
            for (int i = 0; i < 11; i++) {
                this.numberBtns[i].removeActionListener(this.number_listen);
            }
            this.inputTextBox.setText("");
        } else {
            this.doc_pwd.addDocumentListener(this.doc_listen);
            for (int i = 0; i < 11; i++) {
                this.numberBtns[i].addActionListener(this.number_listen);
            }
        }
    }
}


class DisplayScreen extends JButton {
    private LockScreen lockscreen;
    private JLabel timenow, owner, descr, topspace, owner_label, space;
    private JPanel boxtime, boxowner, boxdescr;
    private ActionListener anim_action;
    private Timer timerClockAnimator;
    private boolean enable_actions;

    public DisplayScreen(LockScreen lockscreenpanel) {
        super();
        this.lockscreen = lockscreenpanel;
        setLayout(new BorderLayout());
        setSize(MainAndroidApp.fixSize);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setMargin(new Insets(0,0,0,0));
        this.timenow = new JLabel("00:00:00");
        this.owner = new JLabel(this.lockscreen.getOwnerName());
        this.owner_label = new JLabel("(Owner)") {
            {   // constructor
                this.setFont(new Font("Cambria", Font.ITALIC, 16));
                setForeground(new Color(220,220,220));
                setAlignmentX(CENTER_ALIGNMENT);
                setAlignmentY(CENTER_ALIGNMENT);
            }
        };
        this.descr = new JLabel("Tap to Unlock");
        this.topspace = new JLabel(" ");
        this.space = new JLabel(" ");
        this.boxtime = new JPanel();
        this.boxowner = new JPanel();
        this.boxdescr = new JPanel();
        this.anim_action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enable_actions == true) {
                    timenow.setText(lockscreen.timerClock());
                }
            }
        };
        this.timerClockAnimator = new Timer(1000, this.anim_action);
        this.topspace.setFont(new Font("Arial Black", Font.BOLD, 140));
        this.space.setFont(new Font("Arial Black", Font.BOLD, 50));
        this.timenow.setFont(new Font("Courier New", Font.BOLD, 70));
        this.owner.setFont(new Font("Cambria", Font.TRUETYPE_FONT+Font.ITALIC+Font.BOLD, 18));
        this.descr.setFont(new Font("Cambria", Font.TRUETYPE_FONT+Font.ITALIC+Font.BOLD, 16));
        this.timenow.setForeground(new Color(50, 70, 240));
        this.owner.setForeground(new Color(200, 200, 250));
        this.descr.setForeground(new Color(255, 255, 255));
        this.timenow.setAlignmentX(CENTER_ALIGNMENT);
        this.timenow.setAlignmentY(CENTER_ALIGNMENT);
        this.timenow.setText(lockscreen.timerClock());
        this.owner.setAlignmentX(CENTER_ALIGNMENT);
        this.owner.setAlignmentY(CENTER_ALIGNMENT);
        this.descr.setAlignmentX(CENTER_ALIGNMENT);
        this.boxtime.setLayout(new BoxLayout(this.boxtime, BoxLayout.Y_AXIS));
        this.boxowner.setLayout(new BoxLayout(this.boxowner, BoxLayout.Y_AXIS));
        this.boxdescr.setLayout(new BoxLayout(this.boxdescr, BoxLayout.Y_AXIS));
        this.boxtime.setOpaque(false);
        this.boxowner.setOpaque(false);
        this.boxdescr.setOpaque(false);
        this.boxtime.setPreferredSize(new Dimension(MainAndroidApp.fixWidth, (int)(MainAndroidApp.fixHeight/2)));
        this.boxowner.setPreferredSize(new Dimension(MainAndroidApp.fixWidth, (int)(MainAndroidApp.fixHeight/4)));
        this.boxtime.add(this.topspace);
        this.boxtime.add(this.timenow);
        this.boxowner.add(this.owner);
        this.boxowner.add(this.owner_label);
        this.boxdescr.add(this.descr);
        this.boxdescr.add(this.space);
        this.add(boxtime, BorderLayout.NORTH);
        this.add(boxowner, BorderLayout.CENTER);
        this.add(boxdescr, BorderLayout.SOUTH);
    }

    public boolean isActionsEnabled() {
        return this.enable_actions;
    }

    public void setActionsEnabled(boolean enable) {
        this.enable_actions = enable;
        if (enable == false) {
            this.timerClockAnimator.removeActionListener(this.anim_action);
            this.timerClockAnimator.stop();
        } else {
            this.timerClockAnimator.addActionListener(this.anim_action);
            this.timerClockAnimator.start();
        }
    }
}