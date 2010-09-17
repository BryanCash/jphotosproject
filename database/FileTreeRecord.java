/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jphotos.database;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import jphotos.Photos;

/**
 *
 * @author lordovol
 */
public class FileTreeRecord {

  public int id;
  public String path;
  public File[] subdirs;
  public File[] files;
  public int subdirCount = 0;
  public int fileCount = 0;

  public FileTreeRecord(File path, File[] subdirs, File[] files) {
    this.path = path.getAbsolutePath();
    this.subdirs = subdirs;
    this.subdirCount = subdirs.length;
    this.fileCount = files.length;
    this.files = files;
  }

  public FileTreeRecord() {
    
  }

  public void save() {
    String sql = "INSERT INTO filetree (path, subdirs,files) VALUES ('" + this.path + "'," + this.subdirCount + "," + this.fileCount + ")";
    try {
      Database.stmt.executeUpdate(sql);
      Photos.logger.log(Level.FINE, "Path inserted: \"{0}\"", this.path);
    } catch (SQLException ex) {
      if (ex.getMessage().equals("column path is not unique")) {
        Photos.logger.log(Level.WARNING, "Path already exists: \"{0}\"", this.path);
      } else {
        Photos.logger.log(Level.SEVERE, "SQL error", ex);
      }
    }
  }

  @Override
  public String toString() {
    return path+"["+subdirCount+"]["+fileCount+"]";
  }


}
