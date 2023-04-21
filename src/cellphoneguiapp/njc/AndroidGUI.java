
package cellphoneguiapp.njc;

import cellphoneguiapp.njc.interfaces.AndroidOperations;
import cellphoneguiapp.njc.utils.Helper;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 * 
 * @author Neil Jason Canete
 */
public class AndroidGUI implements Runnable, AndroidOperations {
  public final static Dimension fixSize = new Dimension(420, 750);
  public final Image icon = Helper.iconToImage(new ImageIcon(Helper.getImage("icon.png")));
  
  private final JFrame thisFrame = new JFrame();
  private final CardLayout layout = new CardLayout(0,0);
  private final JPanel turnOnPanel = new JPanel();
  private final JPanel homePanel = new HomePanel(this);
  private final JPanel turnOffPanel = new JPanel();
  private String displayName;

  public AndroidGUI(String displayName) {
    initPanels();
    this.displayName = displayName;
  }
  
  public void setDisplayOwnerName(String name) {
    this.displayName = name;
  }
  
  public String getDisplayOwnerName() {
    return this.displayName;
  }
  
  public JFrame getFrame() {
    return this.thisFrame;
  }
  
  
  private void initPanels() {
    // set icon for the frame
    this.thisFrame.setIconImage(icon);
    /* turnOnPanel */
    CardLayout turnOnCard = new CardLayout(75, 200);
    Icon powerBtnImage = Helper.resizeIcon(new ImageIcon(Helper.getImage("powerbtn.png")), 200, 200);
    ImageIcon spinner = new ImageIcon(Helper.getImage("spinner.gif"));
    JLabel loadingSpinner = new JLabel();
    loadingSpinner.setIcon(spinner);
    loadingSpinner.setSize(200, 200);
    JButton turnOnBtn = new JButton("", powerBtnImage);
    turnOnBtn.setOpaque(false);
    turnOnBtn.setContentAreaFilled(false);
    turnOnBtn.setBorderPainted(false);
    turnOnBtn.setFocusPainted(false);
    turnOnBtn.setSize(200, 200);
    turnOnBtn.setMargin(new Insets(0, 0, 0, 0));
    turnOnBtn.addActionListener((ActionEvent e) -> {
      turnOn();
    });
    turnOnPanel.setOpaque(false);
    turnOnPanel.setSize(new Dimension((int)(fixSize.getWidth() * 0.5), (int)(fixSize.getHeight() * 0.5)));
    turnOnPanel.setLayout(turnOnCard);
    turnOnPanel.add(turnOnBtn);
    turnOnPanel.add(loadingSpinner);
    
    
    turnOffPanel.add(new ShutdownPanel());
    
    thisFrame.setLayout(layout);
    thisFrame.add("first", turnOnPanel);
    thisFrame.add("second", homePanel);
    thisFrame.add("third", turnOffPanel);
  }

  
  /**
   * implements the runnable interface for running the Java Swing JFrame
   */
  @Override
  public void run() {
    thisFrame.setTitle("Android (" + displayName + ")");
    thisFrame.setSize(fixSize);
    thisFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    thisFrame.setResizable(false);
//    setUndecorated(true);
    thisFrame.getContentPane().setBackground(Color.black);
    thisFrame.setLocationRelativeTo(null);
    thisFrame.setVisible(true);
  }

  @Override
  public void turnOn() {
    Container thisPane = thisFrame.getContentPane();
    System.out.println("Turning On Android...");
    ((CardLayout)turnOnPanel.getLayout()).next(turnOnPanel);
    Timer loading = new Timer(1000, (ActionEvent et)->{
      ((Timer)et.getSource()).stop();
      ((CardLayout)thisPane.getLayout()).next(thisPane);
      ((CardLayout)turnOnPanel.getLayout()).next(turnOnPanel);
    });
    loading.start();
  }

  @Override
  public void shutdown() {

  }
  
}
