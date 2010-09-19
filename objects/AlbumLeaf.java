/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jphotos.objects;

/**
 *
 * @author lordovol
 */
public class AlbumLeaf {

  public int id;
  public String album;
  public int count;

  @Override
  public String toString() {
    return album + " (" + count + ")";
  }
}
