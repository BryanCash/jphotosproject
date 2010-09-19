/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jphotos.panes;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import jphotos.Photos;
import jphotos.database.Database;
import jphotos.objects.AlbumCellEditor;
import jphotos.objects.AlbumLeaf;
import jphotos.objects.AlbumTreeCellRenderer;

/**
 *
 * @author lordovol
 */
public class AlbumTreePanel extends TreePanel {

  public static final long serialVersionUID = 235345656456L;
  ArrayList<AlbumLeaf> albumModel = new ArrayList<AlbumLeaf>();
  private AlbumLeaf selectedLeaf;
  public final static String SELECTED_ALBUM = "selected album";

  public AlbumTreePanel() {
    super();
    tree.addMouseListener(new java.awt.event.MouseAdapter() {

      @Override
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        treeMouseReleased(evt);
      }
    });
    setCellRenderer(new AlbumTreeCellRenderer());
    tree.setCellEditor(new AlbumCellEditor());
    tree.setEditable(true);

}

  @Override
  public void populate(int count) {
    String sql = "SELECT albums.id AS id ,  album , count(files.id) as count FROM files JOIN albums ON files.album_id = albums.id GROUP BY album_id ORdER BY album ";
    try {
      ResultSet rs = Database.stmt.executeQuery(sql);
      while (rs.next()) {
        AlbumLeaf a = new AlbumLeaf();
        a.id = rs.getInt("id");
        a.count = rs.getInt("count");
        a.album = rs.getString("album");
        albumModel.add(a);
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
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Άλμπουμς");
    for (Iterator<AlbumLeaf> it = albumModel.iterator(); it.hasNext();) {
      AlbumLeaf albumLeaf = it.next();
      DefaultMutableTreeNode albumNode = new DefaultMutableTreeNode(albumLeaf);
      root.add(albumNode);
    }
    return root;
  }

  private void treeMouseReleased(java.awt.event.MouseEvent evt) {
    DefaultMutableTreeNode node;
    Date dateObj;
    int date, month, year;
    DefaultMutableTreeNode parNode, gParNode;
    Month monthObj;
    Year yearObj;
    if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1) {
      Point p = evt.getPoint();
      TreePath selectedPath = tree.getClosestPathForLocation(p.x, p.y);
      if (tree.getPathBounds(selectedPath).contains(p)) {
        tree.setSelectionPath(selectedPath);
        node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node.isLeaf()) {
          AlbumLeaf newValue = (AlbumLeaf) node.getUserObject();
          AlbumLeaf oldValue = selectedLeaf;
          firePropertyChange(SELECTED_ALBUM, oldValue, newValue);
          selectedLeaf = newValue;
        }

      }
    }
  }
}
