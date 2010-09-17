/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jphotos.objects;

/**
 *
 * @author lordovol
 */
public class DateObj {
  private final String date;
  private final int count;

  public DateObj(String date, int count) {
    this.date = date;
    this.count = count;
  }

  @Override
  public String toString() {
    return this.date+ " (" + this.count +")";
  }



}
