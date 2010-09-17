/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jphotos.database;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jphotos.Photos;

/**
 *
 * @author lordovol
 */
public class List extends Record {





  public int id = 0;
  public String date;

  public List() {
  }

  public void save() {
    String sql = "";
    if(this.id == 0) {
    sql = "INSERT INTO lists (date) VALUES ('"+date+"')";
    }
    int i = query(sql);
    if(i>0){
      this.id = i;
    }
  }

  public static void deleteById(int id) {
    List l = new List();
    l.id = id;
    l.delete();
    Print.deleteByListId(id);
  }

  public boolean delete() {
    String sql = "DELETE FROM lists WHERE id = "+this.id;
    try {
      Database.stmt.executeUpdate(sql);
      return true;
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
      return false;
    }
  }

  public static ArrayList<File> getListFiles() {
    ArrayList<File> files = new ArrayList<File>();
    String sql = "SELECT path FROM prints JOIN files on prints.file_id = files.id WHERE list_id ="+Photos.curListId;
    ResultSet rs;
    try {
      rs = Database.stmt.executeQuery(sql);
      while (rs.next()){
        File f = new File(rs.getString("path"));
        files.add(f);
      }
      return files;
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
      return files;
    }

  }



  
}

