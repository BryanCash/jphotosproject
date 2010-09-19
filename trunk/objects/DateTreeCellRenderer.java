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
public class DateTreeCellRenderer extends JPhotosTreeCellRenderer implements TreeCellRenderer {

  public static final long serialVersionUID = 534646765786987L;

  public DateTreeCellRenderer() {
    setOpaque(true);
    setLeafIcon(new ImageIcon(getClass().getResource("/jphotos/images/photo_icon.png")));
    setClosedIcon(new ImageIcon(getClass().getResource("/jphotos/images/calendar_collapsed.png")));
    setOpenIcon(new ImageIcon(getClass().getResource("/jphotos/images/calendar_expand.png")));
  }
}
