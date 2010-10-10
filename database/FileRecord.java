/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jphotos.database;

import java.io.File;
import java.util.Calendar;
import java.util.logging.Level;
import jphotos.Photos;
import jphotos.tools.Tools;

/**
 *
 * @author lordovol
 */
public class FileRecord extends Record {

  public static final int NO_FAVORITE = 0;
  public static final int FAVORITE = 1;
  public int id;
  public String path;
  public long created = 0L;
  public int year = 0;
  public int month = 0;
  public int date = 0;
  public String inserted = "";
  public int album_id = 0;
  public String album = "";
  public int favorite = 0;

  public FileRecord(String path, Calendar created) {
    this.path = path;
    if (created != null) {
      this.created = created.getTimeInMillis();
      this.year = created.get(Calendar.YEAR);
      this.month = created.get(Calendar.MONTH) + 1;
      this.date = created.get(Calendar.DATE);
    }
  }

  public void save() {
    String sql;
    if (this.id == 0) {
      sql = "INSERT INTO files (path,created,year,month,date,`inserted`,`album_id`,`favorite`) VALUES "
              + "('" + this.path + "'," + this.created + "," + year + "," + month + ", " + date + ", '" + Tools.getNow() + "'," + this.album_id + "," + 0 + " )";
    } else {
      sql = "UPDATE files SET path = '" + this.path + "', created = " + this.created
              + ", year =" + this.year + ", month = " + this.month + ", date = " + this.date + ", album_id =" + this.album_id + ", favorite = " + this.favorite + " WHERE id=" + this.id;
    }
    int i = query(sql);
    if (i > 0) {
      this.id = i;
    }
    Photos.logger.log(Level.FINE, "File inserted: \"{0}\"", this.path);

  }

  @Override
  public String toString() {
    return new File(this.path).getName();

  }

  public String getDate() {
    return date + "/" + month + "/" + year;
  }
}
