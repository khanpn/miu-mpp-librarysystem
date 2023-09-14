package com.miu.cs.librarysystem.ui.panel;

import com.miu.cs.librarysystem.business.ControllerInterface;
import com.miu.cs.librarysystem.business.SystemController;
import com.miu.cs.librarysystem.system.LibWindow;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class SearchMemberCheckoutRecordPanel extends JPanel implements LibWindow {
  private static final long serialVersionUID = -4264885214167193462L;
  private boolean isInitialized = false;

  DefaultTableModel model = new DefaultTableModel();
  ControllerInterface ci = new SystemController();

  public SearchMemberCheckoutRecordPanel() {
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
