/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package cellphoneguiapp.njc;

/**
 *
 * @author Neil Jason Canete
 */
public class ShutdownPanel extends javax.swing.JPanel {

  /**
   * Creates new form ShutdownPanel
   */
  public ShutdownPanel() {
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    javax.swing.JLabel content = new javax.swing.JLabel();
    jLabel1 = new javax.swing.JLabel();

    setBackground(new java.awt.Color(0, 0, 0));
    setRequestFocusEnabled(false);
    setVerifyInputWhenFocusTarget(false);

    content.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cellphoneguiapp/njc/resources/goodbye.gif"))); // NOI18N
    jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
    jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
    jLabel1.setIconTextGap(0);
    jLabel1.setInheritsPopupMenu(false);
    jLabel1.setRequestFocusEnabled(false);
    jLabel1.setVerifyInputWhenFocusTarget(false);
    jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Short.MAX_VALUE))
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addGap(0, 0, Short.MAX_VALUE)
          .addComponent(content)
          .addGap(0, 0, Short.MAX_VALUE)))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Short.MAX_VALUE))
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addGap(0, 0, Short.MAX_VALUE)
          .addComponent(content)
          .addGap(0, 0, Short.MAX_VALUE)))
    );

    getAccessibleContext().setAccessibleDescription("");
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  // End of variables declaration//GEN-END:variables
}
