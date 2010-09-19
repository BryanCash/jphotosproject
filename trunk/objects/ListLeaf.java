/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jphotos.objects;

/**
 *
 * @author lordovol
 */
public class ListLeaf {

  public int id;
  public String date;
  public int count;

  @Override
  public String toString() {
    return date + " (" + count + ")";
  }
}
