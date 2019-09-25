
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TiedostoGUI extends javax.swing.JFrame {

  private int valinta;

  public TiedostoGUI() {
    String[] kielet = {"Finnish", "English"};
    int valinta = JOptionPane.showOptionDialog(this,
            "Select language", "Language",
            0, JOptionPane.QUESTION_MESSAGE, null,
            kielet, "Finnish");

    if (valinta == 0) {
      Locale.setDefault(new Locale("fi", "FI"));
    } else if (valinta == 1) {
      Locale.setDefault(new Locale("en", "EN"));
    }
    this.valinta = valinta;
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

    Tervehdyskieli = new javax.swing.ButtonGroup();
    KenttaLajittelu = new javax.swing.ButtonGroup();
    jTextField1 = new javax.swing.JTextField();
    btnSulje = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    rbNouseva = new javax.swing.JRadioButton();
    rbLaskeva = new javax.swing.JRadioButton();
    btnSuorita = new javax.swing.JButton();
    jLabel2 = new javax.swing.JLabel();
    jTextField2 = new javax.swing.JTextField();
    rbKoodi = new javax.swing.JRadioButton();
    rbNimi = new javax.swing.JRadioButton();
    rbHinta = new javax.swing.JRadioButton();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jTextField1.setColumns(15);

    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("GraafinenUI3Tekstit"); // NOI18N
    btnSulje.setText(bundle.getString("CLOSE")); // NOI18N
    btnSulje.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnSuljeActionPerformed(evt);
      }
    });

    jLabel1.setText(bundle.getString("SOURCEFILE: ")); // NOI18N

    Tervehdyskieli.add(rbNouseva);
    rbNouseva.setText(bundle.getString("UP")); // NOI18N

    Tervehdyskieli.add(rbLaskeva);
    rbLaskeva.setText(bundle.getString("DOWN")); // NOI18N
    rbLaskeva.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        rbLaskevaActionPerformed(evt);
      }
    });

    btnSuorita.setText(bundle.getString("COMMIT")); // NOI18N
    btnSuorita.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnSuoritaActionPerformed(evt);
      }
    });

    jLabel2.setText(bundle.getString("XML-FILE:")); // NOI18N

    jTextField2.setColumns(15);

    KenttaLajittelu.add(rbKoodi);
    rbKoodi.setText(bundle.getString("CODE")); // NOI18N

    KenttaLajittelu.add(rbNimi);
    rbNimi.setText(bundle.getString("NAME")); // NOI18N

    KenttaLajittelu.add(rbHinta);
    rbHinta.setText(bundle.getString("PRICE")); // NOI18N

    jLabel3.setText(bundle.getString("SORTING ORDER")); // NOI18N

    jLabel4.setText(bundle.getString("FIELD")); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel1)
              .addComponent(jLabel2))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
          .addComponent(btnSulje))
        .addGap(39, 39, 39)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(rbNouseva)
          .addComponent(rbLaskeva)
          .addComponent(btnSuorita)
          .addComponent(jLabel3))
        .addGap(66, 66, 66)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel4)
          .addComponent(rbHinta)
          .addComponent(rbKoodi)
          .addComponent(rbNimi))
        .addContainerGap(57, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(25, 25, 25)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(jLabel4))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(rbNouseva)
              .addComponent(rbKoodi))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(rbLaskeva)
              .addComponent(rbNimi))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(rbHinta)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSuorita))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSulje)))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void btnSuljeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuljeActionPerformed
    // TODO add your handling code here:

    System.exit(0);
  }//GEN-LAST:event_btnSuljeActionPerformed

  private void btnSuoritaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuoritaActionPerformed
    // TODO add your handling code here:
    //    c:\\temp\tuotetiedot.txt

    TiedostoMuunninTest test = new TiedostoMuunninTest();
    String lajittelusuunta = null;
    String lajiteltavakentta = null;
    Boolean siirronTulos = false;

    if (rbNouseva.isSelected()) {
      lajittelusuunta = "nouseva";
    } else if (rbLaskeva.isSelected()) {
      lajittelusuunta = "laskeva";
    }

    if (rbKoodi.isSelected()) {
      lajiteltavakentta = "koodi";
    }
    if (rbNimi.isSelected()) {
      lajiteltavakentta = "nimi";
    }
    if (rbHinta.isSelected()) {
      lajiteltavakentta = "hinta";
    }

    try {
      test.tiedostomuunnin(jTextField1.getText(), jTextField2.getText(), lajiteltavakentta, lajittelusuunta);
      siirronTulos = test.onnistuikoMuunnos();
      if (siirronTulos) {
        if (valinta == 0) {
          JOptionPane.showMessageDialog(this, "Tietojen muuttaminen onnistui");
        }
        if (valinta == 1) {
          JOptionPane.showMessageDialog(this, "Transfer ok");
        }
      }
    } catch (FileNotFoundException ex) {
      Logger.getLogger(TiedostoGUI.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(TiedostoGUI.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      if (!siirronTulos) {
        if (valinta == 0) {
          JOptionPane.showMessageDialog(this, "Tietojen muuttaminen EI onnistunut");
        }
        if (valinta == 1) {
          JOptionPane.showMessageDialog(this, "Transfer NOT success");
        }
      }
    }

  }//GEN-LAST:event_btnSuoritaActionPerformed

  private void rbLaskevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbLaskevaActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_rbLaskevaActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws FileNotFoundException, IOException {

    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(TiedostoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(TiedostoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(TiedostoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(TiedostoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>


    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new TiedostoGUI().setVisible(true);

      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.ButtonGroup KenttaLajittelu;
  private javax.swing.ButtonGroup Tervehdyskieli;
  private javax.swing.JButton btnSulje;
  private javax.swing.JButton btnSuorita;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JTextField jTextField1;
  private javax.swing.JTextField jTextField2;
  private javax.swing.JRadioButton rbHinta;
  private javax.swing.JRadioButton rbKoodi;
  private javax.swing.JRadioButton rbLaskeva;
  private javax.swing.JRadioButton rbNimi;
  private javax.swing.JRadioButton rbNouseva;
  // End of variables declaration//GEN-END:variables
}