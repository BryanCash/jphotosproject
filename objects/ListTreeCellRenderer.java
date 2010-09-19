/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jphotos.objects;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author ΔΙΟΝΥΣΗΣ
 */
public class ListTreeCellRenderer extends JPhotosTreeCellRenderer implements TreeCellRenderer {

  private static final long serialVersionUID = 534646765786987L;

  public ListTreeCellRenderer() {
    setOpaque(true);
    setLeafIcon(new ImageIcon(getClass().getResource("/jphotos/images/list_icon.png")));
    setClosedIcon(new ImageIcon(getClass().getResource("/jphotos/images/list_collapse.png")));
    setOpenIcon(new ImageIcon(getClass().getResource("/jphotos/images/list_expand.png")));
  }

}
