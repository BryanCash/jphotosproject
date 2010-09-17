/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jphotos.objects;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import jphotos.tools.Skin;

/**
 *
 * @author ΔΙΟΝΥΣΗΣ
 */
public class DateTreeCellRenderer  extends DefaultTreeCellRenderer implements TreeCellRenderer {
  public static final long serialVersionUID = 534646765786987L;
  public DateTreeCellRenderer() {
    setOpaque(true);
    setLeafIcon(new ImageIcon(getClass().getResource("/jphotos/images/photo_icon.png")));
    setClosedIcon(new ImageIcon(getClass().getResource("/jphotos/images/calendar_collapsed.png")));
    setOpenIcon(new ImageIcon(getClass().getResource("/jphotos/images/calendar_expand.png")));
  }

  @Override
   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
    super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
    if(selected){
      setForeground(Color.BLACK);
      setBackground(Skin.getSkinColor());
      setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    } else {
      setForeground(Color.BLACK);
      setBackground(null);
      setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    }
    return this;
  }


}
