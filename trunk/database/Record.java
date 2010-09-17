/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jphotos.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lordovol
 */
public class Record {

  protected int query(String sql){
    try {
      Database.stmt.executeUpdate(sql);
      ResultSet rs = Database.stmt.executeQuery("SELECT last_insert_rowid() AS id");
      if (rs.next()) {
        return rs.getInt("id");
      } else {
        return  -1;
      }
    } catch (SQLException ex) {
      Logger.getLogger(Record.class.getName()).log(Level.SEVERE, null, ex);
      return -1;
    }
  }

}
