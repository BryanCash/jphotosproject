/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jphotos.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import jphotos.Photos;
import jphotos.tools.Options;

/**
 *
 * @author lordovol
 */
public class Database {

  public static Connection conn;
  public static Statement stmt;

  public static void createConnection(String db) {
    try {
      if (!databaseExists(db)) {
        createDb(db);
      } else {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + Options.USER_DIR + "/" + db + ".db");
        stmt = conn.createStatement();
      }
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, "Could not connect to the SQLite database", ex);
    } catch (ClassNotFoundException ex) {
      Photos.logger.log(Level.SEVERE, "Could not find SQLite class", ex);
    }
  }

  public static boolean createDb(String db) throws ClassNotFoundException {
    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:" + Options.USER_DIR + "/" + db + ".db");
      stmt = conn.createStatement();
      String files = "CREATE TABLE `files` ( `id` INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,`path` VARCHAR UNIQUE  NOT NULL ,`created` REAL,`year`INTEGER, `month` INTEGER, `date` INTEGER,`inserted` VARCHAR)";
      stmt.executeUpdate(files);
      String filetree = "CREATE TABLE `filetree` (`path` VARCHAR PRIMARY KEY  NOT NULL ,`subdirs` INTEGER DEFAULT 0 ,`files` INTEGER DEFAULT 0 )";
      stmt.executeUpdate(filetree);
      String prints = "CREATE TABLE `prints` (`id` INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , `file_id` INTEGER NOT NULL , `list_id` INTEGER)";
      stmt.executeUpdate(prints);
      String lists = "CREATE TABLE `lists` (`id` INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , `date` VARCHAR)";
      String options = "CREATE TABLE `options` (`root` VARCHAR)";
      stmt.executeUpdate(options);
      String ins = "INSERT INTO `options` (`root`) VALUES ('') ";
      stmt.executeUpdate(ins);
      return true;
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
      return false;
    }
  }

  private static boolean databaseExists(String db) {
    File dbFile = new File(Options.USER_DIR + "/" + db + ".db");
    if (dbFile.exists()) {
      return true;
    } else {
      return false;
    }
  }

  private Database() {
  }
}
