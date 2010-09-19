/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TreePanel.java
 *
 * Created on 2 Σεπ 2010, 6:10:03 μμ
 */
package jphotos.panes;

import java.sql.SQLException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author lordovol
 */
public abstract class TreePanel extends javax.swing.JPanel {
  private static final long serialVersionUID = 456567585858L;
  DefaultTreeModel treemodel = new DefaultTreeModel(null);

  public TreePanel() {
    initComponents();
  }


  
  public abstract void populate(int count);

  protected abstract DefaultMutableTreeNode createTree() throws SQLException;

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    scroll_tree = new javax.swing.JScrollPane();
    tree = new javax.swing.JTree();

    tree.setModel(treemodel);
    tree.setAutoscrolls(true);
    tree.setFocusable(false);
    tree.setRowHeight(22);
    scroll_tree.setViewportView(tree);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(scroll_tree, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(scroll_tree, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
    );
  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JScrollPane scroll_tree;
  protected javax.swing.JTree tree;
  // End of variables declaration//GEN-END:variables

  public void setCellRenderer(DefaultTreeCellRenderer renderer) {
    tree.setCellRenderer(renderer);
  }
}

