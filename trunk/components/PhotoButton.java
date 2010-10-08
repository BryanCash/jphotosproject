/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jphotos.components;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author lordovol
 */
public class PhotoButton extends JButton {
  public static final int ROTATE_LEFT = 0;
  public static final int ROTATE_RIGHT = 1;
  public static final int PRINT = 2;
  public static final int STAR_ADD = 3;
  public static final int STAR_REMOVE = 4;

  public static String[] icons = {"rotate_left","rotate_right","add","star_add","star_remove"};
  public static String[] tooltips = {"Περιστροφή προς τα αριστερά","Περιστροφή προς τα δεξιά","Προσθήκη στη λίστα",
  "Προσθήκη στα αγαπημένα","Αφίρεση από αγαπημένα"};
  public static int buttons=0;
  public int type;

  
  public PhotoButton(int type) {
    this.type = type;
    ImageIcon ic = new ImageIcon(getClass().getResource("/jphotos/images/"+icons[type]+".png"));
    setIcon(ic);
    setCursor(new Cursor(Cursor.HAND_CURSOR));
    setOpaque(true);
    setBackground(Color.WHITE);
    setBounds(10 +(buttons*50)+((buttons+1)*15), 20, 50, 38);
    setFocusPainted(false);
    setToolTipText(tooltips[type]);
    buttons ++;
  }
}
