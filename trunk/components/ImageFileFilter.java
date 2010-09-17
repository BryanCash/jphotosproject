/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jphotos.components;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author lordovol
 */
public class ImageFileFilter implements FileFilter{

  public boolean accept(File pathname) {
    return pathname.isFile() && pathname.getName().toLowerCase().endsWith(".jpg");
  }

}
