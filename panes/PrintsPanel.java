/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InfoPanel.java
 *
 * Created on 2 Σεπ 2010, 6:12:03 μμ
 */
package jphotos.panes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import jphotos.Photos;
import jphotos.database.FileRecord;
import jphotos.database.Print;
import jphotos.tools.Tools;

/**
 *
 * @author lordovol
 */
public class PrintsPanel extends JPanel {

  private ScrollableFlowPanel photoPanel;
  private JScrollPane panel;
  private FileRecord fileRecord;
  private Print print;

  /** Creates new form InfoPanel */
  public PrintsPanel() {
    initComponents();
    createPanels();

  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    setMaximumSize(new java.awt.Dimension(500, 32767));
    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(java.awt.event.ComponentEvent evt) {
        formComponentResized(evt);
      }
    });
    addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
      public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
      }
      public void ancestorResized(java.awt.event.HierarchyEvent evt) {
        formAncestorResized(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 400, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 300, Short.MAX_VALUE)
    );
  }// </editor-fold>//GEN-END:initComponents

  private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    validate();
    repaint();

    setPanelSize();
  }//GEN-LAST:event_formComponentResized

  private void formAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_formAncestorResized
    validate();
    repaint();

    setPanelSize();
  }//GEN-LAST:event_formAncestorResized

  public void addPhoto(FileRecord fileRecord) {
    this.fileRecord = fileRecord;
    int id = fileRecord.id;
    String now = Tools.getNow();
    Print print = new Print();
    print.file_id = id;
    Photos.curList.add(print);
    Photos.logger.log(Level.INFO, "Photo {0} added to prints", fileRecord.path);
    Photos.isCurListSaved = false;
    this.print = print;
    PhotoPanelThumb p = new PhotoPanelThumb(photoPanel, fileRecord, print);
    photoPanel.add(p);
    p.addPropertyChangeListener(new PropertyChangeListener() {

      public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PhotoPanelThumb.THUMB_REMOVED)) {
          Photos photos = (Photos) getTopLevelAncestor();
          photos.tree.populate(0);
        }
      }
    });
    validate();
    repaint();

    setPanelSize();
  }

  public void addPhotos(ArrayList<FileRecord> photos) {
      for (Iterator<FileRecord> it = photos.iterator(); it.hasNext();) {
      FileRecord f = it.next();
      addPhoto(f);
    }

  }

  public void clearPrints() {
    photoPanel.removeAll();
    validate();
    repaint();

    setPanelSize();
  }

  private void setPanelSize() {
    panel.setPreferredSize(new Dimension(getWidth() - 20, getHeight() - 10));
    validate();
    repaint();
  }

  private void createPanels() {
    photoPanel = new ScrollableFlowPanel();
    panel = new JScrollPane(photoPanel);
    panel.getViewport().setOpaque(false);
    panel.setOpaque(false);
    photoPanel.setOpaque(false);
    panel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    add(panel);
    photoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    validate();
    repaint();
    
    setPanelSize();
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables
}
