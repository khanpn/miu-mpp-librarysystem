package com.miu.cs.librarysystem.system;

import com.miu.cs.librarysystem.business.ControllerInterface;
import com.miu.cs.librarysystem.business.SystemController;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class SearchMemberCheckoutRecordWindow extends JPanel implements LibWindow {
  private static final long serialVersionUID = -4264885214167193462L;
  private boolean isInitialized = false;

  DefaultTableModel model = new DefaultTableModel();
  ControllerInterface ci = new SystemController();

  public SearchMemberCheckoutRecordWindow() {
    init();
  }

  @Override
  public void init() {}

  @Override
  public boolean isInitialized() {
    return isInitialized;
  }

  @Override
  public void setInitialized(boolean val) {
    isInitialized = val;
  }
}
