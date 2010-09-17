/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jphotos.tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import jphotos.Photos;
import jphotos.database.Database;

/**
 *
 * @author lordovol
 */
public class Options {
  public static final String USER_DIR = "./";
  public static final String DATABASE ="jphotos";
  public static final String FIELD_ROOT = "root";
  public static String ROOT;

  public static void getOptions() {
    try {
      ResultSet rs = Database.stmt.executeQuery("SELECT * FROM options");
      while (rs.next()) {
        ROOT = rs.getString(FIELD_ROOT);
      }
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
    }
  }

  public static void save(String field, String value) {
    try {
      Database.stmt.executeUpdate("UPDATE options SET " + field + " = '" + value + "'");
      getOptions();
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
    }
  }

  private Options() {
  }

}
