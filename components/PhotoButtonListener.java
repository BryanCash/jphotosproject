/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jphotos.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import jphotos.panes.PreviewPanel;

/**
 *
 * @author lordovol
 */
public class PhotoButtonListener extends MouseAdapter {

  private PreviewPanel preview;

  public PhotoButtonListener(PreviewPanel preview) {
    this.preview = preview;

  }

  @Override
  public void mouseReleased(MouseEvent e) {
    PhotoButton b = (PhotoButton) e.getSource();
    switch (b.type) {
      case PhotoButton.ROTATE_LEFT:
        preview.rotate(PreviewPanel.ROTATE_LEFT_DEGREES);
        break;
      case PhotoButton.ROTATE_RIGHT:
        preview.rotate(PreviewPanel.ROTATE_RIGHT_DEGREES);
        break;
      case  PhotoButton.PRINT:
        preview.addToList();
          break;
    }
  }
}
