package com.miu.cs.librarysystem.ui.window;

import com.miu.cs.librarysystem.business.ControllerInterface;
import com.miu.cs.librarysystem.business.SystemController;
import com.miu.cs.librarysystem.system.LibWindow;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class CheckoutBookWindow extends JPanel implements LibWindow {

  private static final long serialVersionUID = -6316708968684859855L;
  private boolean isInitialized = false;

  DefaultTableModel model = new DefaultTableModel();
  ControllerInterface ci = new SystemController();

  public CheckoutBookWindow() {
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
