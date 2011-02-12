/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jphotos.database;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import jphotos.Photos;
import jphotos.panes.Cleaner.CleanFile;
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

    public ArrayList<String> getTags() {
        ArrayList<String> tags = new ArrayList<String>();
        try {

            String sql = "SELECT tag from filetags JOIN tags ON tag_id = tags.id WHERE file_id = " + this.id;
            ResultSet rs = Database.stmt.executeQuery(sql);
            while (rs.next()) {
                tags.add(rs.getString("tag"));
            }
            return tags;
        } catch (SQLException ex) {
            Photos.logger.log(Level.SEVERE, null, ex);
            return tags;
        }
    }

    public static void cleanFile(CleanFile file) throws SQLException {
        int id = file.id;
        String path = file.path;
        String folder = new File(file.path).getParent();
        Database.stmt.execute("DELETE FROM prints WHERE file_id = " + id);
        Database.stmt.execute("DELETE FROM filetags WHERE file_id = " + id);
        Database.stmt.execute("DELETE FROM files WHERE id = " + id);
        Database.stmt.execute("UPDATE filetree set files = files -1 WHERE path = '" + folder +"'");
        Photos.logger.log(Level.INFO, "Removed photo {0} from database", path);
    }
}
