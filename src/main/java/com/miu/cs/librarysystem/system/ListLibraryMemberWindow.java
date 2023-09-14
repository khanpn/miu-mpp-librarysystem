package com.miu.cs.librarysystem.system;

import com.miu.cs.librarysystem.business.ControllerInterface;
import com.miu.cs.librarysystem.business.SystemController;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class ListLibraryMemberWindow extends JPanel implements LibWindow {
  private static final long serialVersionUID = 7863919163615773327L;
  private boolean isInitialized = false;

  DefaultTableModel model = new DefaultTableModel();
  ControllerInterface ci = new SystemController();

  /** Create the application. */
  public ListLibraryMemberWindow() {
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
