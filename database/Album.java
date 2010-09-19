/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jphotos.database;

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
public class Album extends Record{
  public int id = 0;
  public String album = "";

  public Album() {
  }

  public Album(int album_id) {
    if(album_id < 1){
      
    }
    try {
      ResultSet rs = Database.stmt.executeQuery("SELECT * FROM albums WHERE id = " + album_id);
      while (rs.next()) {
      this.id = album_id;
      this.album = rs.getString("album");
      }
      rs.close();
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
      
    }
  }

  public void save() {
    String sql = "";
    if(this.id == 0) {
      sql = "INSERT INTO albums (album) VALUES ('"+this.album+"')";
    } else {
      sql = "UPDATE albums SET album = '"+this.album+"' WHERE id="+this.id;
    }
    int i = query(sql);
    if(i>0){
      this.id = i;
    }
  }

  public static ArrayList<Album> getAlbums(){
    try {
      ArrayList<Album> albums = new ArrayList<Album>();
      String sql = "SELECT * FROM albums ORDER BY album";
      ResultSet rs = Database.stmt.executeQuery(sql);
      while(rs.next()){
        Album a = new Album();
        a.id = rs.getInt("id");
        a.album = rs.getString("album");
        albums.add(a);
      }
      return albums;
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
      return null;
    }


  }

  @Override
  public String toString() {
    return this.album;
  }



}
