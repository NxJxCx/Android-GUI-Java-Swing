package dev.njc.androidgui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LockScreen extends JPanel implements ActionListener {
    private String owner_name, pass_lock;
    private BufferedImage bg_image_buffer;
    private MainAndroidApp parent_frame;
    private JButton display_panel, passlock_panel;
    private JPanel gridcontainer;
    private ImageIcon bgImage;
    private JButton current_panel;

    public LockScreen(MainAndroidApp parentFrame, String ownerName, String passLock, String backgroundImagePath) {
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
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPanels();
    }

    public LockScreen(MainAndroidApp parentFrame, String ownerName, String passLock, BufferedImage backgroundImageBuffer) {
        this.bg_image_buffer = backgroundImageBuffer;
        this.parent_frame = parentFrame;
        this.owner_name = ownerName;
        this.pass_lock = passLock;
        this.bgImage = new ImageIcon(this.bg_image_buffer);
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPanels();
    }

    // private methods
    private void setPanels() {
        JPanel gcon = new JPanel();
        gcon.setLayout(new GridLayout(1,1));
        JButton dpanel = new JButton();
        JButton ppanel = new JButton();
        dpanel.setName("Display Panel");
        ppanel.setName("Passlock Panel");
        gcon.setOpaque(false);
        dpanel.setOpaque(false);
        ppanel.setOpaque(false);
        dpanel.setBounds(0,0,this.getWidth(),this.getHeight());
        ppanel.setBounds(0,0,this.getWidth(),this.getHeight());
        this.gridcontainer = gcon;
        this.display_panel = dpanel;
        this.passlock_panel = ppanel;
        this.switch_to_display();
        this.add(gcon);
    }

    private void switch_to_display() {
        this.display_panel.removeActionListener(this);
        this.gridcontainer.removeAll();
        this.gridcontainer.setBounds(0,0, LockScreen.this.getParentFrame().getFixWidth(), LockScreen.this.getParentFrame().getFixHeight());
        this.gridcontainer.setPreferredSize(this.getParentFrame().getFixSize());
        this.gridcontainer.setMinimumSize(this.getParentFrame().getSize());
        this.current_panel = this.display_panel;
        this.removeAll();
        this.gridcontainer.add(this.display_panel);
        this.setDisplayPanel();
    }

    private void switch_to_passlock() {
        ActionListener animAction = new ActionListener() {
            private int rounds;
            private int lockY;
            {this.rounds = 5; this.lockY = LockScreen.this.display_panel.getY();}
            @Override
            public void actionPerformed(ActionEvent e) {
                this.rounds--;
                this.lockY -= 80;
                if (this.rounds == 0) {
                    ((Timer)e.getSource()).stop();
                    ((Timer)e.getSource()).removeActionListener(this);
                    LockScreen.this.finalswitch_passlock();
                } else if (this.rounds > 0) {
                    LockScreen.this.gridcontainer.setBounds(LockScreen.this.display_panel.getX(), this.lockY, LockScreen.this.getParentFrame().getFixWidth(), LockScreen.this.getParentFrame().getFixHeight());
                }
            }
        };
        Timer animation = new Timer(15, animAction);
        animation.start();
    }

    private void finalswitch_passlock() {
        this.passlock_panel.removeActionListener(this);
        this.gridcontainer.removeAll();
        this.gridcontainer.setBounds(0,0, LockScreen.this.getParentFrame().getFixWidth(), LockScreen.this.getParentFrame().getFixHeight());
        this.gridcontainer.setPreferredSize(this.getParentFrame().getFixSize());
        this.gridcontainer.setMinimumSize(this.getParentFrame().getSize());
        this.current_panel = this.passlock_panel;
        this.removeAll();
        this.gridcontainer.add(this.passlock_panel);
        this.add(this.gridcontainer);
        this.setPassLockPanel();
    }

    private void timerClock(JLabel timelabel) {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        timelabel.setText(formattedDate);
    }

    private void setDisplayPanel() {
        JButton btn = this.display_panel;
        btn.removeAll();
        btn.setLayout(new BorderLayout());
        btn.setMargin(new Insets(0,0,0,0));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        JLabel timenow = new JLabel("00:00:00");
        JLabel owner = new JLabel(this.getOwnerName());
        JLabel descr = new JLabel("Tap to Unlock");
        JPanel boxtime = new JPanel();
        JPanel boxowner = new JPanel();
        JPanel boxdescr = new JPanel();
        JLabel topspace = new JLabel(" ");
        ActionListener animAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (LockScreen.this.display_panel.equals(LockScreen.this.current_panel)) {
                    LockScreen.this.timerClock(timenow);
                } else {
                    ((Timer)e.getSource()).stop();
                    ((Timer)e.getSource()).removeActionListener(this);
                }
            }
        };
        Timer clocktimer = new Timer(500, animAction);
        clocktimer.start();
        topspace.setFont(new Font("Arial Black", Font.BOLD, 140));
        timenow.setFont(new Font("Courier New", Font.BOLD, 70));
        owner.setFont(new Font("Cambria", Font.TRUETYPE_FONT+Font.ITALIC+Font.BOLD, 18));
        descr.setFont(new Font("Cambria", Font.TRUETYPE_FONT+Font.ITALIC+Font.BOLD, 16));
        timenow.setForeground(new Color(50, 70, 240));
        owner.setForeground(new Color(0, 0, 0));
        descr.setForeground(new Color(255, 255, 255));
        boxtime.setOpaque(false);
        boxowner.setOpaque(false);
        boxdescr.setOpaque(false);
        boxtime.setPreferredSize(new Dimension(this.getParentFrame().getFixWidth(), (int)(this.getParentFrame().getFixHeight()/2)));
        boxowner.setPreferredSize(new Dimension(this.getParentFrame().getFixWidth(), (int)(this.getParentFrame().getFixHeight()/4)));
        boxtime.setLayout(new BoxLayout(boxtime, BoxLayout.Y_AXIS));
        boxowner.setLayout(new BoxLayout(boxowner, BoxLayout.Y_AXIS));
        boxdescr.setLayout(new BoxLayout(boxdescr, BoxLayout.Y_AXIS));
        timenow.setAlignmentX(CENTER_ALIGNMENT);
        timenow.setAlignmentY(CENTER_ALIGNMENT);
        owner.setAlignmentX(CENTER_ALIGNMENT);
        owner.setAlignmentY(CENTER_ALIGNMENT);
        descr.setAlignmentX(CENTER_ALIGNMENT);
        boxtime.add(topspace);
        boxtime.add(timenow);
        boxowner.add(owner);
        boxowner.add(new JLabel("(Owner)") {
            {   // constructor
                this.setFont(new Font("Cambria", 2, 16));
                setForeground(new Color(0,0,0));
                setAlignmentX(CENTER_ALIGNMENT);
                setAlignmentY(CENTER_ALIGNMENT);
            }
        });
        boxdescr.add(descr);
        boxdescr.add(new JLabel(" "));
        btn.add(boxtime, BorderLayout.NORTH);
        btn.add(boxowner, BorderLayout.CENTER);
        btn.add(boxdescr, BorderLayout.SOUTH);
        btn.addActionListener(this);
    }

    private void setPassLockPanel() {
        JButton btn = this.passlock_panel;
        btn.removeAll();
        btn.setLayout(new BorderLayout());
        btn.setMargin(new Insets(0,0,0,0));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        JPanel topbox = new JPanel();
        JPanel centerbox = new JPanel();
        JPanel bottombox = new JPanel();
        topbox.setOpaque(false);
        centerbox.setOpaque(false);
        bottombox.setOpaque(false);
        topbox.setPreferredSize(new Dimension(this.getParentFrame().getFixWidth(), (int)(this.getParentFrame().getFixHeight()/4)));
        centerbox.setPreferredSize(new Dimension(this.getParentFrame().getFixWidth(), (int)(this.getParentFrame().getFixHeight()/2)));
        bottombox.setPreferredSize(new Dimension(this.getParentFrame().getFixWidth(), (int)(this.getParentFrame().getFixHeight()/8)));
        topbox.setLayout(new BoxLayout(topbox, BoxLayout.Y_AXIS));
        centerbox.setLayout(new BoxLayout(centerbox, BoxLayout.Y_AXIS));
        bottombox.setLayout(new BoxLayout(bottombox, BoxLayout.Y_AXIS));
        topbox.setName("top");
        centerbox.setName("center");
        bottombox.setName("bottom");
        JLabel topspacing = new JLabel(" ");
        topspacing.setFont(new Font("Arial Black", Font.BOLD, 100));
        topspacing.setMinimumSize(new Dimension(this.getParentFrame().getFixWidth(), this.getParentFrame().getFixHeight()/16));
        topspacing.setMaximumSize(new Dimension(this.getParentFrame().getFixWidth(), this.getParentFrame().getFixHeight()/16));
        JPasswordField inputTextbox = new JPasswordField( 10);
        inputTextbox.setFont(new Font("Cambria", Font.BOLD, 40));
        inputTextbox.setForeground(new Color(50, 100, 255));
        inputTextbox.setMinimumSize(new Dimension(this.getParentFrame().getFixWidth(), this.getParentFrame().getFixHeight()/16));
        inputTextbox.setMaximumSize(new Dimension(this.getParentFrame().getFixWidth(), this.getParentFrame().getFixHeight()/16));
        inputTextbox.setOpaque(false);
        inputTextbox.setMargin(new Insets(0,100,0,100));
        inputTextbox.setHorizontalAlignment(JTextField.CENTER);
        inputTextbox.setBorder(null);
        inputTextbox.setActionCommand("inputPassword");
        inputTextbox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                this.process_input(inputTextbox);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                this.process_input(inputTextbox);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // nothing to do here
            }
            
            void process_input(JPasswordField pwd) {
                String mypass = new String(pwd.getPassword());
                if (mypass.equals(LockScreen.this.getPassLock())) {
                    LockScreen.this.getParentFrame().switch_panel();
                }
            }
            
        });
        JButton[] numbers = new JButton[11];
        ActionListener numberListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton myNumBtn = (JButton)e.getSource();
                for (int i = 0; i < 10; i++) {
                    if (myNumBtn.getName().equals("Button #" + i)) {
                        String thepass = new String(inputTextbox.getPassword());
                        inputTextbox.setText(thepass + i);
                        if (myNumBtn.isFocusOwner()) {
                            Timer animate = new Timer(35, new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    myNumBtn.setForeground(new Color(0, 100, 0));
                                }
                            });
                            myNumBtn.setForeground(new Color(255, 255, 255));
                            animate.setRepeats(false);
                            animate.setInitialDelay(15);
                            animate.start();
                            inputTextbox.requestFocus();
                        }
                        return;
                    }
                }
                if (myNumBtn.getName().equals("delete")) {
                    char[] passcode = inputTextbox.getPassword();
                    if (passcode.length>0) {
                        char[] newpasscode = new char[passcode.length-1];
                        for (int i = 0; i < passcode.length-1; i++) {
                            newpasscode[i] = passcode[i];
                        }
                        inputTextbox.setText(newpasscode.length > 0 ? new String(newpasscode) : "");
                    }
                    if (myNumBtn.isFocusOwner()) {
                        Timer animate = new Timer(35, new ActionListener() {
    
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                myNumBtn.setForeground(new Color(0, 100, 0));
                            }
                        });
                        myNumBtn.setForeground(new Color(255, 255, 255));
                        animate.setRepeats(false);
                        animate.setInitialDelay(15);
                        animate.start();
                        inputTextbox.requestFocus();
                    }
                }
                
            }
        };
        for (int i = 0; i < 10; i++) {
            numbers[i] = new JButton("" + i);
            numbers[i].setFont(new Font("Cambria", Font.BOLD+Font.TRUETYPE_FONT, 50));
            numbers[i].setName("Button #" + i);
            numbers[i].setForeground(new Color(0, 100, 0));
            numbers[i].setBorderPainted(false);
            numbers[i].setContentAreaFilled(false);
            numbers[i].setFocusPainted(false);
            numbers[i].addActionListener(numberListener);
        }
        numbers[10] = new JButton("<X]");
        numbers[10].setFont(new Font("Courier New", Font.BOLD, 20));
        numbers[10].setName("delete");
        numbers[10].setForeground(new Color(0, 100, 0));
        numbers[10].setBorderPainted(false);
        numbers[10].setContentAreaFilled(false);
        numbers[10].setFocusPainted(false);
        numbers[10].addActionListener(numberListener);
        JPanel toppanel = new JPanel();
        toppanel.setLayout(new BorderLayout());
        toppanel.setOpaque(false);
        toppanel.add(inputTextbox, BorderLayout.PAGE_END);
        topbox.add(toppanel);
        JPanel centerpanel = new JPanel();
        centerpanel.setLayout(new GridLayout(4, 3));
        centerpanel.setOpaque(false);
        for (int i = 1; i <= 10; i++) {
            if (i < 10)
                centerpanel.add(numbers[i]);
            else {
                centerpanel.add(new JLabel(" "));
                centerpanel.add(numbers[0]);
                centerpanel.add(numbers[10]);
            }
        }
        centerbox.add(centerpanel);
        bottombox.add(new JLabel(" "));
        btn.add(topbox, BorderLayout.NORTH);
        btn.add(centerbox, BorderLayout.CENTER);
        btn.add(bottombox, BorderLayout.SOUTH);
        btn.addActionListener(this);
        inputTextbox.requestFocus();
    }

    // public methods
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
        g.drawImage(new IconToImage(this.bgImage).getImage(),(this.getParentFrame().getWidth()/2)-(this.bgImage.getIconWidth()/2),(this.getParentFrame().getHeight()/2)-(this.bgImage.getIconHeight()/2),this);
        Image overlayimg = BackgroundImagePaths.LockOverlayImage.loadImage();
        Image resizedoverlay = overlayimg.getScaledInstance(this.getParentFrame().getFixWidth(), this.getParentFrame().getFixHeight(), Image.SCALE_SMOOTH);
        g.drawImage(resizedoverlay,0,0,this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.display_panel.isFocusOwner()) {
            this.switch_to_passlock();
        } else {
            System.out.println("At passlock");
        }
    }
}
