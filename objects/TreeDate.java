/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jphotos.objects;

/**
 *
 * @author lordovol
 */
public class TreeDate {

  public int year;
  public int month;
  public int date;

  public TreeDate(int year, int month, int date) {
    this.year = year;
    this.month = month;
    this.date = date;
  }

  @Override
  public String toString() {
    return year + " " + month + " " + date;
  }
}
