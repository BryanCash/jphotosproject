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

  public int id;
  public String path;
  public long created;
  private final int year;
  private final int month;
  private final int date;
  public String inserted;

  public FileRecord(String path, Calendar created) {
    this.path = path;
    if (created != null) {
      this.created = created.getTimeInMillis();
      this.year = created.get(Calendar.YEAR);
      this.month = created.get(Calendar.MONTH) + 1;
      this.date = created.get(Calendar.DATE);
    } else {
      this.created = 0L;
      this.year = 0;
      this.month = 0;
      this.date = 0;
    }
  }

  public void save() {
    String sql;
    if (this.id == 0) {
      sql = "INSERT INTO files (path,created,year,month,date,`inserted`) VALUES "
              + "('" + this.path + "'," + this.created + "," + year + "," + month + ", " + date + ", '" + Tools.getNow() + "' )";
    } else {
      sql = "UPDATE files SET path = '" + this.path + "', created = " + this.created
              + ", year =" + this.year + ", month = " + this.month + ", date = " + this.date;
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
}
