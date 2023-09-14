package com.miu.cs.librarysystem.system;

import com.miu.cs.librarysystem.business.ControllerInterface;
import com.miu.cs.librarysystem.business.SystemController;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class ListLibraryBookWindow extends JPanel implements LibWindow {

  private static final long serialVersionUID = -8717602939009496185L;
  private boolean isInitialized = false;

  DefaultTableModel model = new DefaultTableModel();
  ControllerInterface ci = new SystemController();

  /** Create the application. */
  public ListLibraryBookWindow() {
    init();
  }

  /** Initialize the contents of the frame. */
  private void initialize() {}

  @Override
  public void init() {
    initialize();
  }

  @Override
  public boolean isInitialized() {
    return isInitialized;
  }

  @Override
  public void setInitialized(boolean val) {
    isInitialized = val;
  }
}
