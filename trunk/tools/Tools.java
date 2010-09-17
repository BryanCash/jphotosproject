/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jphotos.tools;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import jphotos.objects.DateObj;
import jphotos.objects.TreeDate;
import jphotos.Photos;
import jphotos.components.ImageFileFilter;
import jphotos.database.Database;
import jphotos.database.FileRecord;
import jphotos.database.FileTreeRecord;
import jphotos.database.List;
import jphotos.database.Print;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.SanselanConstants;
import org.apache.sanselan.common.IBufferedImageFactory;
import org.apache.sanselan.common.byteSources.ByteSourceFile;
import org.apache.sanselan.formats.jpeg.JpegImageParser;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;

/**
 *
 * @author lordovol
 */
public class Tools {

  public static String getUserInput(String title, String message) {
    return JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
  }

  public static boolean isDirectory(String path) {
    return (new File(path).isDirectory());
  }

  public static ArrayList<FileTreeRecord> scanDir(File root, ArrayList<FileTreeRecord> fileTreeRecords) {
    File[] subDirs = getSubDirs(root);
    File[] files = getFiles(root);
    FileTreeRecord ft = new FileTreeRecord(root, subDirs, files);
    fileTreeRecords.add(ft);
    if (subDirs.length == 0) {
      return fileTreeRecords;
    } else {
      for (int i = 0; i < subDirs.length; i++) {
        File sub = subDirs[i];
        scanDir(sub, fileTreeRecords);
      }
      return fileTreeRecords;
    }
  }

  private static File[] getSubDirs(File fileRoot) {
    return fileRoot.listFiles(new FileFilter() {

      public boolean accept(File pathname) {
        return pathname.isDirectory();
      }
    });

  }

  private static File[] getFiles(File root) {
    return root.listFiles(new ImageFileFilter());
  }

  public static int countFiles(ArrayList<FileTreeRecord> fileTreeRecords) {
    int files = 0;
    for (Iterator<FileTreeRecord> it = fileTreeRecords.iterator(); it.hasNext();) {
      FileTreeRecord fileTreeRecord = it.next();
      files += fileTreeRecord.files.length;
    }
    return files;
  }

  public static ArrayList<DateObj> getAllPhotos() {
    ArrayList<DateObj> dateObj = new ArrayList<DateObj>();
    try {
      String sql = "select count(*) AS total , date(created/1000,'unixepoch') AS date " + "from files group by date(created/1000,'unixepoch') " + "order by date(created/1000,'unixepoch')  desc";
      ResultSet rs = Database.stmt.executeQuery(sql);
      while (rs.next()) {
        dateObj.add(new DateObj(rs.getString("date"), rs.getInt("total")));
      }
      return dateObj;
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
      return dateObj;
    }
  }

  public static String monthName(int month) {
    switch (month) {
      case 0:
        return "Αγνωστος μήνας";
      case 1:
        return "Ιανουάριος";
      case 2:
        return "Φεβρουάριος";
      case 3:
        return "Μάρτιος";
      case 4:
        return "Απρίλιος";
      case 5:
        return "Μάιος";
      case 6:
        return "Ιούνιος";
      case 7:
        return "Ιούλιος";
      case 8:
        return "Αύγουστος";
      case 9:
        return "Σεπτέμβριος";
      case 10:
        return "Οκτώβριος";
      case 11:
        return "Νοέμβριος";
      case 12:
        return "Δεκέμβριος";
      default:
        throw new RuntimeException("Λάθος μήνας " + month);
    }
  }

  public static Calendar getCreateDate(File photo) {

    try {
      JpegImageParser parser = new JpegImageParser();
      ByteSourceFile bytesource = new ByteSourceFile(photo);
      HashMap params = new HashMap();
      TiffImageMetadata d = parser.getExifMetadata(bytesource, params);
      if (d != null) {
        TiffField ti = d.findField(TiffField.EXIF_TAG_CREATE_DATE);
        String val = ti.getStringValue();
        String[] fields = val.split("[: ]", -1);
        Calendar date = Calendar.getInstance();
        date.set(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]) - 1, Integer.parseInt(fields[2]));
        return date;
      } else {
        Photos.logger.log(Level.WARNING, "Could not read exif info from file {0}", photo);
        return getLastModified(photo);
      }
    } catch (ImageReadException ex) {
      Photos.logger.log(Level.WARNING, "Could not read exif info from file {0}", photo);
      return getLastModified(photo);
    } catch (IOException ex) {
      Photos.logger.log(Level.WARNING, "Could not read file {0}", photo);
      return getLastModified(photo);
    } catch (Exception ex) {
      Photos.logger.log(Level.WARNING, "Could not read exif info from file {0}", photo);
      return getLastModified(photo);
    }
  }

  private static Calendar getLastModified(File photo) {
    long mod = photo.lastModified();
    Calendar date = Calendar.getInstance();
    date.setTimeInMillis(mod);
    return date;
  }

  public static ArrayList<FileRecord> getPhotosByDate(TreeDate date) {
    ArrayList<FileRecord> photos = new ArrayList<FileRecord>();
    try {
      String sql = "Select * from files WHERE year = " + date.year
              + " AND month = " + date.month + " AND date = " + date.date
              + " ORDER BY created DESC";
      ResultSet rs = Database.stmt.executeQuery(sql);
      while (rs.next()) {
        Calendar cal = Calendar.getInstance();
        cal.set(rs.getInt("year"), rs.getInt("month") - 1, rs.getInt("date"));
        FileRecord f = new FileRecord(rs.getString("path"), cal);
        f.id = rs.getInt("id");
        photos.add(f);
      }
      return photos;
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
      return photos;
    }
  }

  @SuppressWarnings("unchecked")
  public static BufferedImage imageRead(File file) throws ImageReadException, IOException {
    Map params = new HashMap();

    // set optional parameters if you like
    params.put(SanselanConstants.BUFFERED_IMAGE_FACTORY,
            new ManagedImageBufferedImageFactory());

    //		params.put(SanselanConstants.PARAM_KEY_VERBOSE, Boolean.TRUE);

    // read image
    BufferedImage image = Sanselan.getBufferedImage(file, params);

    return image;
  }

  public static ArrayList<FileTreeRecord> getFileTreePaths() {
    ArrayList<FileTreeRecord> filetree = new ArrayList<FileTreeRecord>();
    try {
      ResultSet rs = Database.stmt.executeQuery("SELECT * FROM filetree");
      while (rs.next()) {
        FileTreeRecord f = new FileTreeRecord();
        f.path = rs.getString("path");
        f.subdirCount = rs.getInt("subdirs");
        f.fileCount = rs.getInt("files");
        filetree.add(f);
      }
      return filetree;
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
      return null;
    }
  }

  public static int getPathFileCount(String path) {
    File dir = new File(path);
    return dir.listFiles(new ImageFileFilter()).length;
  }

  public static int getPathSubdirCount(String path) {
    int dirs = 0;
    File dir = new File(path);
    File[] files = dir.listFiles();
    for (int i = 0; i < files.length; i++) {
      File file = files[i];
      if (file.isDirectory()) {
        dirs++;
      }
    }
    return dirs;
  }

  public static File[] getNewSubdirs(String path) {
    File[] dirs = new File(path).listFiles();
    for (int i = 0; i < dirs.length; i++) {
      File d = dirs[i];
      if (d.isDirectory() && Tools.isPathInDatabase(d)) {
        dirs[i] = null;
      }
    }
    return dirs;
  }

  private static boolean isPathInDatabase(File path) {
    try {
      ResultSet rs = Database.stmt.executeQuery("SELECT * FROM filetree WHERE path = '" + path.getAbsolutePath() + "'");
      if (rs.next()) {
        return true;
      }
      return false;
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
      return false;
    }
  }

  public static File[] getNewFiles(String path) {
    File[] files = new File(path).listFiles(new ImageFileFilter());
    for (int i = 0; i < files.length; i++) {
      File file = files[i];
      if (isFileInDatabase(file)) {
        files[i] = null;
      }
    }
    return files;
  }

  private static boolean isFileInDatabase(File file) {
    try {
      ResultSet rs = Database.stmt.executeQuery("SELECT * FROM files WHERE path = '" + file.getAbsolutePath() + "'");
      if (rs.next()) {
        return true;
      }
      return false;
    } catch (SQLException ex) {
      Photos.logger.log(Level.SEVERE, null, ex);
      return false;
    }
  }

  public static ImageIcon ReadImage(String path, int ICON_WIDTH) {
    FileInputStream in = null;
    try {
      in = new FileInputStream(path);
      JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
      BufferedImage image = decoder.decodeAsBufferedImage();
      in.close();
      double w = 0.1;
      double h = 0.1;
      BufferedImageOp op = new AffineTransformOp(AffineTransform.getScaleInstance(w, h), new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC));
      image = op.filter(image, null);
      return new ImageIcon(image);
    } catch (IOException ex) {
      Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    } catch (ImageFormatException ex) {
      Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    } finally {
      try {
        in.close();
      } catch (IOException ex) {
        Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  public static String getNow() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    java.util.Date date = new java.util.Date();
    return dateFormat.format(date);

  }

  public static int ask(String title, String message) {
    return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
  }

  public static void error(String title, String message) {
    JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
  }

  public static void message(String title, String message) {
    JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  public static void saveList() {
    if (Photos.curListId == 0) {
      List l = new List();
      l.date = Tools.getNow();
      l.save();
      if (l.id > 0) {
        Photos.curListId = l.id;
      } else {
        Tools.error("Σώσιμο λίστας", "Η λίστα δεν σώθηκε");
      }
    } else {
      Print.deleteByListId(Photos.curListId);

    }
    for (Iterator<Print> it = Photos.curList.iterator(); it.hasNext();) {
      Print print = it.next();
      print.id = 0;
      print.list_id = Photos.curListId;
      print.save();
    }
    Tools.message("Σώσιμο λίστας", Photos.curList.size() + " φωτογραφίες σώθηκαν στη λίστα");
    Photos.isCurListSaved = true;

  }

  public static boolean createNewList() {
    if (Photos.curList.size() > 0 && !Photos.isCurListSaved) {
      if (Tools.ask("Σώσιμο Λίστας", "Θέλετε να σώσετε την τρέχουσα λίστα πρώτα") == JOptionPane.YES_OPTION) {
        saveList();
      }
    }
    return Photos.clearList();
  }

  public static void saveListToDisk() {
    if (Photos.curList.isEmpty()) {
      error("Αποθήκευση φωτογραφιών", "Δεν υπάρχει καμιά φωτογραφία στη λίστα");
      return;
    }
    if (!Photos.isCurListSaved) {
      Tools.saveList();
    }
    JFileChooser fc = new JFileChooser();
    fc.setDialogTitle("Αποθήκευση φωτογραφιών");
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fc.setApproveButtonText("Αποθήκευση");
    fc.showOpenDialog(null);
    File f = fc.getSelectedFile();
    ArrayList<File> files = List.getListFiles();
    Copier copier = new Copier(files, f);
  }

  public static boolean copyfile(String srFile, String dtFile) throws FileNotFoundException, IOException {

    File f1 = new File(srFile);
    File f2 = new File(dtFile);
    InputStream in = new FileInputStream(f1);
    OutputStream out = new FileOutputStream(f2);
    try {
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
    } finally {
      in.close();
      out.close();
    }
    return true;
  }

  public static String createPhotoName(File path, File file) {
    File dest = new File(path.getAbsolutePath() + "/" + file.getName());
    if (!dest.exists()) {
      return dest.getAbsolutePath();
    } else {
      return dest.getParent() + "/copy_" + createRandomString(4) + "_" + file.getName();
    }
  }

  /**
   * Creates a random string
   * @param length The length of the string
   * @return The random string
   */
  public static String createRandomString(int length) {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    while (sb.length() < length) {
      sb.append(Integer.toHexString(random.nextInt()));
    }
    return sb.toString();
  }

  public static ArrayList<File> removeNullEntries(ArrayList<File> list) {
   ArrayList<File> filteredList = new ArrayList<File>();
    for (Iterator<File> it = list.iterator(); it.hasNext();) {
      File file = it.next();
      if(file!=null){
        filteredList.add(file);
      }
    }
   return filteredList;
  }

  public static class ManagedImageBufferedImageFactory implements IBufferedImageFactory {

    public BufferedImage getColorBufferedImage(int width, int height,
            boolean hasAlpha) {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice gd = ge.getDefaultScreenDevice();
      GraphicsConfiguration gc = gd.getDefaultConfiguration();
      return gc.createCompatibleImage(width, height,
              Transparency.TRANSLUCENT);
    }

    public BufferedImage getGrayscaleBufferedImage(int width, int height,
            boolean hasAlpha) {
      return getColorBufferedImage(width, height, hasAlpha);
    }
  }

  public static ImageIcon getImage(FileRecord fileRecord, int width) {
    int w = new ImageIcon(fileRecord.path).getImage().getWidth(null);
    int h = new ImageIcon(fileRecord.path).getImage().getHeight(null);
    Image icon = new ImageIcon(fileRecord.path).getImage().getScaledInstance(width, width * h / w, Image.SCALE_SMOOTH);
    return new ImageIcon(icon);
  }

  private Tools() {
  }
}
