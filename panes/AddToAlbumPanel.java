/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AddToAlbumPanel.java
 *
 * Created on 17 Σεπ 2010, 8:05:16 μμ
 */
package jphotos.panes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import jphotos.database.Album;
import jphotos.database.Database;
import jphotos.database.FileRecord;
import jphotos.tools.MyDraggable;

/**
 *
 * @author lordovol
 */
public class AddToAlbumPanel extends MyDraggable {

    public static final long serialVersionUID = 124234534656L;
    private ArrayList<Album> albums;
    ComboBoxModel albumModel = new DefaultComboBoxModel();
    private ArrayList<PhotoPanel> photos;

    /** Creates new form AddToAlbumPanel */
    public AddToAlbumPanel() {
        this(null);
    }

    AddToAlbumPanel(ArrayList<PhotoPanel> photos) {
        this.photos = photos;
        albums = Album.getAlbums();
        albumModel = new DefaultComboBoxModel(albums.toArray());
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    sTextField1 = new com.googlecode.svalidators.formcomponents.STextField();
    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    bt_ok = new javax.swing.JButton();
    bt_cancel = new javax.swing.JButton();
    l_exAlbum = new javax.swing.JLabel();
    c_album = new javax.swing.JComboBox();
    jLabel2 = new javax.swing.JLabel();
    tf_newAlbum = new javax.swing.JTextField();

    sTextField1.setText("sTextField1");

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

    jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

    jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize()+3));
    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setText("Προσθήκη σε Αλμπουμ");

    bt_ok.setText("ΟΚ");
    bt_ok.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bt_okActionPerformed(evt);
      }
    });

    bt_cancel.setText("Ακύρωση");
    bt_cancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bt_cancelActionPerformed(evt);
      }
    });

    l_exAlbum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    l_exAlbum.setText("Υπάρχον άλμπουμ:");

    c_album.setModel(albumModel);

    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel2.setText("Νέο άλμπουμ:");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(l_exAlbum, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(bt_ok)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_cancel)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(tf_newAlbum)
              .addComponent(c_album, 0, 166, Short.MAX_VALUE))))
        .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabel1)
        .addGap(18, 18, 18)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(l_exAlbum)
          .addComponent(c_album, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(tf_newAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(18, 18, 18)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(bt_cancel)
          .addComponent(bt_ok))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void bt_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cancelActionPerformed
        dispose();
    }//GEN-LAST:event_bt_cancelActionPerformed

    private void bt_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_okActionPerformed
        int album_id = 0;
        if (tf_newAlbum.getText().trim().equals("")) {
            Album a = (Album) c_album.getSelectedItem();
            album_id = a.id;
        } else {
            String albumName = tf_newAlbum.getText().trim();
            Album a = new Album();
            a.album = albumName;
            a.save();
            album_id = a.id;
        }
        if (album_id > 0) {
            try {
                Database.stmt.execute("BEGIN TRANSACTION");
                for (Iterator<PhotoPanel> it = photos.iterator(); it.hasNext();) {
                    PhotoPanel photoPanel = it.next();
                    FileRecord file = photoPanel.fileRecord;
                    file.album_id = album_id;
                    file.save();
                }
                Database.stmt.execute("END TRANSACTION");
            } catch (SQLException ex) {
                Logger.getLogger(AddToAlbumPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dispose();
    }//GEN-LAST:event_bt_okActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton bt_cancel;
  private javax.swing.JButton bt_ok;
  private javax.swing.JComboBox c_album;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JLabel l_exAlbum;
  private com.googlecode.svalidators.formcomponents.STextField sTextField1;
  private javax.swing.JTextField tf_newAlbum;
  // End of variables declaration//GEN-END:variables
}
