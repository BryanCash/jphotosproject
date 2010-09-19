/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * photos.java
 *
 * Created on 2 Σεπ 2010, 6:07:06 μμ
 */
package jphotos;

import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import jphotos.objects.DateTreeCellRenderer;
import jphotos.objects.TreeDate;
import jphotos.database.Database;
import jphotos.database.FileRecord;
import jphotos.database.FileTreeRecord;
import jphotos.database.Print;
import jphotos.objects.AlbumLeaf;
import jphotos.objects.AlbumTreeCellRenderer;
import jphotos.panes.AlbumTreePanel;
import jphotos.panes.PhotoPanel;
import jphotos.panes.DateTreePanel;
import jphotos.panes.ListTreePanel;
import jphotos.panes.TreePanel;
import jphotos.tools.Importer;
import jphotos.tools.Options;
import jphotos.tools.Skin;
import jphotos.tools.Tools;

/**
 *
 * @author lordovol
 */
public class Photos extends javax.swing.JFrame {

  public static final long serialVersionUID = 34647557858568L;
  public static String version = "0.1";
  public static final Logger logger = Logger.getLogger("JPhotos");
  public static int selectedIndex = -1;
  public ArrayList<FileTreeRecord> fileTreeRecords;
  public int photos;
  public static ArrayList<Print> curList = new ArrayList<Print>();
  public static boolean isCurListSaved = false;
  public static int curListId = 0;
  private int mainSplitPosition;

  /** Creates new form photos */
  public Photos() {
    createLogger();
    Skin skin = new Skin(new Color(255, 255, 200));
    //Skin.applySkin();
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(Photos.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(Photos.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(Photos.class.getName()).log(Level.SEVERE, null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(Photos.class.getName()).log(Level.SEVERE, null, ex);
    }

    Database.createConnection(Options.DATABASE);
    Options.getOptions();
    while (Options.ROOT == null || Options.ROOT.equals("") || !Tools.isDirectory(Options.ROOT)) {
      String root = Tools.getUserInput("Root folder", "Please provide the root folder");
      if (Tools.isDirectory(root)) {
        Options.save(Options.FIELD_ROOT, root);

      }
    }
    initComponents();
    tree.populate(0);
    setLocationRelativeTo(null);
    setExtendedState(MAXIMIZED_BOTH);
    addTreeListener(tree);

    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    manager.addKeyEventDispatcher(new MyDispatcher());

  }

  private static void createLogger() {
    //Create the JVM logger
    try {
      // Create an appending file handler
      boolean append = true;
      int limit = 1000000; // 1 Mb
      int numLogFiles = 5;
      FileHandler fh = new FileHandler("jphotos_%g.log", limit, numLogFiles, true);
      fh.setFormatter(new SimpleFormatter());
      // Add to the desired logger
      logger.setLevel(Level.ALL);
      logger.addHandler(fh);
    } catch (IOException e) {
    }
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    toolbar = new javax.swing.JToolBar();
    bt_scan = new javax.swing.JButton();
    bt_update = new javax.swing.JButton();
    jSeparator1 = new javax.swing.JToolBar.Separator();
    Bt_allPhotos = new javax.swing.JButton();
    bt_newPhotos = new javax.swing.JButton();
    sp_count = new javax.swing.JSpinner();
    bt_album = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jSeparator4 = new javax.swing.JToolBar.Separator();
    bt_newList = new javax.swing.JButton();
    bt_saveList = new javax.swing.JButton();
    bt_flashDisk = new javax.swing.JButton();
    jSeparator2 = new javax.swing.JToolBar.Separator();
    jButton1 = new javax.swing.JButton();
    split_main = new javax.swing.JSplitPane();
    split_right = new javax.swing.JSplitPane();
    printsPanel = new jphotos.panes.PrintsPanel();
    split_top = new javax.swing.JSplitPane();
    gridPanel = new jphotos.panes.GridPanel();
    previewPanel = new jphotos.panes.PreviewPanel();
    tree = new jphotos.panes.DateTreePanel();
    menubar = new javax.swing.JMenuBar();
    m_photos = new javax.swing.JMenu();
    m_insert = new javax.swing.JMenuItem();
    m_update = new javax.swing.JMenuItem();
    jSeparator3 = new javax.swing.JPopupMenu.Separator();
    m_exit = new javax.swing.JMenuItem();
    m_list = new javax.swing.JMenu();
    m_newList = new javax.swing.JMenuItem();
    m_saveList = new javax.swing.JMenuItem();
    m_disk = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("JPhotos - version :" + version);
    setBackground(new java.awt.Color(255, 255, 255));
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });
    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(java.awt.event.ComponentEvent evt) {
        formComponentResized(evt);
      }
    });

    toolbar.setRollover(true);
    toolbar.setMaximumSize(new java.awt.Dimension(13, 25));
    toolbar.setMinimumSize(new java.awt.Dimension(13, 25));

    bt_scan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/scan.png"))); // NOI18N
    bt_scan.setToolTipText("Εισαγωγτή όλων τον φωτογραφιών στην βάση");
    bt_scan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    bt_scan.setFocusable(false);
    bt_scan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    bt_scan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    bt_scan.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bt_scanActionPerformed(evt);
      }
    });
    toolbar.add(bt_scan);

    bt_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/refresh.png"))); // NOI18N
    bt_update.setToolTipText("Εισαγωγή νέων φωτογραφιών στη βάση");
    bt_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    bt_update.setFocusable(false);
    bt_update.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    bt_update.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    bt_update.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bt_updateActionPerformed(evt);
      }
    });
    toolbar.add(bt_update);
    toolbar.add(jSeparator1);

    Bt_allPhotos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/photos.jpeg"))); // NOI18N
    Bt_allPhotos.setToolTipText("Όλες οπι φωτογραφίες");
    Bt_allPhotos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    Bt_allPhotos.setFocusable(false);
    Bt_allPhotos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    Bt_allPhotos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    Bt_allPhotos.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        Bt_allPhotosActionPerformed(evt);
      }
    });
    toolbar.add(Bt_allPhotos);

    bt_newPhotos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/new_photos.png"))); // NOI18N
    bt_newPhotos.setToolTipText("Νέες φωτογραφίες");
    bt_newPhotos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    bt_newPhotos.setFocusable(false);
    bt_newPhotos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    bt_newPhotos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    bt_newPhotos.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bt_newPhotosActionPerformed(evt);
      }
    });
    toolbar.add(bt_newPhotos);

    sp_count.setModel(new javax.swing.SpinnerNumberModel(100, 10, 1000, 10));
    sp_count.setToolTipText("Αριθμός νέων φωτογραφιών");
    sp_count.setAlignmentX(0.0F);
    sp_count.setAutoscrolls(true);
    sp_count.setMaximumSize(new java.awt.Dimension(50, 40));
    sp_count.setMinimumSize(new java.awt.Dimension(63, 40));
    toolbar.add(sp_count);

    bt_album.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/album.png"))); // NOI18N
    bt_album.setToolTipText("Λίστα Άλμπουμς");
    bt_album.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    bt_album.setFocusable(false);
    bt_album.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    bt_album.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    bt_album.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bt_albumActionPerformed(evt);
      }
    });
    toolbar.add(bt_album);

    jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/list.png"))); // NOI18N
    jButton2.setToolTipText("Αποθηκευμένες λίστες");
    jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton2.setFocusable(false);
    jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    toolbar.add(jButton2);
    toolbar.add(jSeparator4);

    bt_newList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/new.png"))); // NOI18N
    bt_newList.setToolTipText("Νέα Λίστα");
    bt_newList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    bt_newList.setFocusable(false);
    bt_newList.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    bt_newList.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    bt_newList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bt_newListActionPerformed(evt);
      }
    });
    toolbar.add(bt_newList);

    bt_saveList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/save.png"))); // NOI18N
    bt_saveList.setToolTipText("Σώσιμο λίστας");
    bt_saveList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    bt_saveList.setFocusable(false);
    bt_saveList.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    bt_saveList.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    bt_saveList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bt_saveListActionPerformed(evt);
      }
    });
    toolbar.add(bt_saveList);

    bt_flashDisk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/flash_disk.png"))); // NOI18N
    bt_flashDisk.setToolTipText("Αποθήκευση φωτογραφιών  στο δίσκο");
    bt_flashDisk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    bt_flashDisk.setFocusable(false);
    bt_flashDisk.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    bt_flashDisk.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    bt_flashDisk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bt_flashDiskActionPerformed(evt);
      }
    });
    toolbar.add(bt_flashDisk);
    toolbar.add(jSeparator2);

    jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/exit.png"))); // NOI18N
    jButton1.setToolTipText("Έξοδος");
    jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton1.setFocusable(false);
    jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    toolbar.add(jButton1);

    getContentPane().add(toolbar, java.awt.BorderLayout.NORTH);

    split_main.setDividerLocation(300);
    split_main.setOpaque(false);

    split_right.setBackground(Skin.getSkinColor());
    split_right.setDividerLocation(400);
    split_right.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
    split_right.setOpaque(false);

    printsPanel.setBackground(new java.awt.Color(255, 255, 255));
    printsPanel.setFocusable(false);
    printsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    split_right.setRightComponent(printsPanel);

    split_top.setBackground(Skin.getSkinColor());
    split_top.setDividerLocation(550);
    split_top.setOpaque(false);

    gridPanel.setBackground(new java.awt.Color(255, 255, 255));
    gridPanel.setFocusable(false);
    gridPanel.setLayout(new java.awt.BorderLayout());
    split_top.setLeftComponent(gridPanel);

    previewPanel.setBackground(new java.awt.Color(255, 255, 255));
    previewPanel.setFocusable(false);

    javax.swing.GroupLayout previewPanelLayout = new javax.swing.GroupLayout(previewPanel);
    previewPanel.setLayout(previewPanelLayout);
    previewPanelLayout.setHorizontalGroup(
      previewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 169, Short.MAX_VALUE)
    );
    previewPanelLayout.setVerticalGroup(
      previewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 397, Short.MAX_VALUE)
    );

    split_top.setRightComponent(previewPanel);

    split_right.setLeftComponent(split_top);

    split_main.setRightComponent(split_right);

    tree.setOpaque(false);
    split_main.setLeftComponent(tree);

    getContentPane().add(split_main, java.awt.BorderLayout.CENTER);

    m_photos.setText("Φωτογραφίες");

    m_insert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/scan.png"))); // NOI18N
    m_insert.setText("Εισαγωγή Όλων");
    m_insert.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        m_insertActionPerformed(evt);
      }
    });
    m_photos.add(m_insert);

    m_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/refresh.png"))); // NOI18N
    m_update.setText("Ανανέωση");
    m_update.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        m_updateActionPerformed(evt);
      }
    });
    m_photos.add(m_update);
    m_photos.add(jSeparator3);

    m_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/exit.png"))); // NOI18N
    m_exit.setText("Έξοδος");
    m_exit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        m_exitActionPerformed(evt);
      }
    });
    m_photos.add(m_exit);

    menubar.add(m_photos);

    m_list.setText("Λίστα Φωτογραφιών");

    m_newList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/new.png"))); // NOI18N
    m_newList.setText("Νέα Λίστα");
    m_newList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        m_newListActionPerformed(evt);
      }
    });
    m_list.add(m_newList);

    m_saveList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/save.png"))); // NOI18N
    m_saveList.setText("Αποθήκευση Λίστας");
    m_saveList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        m_saveListActionPerformed(evt);
      }
    });
    m_list.add(m_saveList);

    m_disk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jphotos/images/flash_disk.png"))); // NOI18N
    m_disk.setText("Αποθήκευση φωτογραφιών");
    m_disk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        m_diskActionPerformed(evt);
      }
    });
    m_list.add(m_disk);

    menubar.add(m_list);

    setJMenuBar(menubar);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void bt_scanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_scanActionPerformed
    fileTreeRecords = new ArrayList<FileTreeRecord>();
    Tools.scanDir(new File(Options.ROOT), fileTreeRecords);
    photos = Tools.countFiles(fileTreeRecords);
    Importer imp = new Importer(this, fileTreeRecords);
  }//GEN-LAST:event_bt_scanActionPerformed

  private void bt_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_updateActionPerformed
    ArrayList<FileTreeRecord> filetrees = Tools.getFileTreePaths();
    ArrayList<FileTreeRecord> dirsToImport = new ArrayList<FileTreeRecord>();
    ArrayList<File> filesToImport = new ArrayList<File>();
    for (Iterator<FileTreeRecord> it = filetrees.iterator(); it.hasNext();) {
      FileTreeRecord f = it.next();
      //NEW FILES
      if (f.fileCount != Tools.getPathFileCount(f.path)) {
        File[] newFiles = Tools.getNewFiles(f.path);
        filesToImport.addAll(Arrays.asList(newFiles));
      }
      //NEW DIRS
      if (f.subdirCount != Tools.getPathSubdirCount(f.path)) {
        File[] newDirs = Tools.getNewSubdirs(f.path);
        for (int i = 0; i < newDirs.length; i++) {
          File newDir = newDirs[i];
          if (newDir != null && newDir.isDirectory()) {
            Tools.scanDir(newDir, dirsToImport);
          }
        }
      }
    }
    for (Iterator<FileTreeRecord> it1 = dirsToImport.iterator(); it1.hasNext();) {
      FileTreeRecord fi = it1.next();
      File[] files = fi.files;
      filesToImport.addAll(Arrays.asList(files));
    }
    Importer imp = new Importer(this, dirsToImport, filesToImport);
    //   dirsToImport dirs
    // filesToImport files
  }//GEN-LAST:event_bt_updateActionPerformed

  private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    setDividers();
  }//GEN-LAST:event_formComponentResized

  private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    exit();
  }//GEN-LAST:event_formWindowClosing

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    exit();
  }//GEN-LAST:event_jButton1ActionPerformed

  private void bt_saveListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveListActionPerformed
    if (curList.size() > 0 && !isCurListSaved) {
      Tools.saveList();
    } else {
      Tools.message("Σώσιμο λίστας", "Η λίστα δεν χρειάζεται αποθήκευση");
    }
  }//GEN-LAST:event_bt_saveListActionPerformed

  private void bt_newListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_newListActionPerformed
    if (curList.size() > 0) {
      if (Tools.createNewList()) {
        printsPanel.clearPrints();
      }
    }
  }//GEN-LAST:event_bt_newListActionPerformed

  private void m_insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_insertActionPerformed
    bt_scanActionPerformed(evt);
  }//GEN-LAST:event_m_insertActionPerformed

  private void m_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_updateActionPerformed
    bt_updateActionPerformed(evt);
  }//GEN-LAST:event_m_updateActionPerformed

  private void m_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_exitActionPerformed
    exit();
  }//GEN-LAST:event_m_exitActionPerformed

  private void m_newListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_newListActionPerformed
    bt_newListActionPerformed(evt);
  }//GEN-LAST:event_m_newListActionPerformed

  private void m_saveListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_saveListActionPerformed
    bt_saveListActionPerformed(evt);
  }//GEN-LAST:event_m_saveListActionPerformed

  private void bt_flashDiskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_flashDiskActionPerformed
    Tools.saveListToDisk();
  }//GEN-LAST:event_bt_flashDiskActionPerformed

  private void m_diskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_diskActionPerformed
    Tools.saveListToDisk();
  }//GEN-LAST:event_m_diskActionPerformed

  private void bt_newPhotosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_newPhotosActionPerformed
    DateTreePanel date = new DateTreePanel();
    date.populate((Integer) sp_count.getValue());
    mainSplitPosition = split_main.getDividerLocation();
    split_main.setLeftComponent(date);
    split_main.setDividerLocation(mainSplitPosition);
    addTreeListener(date);
  }//GEN-LAST:event_bt_newPhotosActionPerformed

  private void addTreeListener(TreePanel tree) {
    tree.addPropertyChangeListener(new PropertyChangeListener() {

      public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(DateTreePanel.SELECTED_DATE)) {
          TreeDate newDate = (TreeDate) evt.getNewValue();
          ArrayList<FileRecord> datePhotos = Tools.getPhotosByDate(newDate);
          gridPanel.setPhotos(datePhotos);
        } else if (evt.getPropertyName().equals(AlbumTreePanel.SELECTED_ALBUM)) {
          AlbumLeaf alb = (AlbumLeaf) evt.getNewValue();
          ArrayList<FileRecord> albumPhotos = Tools.getPhotosByAlbum(alb.id);
          gridPanel.setPhotos(albumPhotos);
        }
      }
    });
  }

  private void Bt_allPhotosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bt_allPhotosActionPerformed
    DateTreePanel date = new DateTreePanel();
    date.populate(0);
    mainSplitPosition = split_main.getDividerLocation();
    split_main.setLeftComponent(date);
    split_main.setDividerLocation(mainSplitPosition);
    addTreeListener(date);
  }//GEN-LAST:event_Bt_allPhotosActionPerformed

  private void bt_albumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_albumActionPerformed
    AlbumTreePanel album = new AlbumTreePanel();
    album.populate(0);
    mainSplitPosition = split_main.getDividerLocation();
    split_main.setLeftComponent(album);
    split_main.setDividerLocation(mainSplitPosition);
    addTreeListener(album);
  }//GEN-LAST:event_bt_albumActionPerformed

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    ListTreePanel list = new ListTreePanel();
    list.populate(0);
    mainSplitPosition = split_main.getDividerLocation();
    split_main.setLeftComponent(list);
    split_main.setDividerLocation(mainSplitPosition);
    addTreeListener(list);
  }//GEN-LAST:event_jButton2ActionPerformed

  private class MyDispatcher implements KeyEventDispatcher {

    @Override
    public boolean dispatchKeyEvent(KeyEvent evt) {
      if (evt.getID() == KeyEvent.KEY_PRESSED) {
      } else if (evt.getID() == KeyEvent.KEY_RELEASED) {
        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
          if (selectedIndex == -1) {
            return false;
          }
          if (selectedIndex < gridPanel.photoPanel.getComponentCount() - 1) {
            PhotoPanel p = (PhotoPanel) gridPanel.photoPanel.getComponent(selectedIndex + 1);
            p.selectPhoto();
          }
        } else if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
          if (selectedIndex > 0) {
            PhotoPanel p = (PhotoPanel) gridPanel.photoPanel.getComponent(selectedIndex - 1);
            p.selectPhoto();
          }
        }
      } else if (evt.getID() == KeyEvent.KEY_TYPED) {
      }
      return false;
    }
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        new Photos().setVisible(true);
      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  public javax.swing.JButton Bt_allPhotos;
  public javax.swing.JButton bt_album;
  public javax.swing.JButton bt_flashDisk;
  public javax.swing.JButton bt_newList;
  public javax.swing.JButton bt_newPhotos;
  public javax.swing.JButton bt_saveList;
  public javax.swing.JButton bt_scan;
  public javax.swing.JButton bt_update;
  public jphotos.panes.GridPanel gridPanel;
  public javax.swing.JButton jButton1;
  public javax.swing.JButton jButton2;
  public javax.swing.JToolBar.Separator jSeparator1;
  public javax.swing.JToolBar.Separator jSeparator2;
  public javax.swing.JPopupMenu.Separator jSeparator3;
  public javax.swing.JToolBar.Separator jSeparator4;
  public javax.swing.JMenuItem m_disk;
  public javax.swing.JMenuItem m_exit;
  public javax.swing.JMenuItem m_insert;
  public javax.swing.JMenu m_list;
  public javax.swing.JMenuItem m_newList;
  public javax.swing.JMenu m_photos;
  public javax.swing.JMenuItem m_saveList;
  public javax.swing.JMenuItem m_update;
  public javax.swing.JMenuBar menubar;
  public jphotos.panes.PreviewPanel previewPanel;
  public jphotos.panes.PrintsPanel printsPanel;
  public javax.swing.JSpinner sp_count;
  public javax.swing.JSplitPane split_main;
  public javax.swing.JSplitPane split_right;
  public javax.swing.JSplitPane split_top;
  public javax.swing.JToolBar toolbar;
  public jphotos.panes.DateTreePanel tree;
  // End of variables declaration//GEN-END:variables

  private void setDividers() {
    split_main.setDividerLocation(getWidth() * 15 / 100);
    split_right.setDividerLocation(getHeight() * 75 / 100);
    split_top.setDividerLocation((getWidth() - tree.getWidth()) * 40 / 100);
  }

  private void exit() {
    if (curList.size() > 0 && !isCurListSaved) {
      if (Tools.ask("Σώσιμο Λίστας", "Η λίστα σας δεν έχει σωθεί, θέλετε να την σώσετε;") == JOptionPane.YES_OPTION) {
        Tools.saveList();
      }
    }
    System.exit(0);
  }

  public static boolean clearList() {
    Photos.curList.clear();
    Photos.isCurListSaved = false;
    Photos.curListId = 0;
    return true;
  }
}
