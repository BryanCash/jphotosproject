/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jphotos.objects;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import jphotos.database.Album;

/**
 *
 * @author lordovol
 */
public class AlbumCellEditor extends DefaultCellEditor {

  public static final long serialVersionUID = 2324342L;
  private AlbumLeaf a;

  public AlbumCellEditor() {
    super(new JTextField());
 
  }

 
  @Override
  public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
    if(value instanceof DefaultMutableTreeNode){
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
      a = (AlbumLeaf) node.getUserObject();
      value = a.album;
    }
    return super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);
  }

    @Override
  public boolean stopCellEditing() {
    String val = ((JTextField) editorComponent).getText();
    Album album = new Album(a.id);
    album.album = val;
    album.save();
    return super.stopCellEditing();
  }
}
