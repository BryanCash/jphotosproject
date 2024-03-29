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

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import jphotos.objects.DateTreeCellRenderer;
import jphotos.objects.TreeDate;
import jphotos.Photos;
import jphotos.database.Database;
import jphotos.tools.Tools;

/**
 *
 * @author lordovol
 */
public class DateTreePanel extends TreePanel{
  private static final long serialVersionUID = 25324534535345L;
  Tree dateTree = new Tree();
  TreeDate treeDate;
  

  /** Creates new form TreePanel */
  public DateTreePanel() {
    initComponents();
    setCellRenderer(new DateTreeCellRenderer());
  }

  public void populate(int count) {
    try {
      String sql;
      String where  = Photos.FAVORITES ? " WHERE favorite = 1 " : "";
      if(count ==0){
        count = 100000;
      sql = "SELECT year , month ,date, count(*)  as count "
              + "FROM files " +  where + " group by year,month,date  "
              + "ORDER BY year desc, month, date ";
      } else {
       sql = "SELECT year , month ,date, count(*)  as count "
              + "FROM files " +  where + " group by year,month,date  "
              + "ORDER BY inserted desc,  year desc, month, date ";
      }
      ResultSet rs = Database.stmt.executeQuery(sql);
      dateTree.years = new ArrayList<Year>();
      while (rs.next()) {
        int y = rs.getInt("year");
        int m = rs.getInt("month");
        int d = rs.getInt("date");
        int c = rs.getInt("count");
        count -= c;
        Date date = new Date(d, c);
        Month month = new Month(m, c);
        Year year = new Year(y, c);
        dateTree.addYear(year);
        Year cYear = dateTree.years.get(dateTree.years.size() - 1);
        cYear.addMonth(month);
        cYear.months.get(cYear.months.size() - 1).addDate(date);
        if(count < 0 ){
          break;
        }
      }

      DefaultMutableTreeNode root = createTree();
      treemodel = new DefaultTreeModel(root);
      tree.setModel(treemodel);
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
    }
  }

  protected DefaultMutableTreeNode createTree() throws SQLException {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Ημερομηνία");
    DefaultMutableTreeNode yearNode = null;
    DefaultMutableTreeNode monthNode = null;
    DefaultMutableTreeNode dateNode = null;
    for (Iterator<Year> it = dateTree.years.iterator(); it.hasNext();) {
      Year year = it.next();
      yearNode = new DefaultMutableTreeNode(year);
      root.add(yearNode);
      for (Iterator<Month> it2 = year.months.iterator(); it2.hasNext();) {
        Month month = it2.next();
        monthNode = new DefaultMutableTreeNode(month);
        yearNode.add(monthNode);
        for (Iterator<Date> it3 = month.dates.iterator(); it3.hasNext();) {
          Date date = it3.next();
          dateNode = new DefaultMutableTreeNode(date);
          monthNode.add(dateNode);

        }
      }
    }
    return root;

  }

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
    tree.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        treeMouseReleased(evt);
      }
    });
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

  private void treeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeMouseReleased
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
          dateObj = (Date) node.getUserObject();
          date = dateObj.value;
          parNode = (DefaultMutableTreeNode) tree.getSelectionPath().getParentPath().getLastPathComponent();
          monthObj = (Month) parNode.getUserObject();
          month = monthObj.value;
          gParNode = (DefaultMutableTreeNode) parNode.getParent();
          yearObj = (Year) gParNode.getUserObject();
          year = yearObj.value;
          TreeDate oldVal = treeDate;
          TreeDate newValue = new TreeDate(year, month, date);
          firePropertyChange(SELECTED_DATE, oldVal, newValue);
        }

      }
    }
  }//GEN-LAST:event_treeMouseReleased

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JScrollPane scroll_tree;
  private javax.swing.JTree tree;
  // End of variables declaration//GEN-END:variables

  public void setCellRenderer(DateTreeCellRenderer dateTreeCellRenderer) {
    tree.setCellRenderer(dateTreeCellRenderer);
  }
}

class Tree {

  ArrayList<Year> years = new ArrayList<Year>();

  void addYear(Year year) {
    if (hasYear(year)) {
    } else {
      years.add(year);
    }
  }

  private boolean hasYear(Year y) {
    for (Iterator<Year> it = years.iterator(); it.hasNext();) {
      Year year = it.next();
      if (year.value == y.value) {
        year.count += y.count;
        return true;
      }
    }
    return false;
  }
}

class Year {

  int value;
  int count;
  ArrayList<Month> months = new ArrayList<Month>();

  Year(int value, int count) {
    this.value = value;
    this.count = count;
  }

  @Override
  public String toString() {
    return String.valueOf(value) + " (" + count + ")";
  }

  void addMonth(Month month) {
    if (hasMonth(month)) {
    } else {
      months.add(month);
    }
  }

  private boolean hasMonth(Month m) {
    for (Iterator<Month> it = months.iterator(); it.hasNext();) {
      Month month = it.next();
      if (month.value == m.value) {
        month.count += m.count;
        return true;
      }
    }
    return false;
  }
}

class Month {

  int value;
  int count;
  ArrayList<Date> dates = new ArrayList<Date>();

  Month(int value, int count) {
    this.value = value;
    this.count = count;

  }

  void addDate(Date date) {
    if (hasDate(date)) {
    } else {
      dates.add(date);
    }
  }

  @Override
  public String toString() {
    return String.valueOf(Tools.monthName(value)) + " (" + count + ")";
  }

  private boolean hasDate(Date d) {
    for (Iterator<Date> it = dates.iterator(); it.hasNext();) {
      Date date = it.next();
      if (date.value == d.value) {
        date.count += d.count;
        return true;
      }
    }
    return false;
  }
}

class Date {

  int value;
  int count;

  Date(int value, int count) {
    this.value = value;
    this.count = count;

  }

  @Override
  public String toString() {
    return String.valueOf(value) + " (" + count + ")";
  }
}
