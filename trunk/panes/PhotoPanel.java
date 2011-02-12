/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PhotoPanel.java
 *
 * Created on 5 Σεπ 2010, 10:44:29 μμ
 */
package jphotos.panes;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import jphotos.Photos;
import jphotos.database.Album;
import jphotos.database.FileRecord;
import jphotos.tools.Tools;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.common.byteSources.ByteSourceFile;
import org.apache.sanselan.formats.jpeg.JpegImageParser;
import org.apache.sanselan.formats.tiff.JpegImageData;
import org.apache.sanselan.formats.tiff.TiffDirectory;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;

/**
 *
 * @author lordovol
 */
public class PhotoPanel extends javax.swing.JPanel implements Runnable {

  public static final int gridSize = 160;
  public static final long serialVersionUID = 235346345645L;
  public static final Color BORDER_HIGHLIGHT_COLOR = Color.BLACK;
  public static final Color BORDER_MEDIUM_COLOR = Color.LIGHT_GRAY;
  public static final int BORDER_WIDTH = 3;
  public static final int ICON_WIDTH = 160;
  private ScrollableFlowPanel photoPanel;
  public FileRecord fileRecord;
  private int index;

  /** Creates new form PhotoPanel */
  public PhotoPanel() {
    initComponents();
  }

  PhotoPanel(ScrollableFlowPanel photoPanel, FileRecord fileRecord, int index) {
    this.fileRecord = fileRecord;
    this.index = index;
    this.photoPanel = photoPanel;
    initComponents();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    label_album = new javax.swing.JLabel();
    label_photo = new javax.swing.JLabel();
    check = new javax.swing.JCheckBox();

    setBackground(new java.awt.Color(255, 255, 255));
    setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    label_album.setBackground(new java.awt.Color(255, 255, 255));
    label_album.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    label_album.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    label_album.setText("jLabel1");
    label_album.setOpaque(true);

    label_photo.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
    label_photo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    label_photo.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        label_photoMouseEntered(evt);
      }
      public void mouseExited(java.awt.event.MouseEvent evt) {
        label_photoMouseExited(evt);
      }
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        label_photoMouseReleased(evt);
      }
    });

    check.setOpaque(false);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(label_album, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(check))
          .addComponent(label_photo, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(label_photo, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
        .addGap(6, 6, 6)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
          .addComponent(label_album)
          .addComponent(check))
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

  private void label_photoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_photoMouseEntered
    label_photo.setBorder(BorderFactory.createLineBorder(BORDER_MEDIUM_COLOR, BORDER_WIDTH));
  }//GEN-LAST:event_label_photoMouseEntered

  private void label_photoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_photoMouseExited
    label_photo.setBorder(BorderFactory.createEmptyBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));
  }//GEN-LAST:event_label_photoMouseExited

  public void selectPhoto() {
    Photos p = (Photos) getTopLevelAncestor();
    Component[] c = p.gridPanel.photoPanel.getComponents();
    for (int i = 0; i < c.length; i++) {
      Component component = c[i];
      component.setBackground(Color.WHITE);
    }
    Photos.selectedIndex = index;
    label_photo.setBorder(BorderFactory.createLineBorder(BORDER_HIGHLIGHT_COLOR, BORDER_WIDTH));
    setBackground(BORDER_MEDIUM_COLOR);
    p.previewPanel.setImage(fileRecord);
  }

  private void label_photoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_photoMouseReleased
    selectPhoto();
  }//GEN-LAST:event_label_photoMouseReleased
  // Variables declaration - do not modify//GEN-BEGIN:variables
  public javax.swing.JCheckBox check;
  private javax.swing.JLabel label_album;
  private javax.swing.JLabel label_photo;
  // End of variables declaration//GEN-END:variables

  public void run() {
    try {
      File photo = new File(fileRecord.path);
      if(!photo.exists()){
        return;
      }
      label_photo.setToolTipText(fileRecord.toString());
      label_album.setText(fileRecord.album);
      label_album.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      JpegImageParser parser = new JpegImageParser();
      ByteSourceFile bytesource = new ByteSourceFile(photo);
      HashMap params = new HashMap();
      TiffImageMetadata d = parser.getExifMetadata(bytesource, params);
      TiffDirectory dir = d.findDirectory(TiffImageMetadata.DIRECTORY_TYPE_SUB);
      JpegImageData im = dir.getJpegImageData();
      ImageIcon ic = new ImageIcon(im.data);
      label_photo.setIcon(ic);
    } catch (Exception ex) {
      ImageIcon im = Tools.getImage(fileRecord, ICON_WIDTH, this);
      label_photo.setIcon(im);
      Photos.logger.log(Level.WARNING, "Could not read exif thumbnail from {0}", fileRecord.path);
    } finally{
      photoPanel.add(this);
      photoPanel.revalidate();
      photoPanel.repaint();
    }
  }
}