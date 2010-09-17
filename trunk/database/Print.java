/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jphotos.database;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jphotos.Photos;

/**
 *
 * @author lordovol
 */
public class Print extends Record {


  public int id = 0;
  public int file_id;
  public int  list_id;

  public Print() {
  }

  public void save() {
    String sql = "";
    if(this.id == 0) {
    sql = "INSERT INTO prints (file_id,list_id) VALUES ("+file_id+","+list_id+")";
    }
    int i = query(sql);
    if(i>0){
      this.id = i;
    }
  }

  public static void deleteByListId(int id) {
    String sql = "DELETE FROM prints WHERE list_id = "+id;
    try {
      Database.stmt.executeUpdate(sql);
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
    }
  }

  public boolean delete() {
    String sql = "DELETE FROM prints WHERE id = "+this.id;
    try {
      Database.stmt.executeUpdate(sql);
      return true;
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
      return false;
    }
  }

  
}
