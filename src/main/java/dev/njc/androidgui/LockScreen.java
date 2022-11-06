package dev.njc.androidgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
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
        gcon.setOpaque(false);
        dpanel.setOpaque(false);
        ppanel.setOpaque(false);
        dpanel.setBounds(0,0,this.getWidth(),this.getHeight());
        ppanel.setBounds(0,0,this.getWidth(),this.getHeight());
        this.gridcontainer = gcon;
        this.display_panel = dpanel;
        this.passlock_panel = ppanel;
        this.setDisplayPanel();
        this.setPassLockPanel();
        this.switch_to_display();
        this.add(gcon);
    }

    private void switch_to_display() {
        this.gridcontainer.remove(this.passlock_panel);
        this.current_panel = this.display_panel;
        this.gridcontainer.add(this.current_panel);
        this.repaint();
    }

    private void switch_to_passlock() {
        this.current_panel = this.passlock_panel;
        LockScreen lock = this;
        ActionListener animAction = new ActionListener() {
            private int rounds;
            private int lockY;
            {this.rounds = 5; this.lockY = lock.display_panel.getY();}
            @Override
            public void actionPerformed(ActionEvent e) {
                this.rounds--;
                this.lockY -= 80;
                if (this.rounds < 0) {
                    lock.gridcontainer.remove(lock.display_panel);
                    lock.gridcontainer.add(lock.current_panel);
                    Timer timer = (Timer)e.getSource();
                    timer.stop();
                } else {
                    lock.display_panel.setLocation(lock.display_panel.getX(), this.lockY);
                }
                lock.repaint();
            }
        };
        Timer animation = new Timer(15, animAction);
        animation.start();
        
    }

    private void timerClock(JLabel timelabel) {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        timelabel.setText(formattedDate);
    }

    private void setDisplayPanel() {
        LockScreen thislock = this;
        JButton btn = this.display_panel;
        JLabel timenow = new JLabel("00:00:00");
        JLabel owner = new JLabel(this.getOwnerName());
        JLabel descr = new JLabel("Tap to Unlock");
        JPanel boxtime = new JPanel();
        JPanel boxowner = new JPanel();
        JPanel boxdescr = new JPanel();
        JLabel topspace = new JLabel(" ");
        ActionListener animAction = new ActionListener() {
            JLabel timelabel;
            LockScreen lockscreen;
            {this.timelabel = timenow; this.lockscreen = thislock;}
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lockscreen.current_panel.equals(lockscreen.display_panel)) {
                    this.lockscreen.timerClock(this.timelabel);
                }
            }
        };
        Timer clocktimer = new Timer(100, animAction);
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
            {this.setFont(new Font("Cambria", 2, 16)); setForeground(new Color(0,0,0)); setAlignmentX(CENTER_ALIGNMENT); setAlignmentY(CENTER_ALIGNMENT);}
        });
        boxdescr.add(descr);
        boxdescr.add(new JLabel(" "));
        btn.setLayout(new BorderLayout());
        btn.setMargin(new Insets(0,0,0,0));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.add(boxtime, BorderLayout.NORTH);
        btn.add(boxowner, BorderLayout.CENTER);
        btn.add(boxdescr, BorderLayout.SOUTH);
        btn.addActionListener(this);
    }

    private void setPassLockPanel() {

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
        if (((JButton)e.getSource()).equals(this.display_panel)) {
            this.switch_to_passlock();
        } else if (((JButton)e.getSource()).equals(this.passlock_panel)) {
            System.out.println("alright");
        }
    }
}
