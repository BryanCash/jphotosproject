/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jphotos.panes;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import jphotos.Photos;
import jphotos.database.Database;
import jphotos.database.List;
import jphotos.objects.AlbumLeaf;
import jphotos.objects.AlbumTreeCellRenderer;
import jphotos.objects.ListLeaf;
import jphotos.objects.ListTreeCellRenderer;
import jphotos.tools.Tools;

/**
 *
 * @author lordovol
 */
public class ListTreePanel extends TreePanel {

  public static final long serialVersionUID = 235345656456L;
  ArrayList<ListLeaf> listModel = new ArrayList<ListLeaf>();
  private ListLeaf selectedLeaf;
  private JPopupMenu popup;

  public ListTreePanel() {
    super();
    tree.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseReleased(java.awt.event.MouseEvent evt) {
        treeMouseReleased(evt);
      }
    });
    setCellRenderer(new ListTreeCellRenderer());
    popup = new JPopupMenu();
    JMenuItem delete = new JMenuItem("Διαγραφή");
    delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
    popup.add(delete);
    popup.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseExited(MouseEvent e) {
        popup.setVisible(false);
      }

    });
    delete.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseReleased(MouseEvent e) {
        if(Tools.ask("Διαγραφή λίστας", "Θέλετε να διαγράψετε τη λίστα;") == JOptionPane.YES_OPTION){
          List.deleteById(selectedLeaf.id);
          populate(0);
          firePropertyChange(DELETED_LIST, null, null);
        };
        popup.setVisible(false);
      }
      
    });
  }

  @Override
  public void populate(int count) {
    listModel.clear();
    String sql = "SELECT lists.id AS id ,  date , count(prints.id) as count FROM lists JOIN prints ON prints.list_id = lists.id GROUP BY id ORDER BY date DESC";
    try {
      ResultSet rs = Database.stmt.executeQuery(sql);
      while (rs.next()) {
        ListLeaf l = new ListLeaf();
        l.id = rs.getInt("id");
        l.count = rs.getInt("count");
        l.date = rs.getString("date");
        listModel.add(l);
      }
      DefaultMutableTreeNode root = createTree();
      treemodel = new DefaultTreeModel(root);
      tree.setModel(treemodel);
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected DefaultMutableTreeNode createTree() throws SQLException {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Λίστες");
    for (Iterator<ListLeaf> it = listModel.iterator(); it.hasNext();) {
      ListLeaf listLeaf = it.next();
      DefaultMutableTreeNode listNode = new DefaultMutableTreeNode(listLeaf);
      root.add(listNode);
    }
    return root;
  }

  private void treeMouseReleased(java.awt.event.MouseEvent evt) {
    DefaultMutableTreeNode node;
    if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1) {
      Point p = evt.getPoint();
      TreePath selectedPath = tree.getClosestPathForLocation(p.x, p.y);
      if (tree.getPathBounds(selectedPath).contains(p)) {
        tree.setSelectionPath(selectedPath);
        node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node.isLeaf()) {
          ListLeaf newValue = (ListLeaf) node.getUserObject();
          ListLeaf oldValue = selectedLeaf;
          firePropertyChange(SELECTED_LIST, oldValue, newValue);
          selectedLeaf = newValue;
        }

      }
    } else if (evt.getButton() == MouseEvent.BUTTON3) {
      Point p = evt.getPoint();
      TreePath selectedPath = tree.getClosestPathForLocation(p.x, p.y);
      if (tree.getPathBounds(selectedPath).contains(p)) {
        tree.setSelectionPath(selectedPath);
        node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node.isLeaf()) {
          selectedLeaf = (ListLeaf) node.getUserObject();
          popup.setLocation(evt.getXOnScreen()-10, evt.getYOnScreen()-10);
          popup.setVisible(true);

        }

      }
    }
  }
}
