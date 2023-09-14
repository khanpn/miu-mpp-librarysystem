package com.miu.cs.librarysystem.system;

import com.miu.cs.librarysystem.dataaccess.Auth;
import com.miu.cs.librarysystem.ui.window.LoginWindow;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class MenuWindow extends JPanel implements LibWindow {
  /** */
  private static final long serialVersionUID = -2239506196627731908L;

  public static final MenuWindow INSTANCE = new MenuWindow();

  private boolean isInitialized = false;

  private Auth role;

  public void init() {
    clearContent();

    switch (role) {
      case ADMIN -> {
        add(getListBooksButton());
        add(addMember());
      }
      case LIBRARIAN -> {
        add(getCheckoutButton());
        add(getSearchMemberCheckoutRecordButton());
        add(getSearchOverDueBookButton());
      }
      case BOTH -> {
        add(getListBooksButton());
        add(addMember());
        add(getCheckoutButton());
        add(getSearchOverDueBookButton());
        add(getSearchMemberCheckoutRecordButton());
      }
    }

    JSeparator separator = new JSeparator();
    separator.setOrientation(SwingConstants.HORIZONTAL);
    add(separator);

    add(getLogoutButton());
    setInitialized(true);
  }

  private static JButton getLogoutButton() {
    JButton logoutButton = new JButton("Log Out");
    logoutButton.addActionListener(
        e -> {
          LibrarySystem.INSTANCE.setLoggedInUser(null);
          LibrarySystem.INSTANCE.dispose();

          LoginWindow loginWindow = new LoginWindow();
          Util.centerFrameOnDesktop(loginWindow);
          loginWindow.setVisible(true);
        });
    return logoutButton;
  }

  private static JButton addMember() {
    JButton btn = new JButton("Add Member");
    btn.addActionListener(e -> LibrarySystem.INSTANCE.openListLibraryMemberWindow());
    return btn;
  }

  private static JButton getCheckoutButton() {
    JButton btn = new JButton("Checkout Book");
    btn.addActionListener(e -> LibrarySystem.INSTANCE.openCheckoutBookWindow());
    return btn;
  }

  private static JButton getListBooksButton() {
    JButton btn = new JButton("Add Book Copy");
    btn.addActionListener(e -> LibrarySystem.INSTANCE.openAddBookCopyWindow());
    return btn;
  }

  private static JButton getSearchMemberCheckoutRecordButton() {
    JButton btn = new JButton("Search Member Checkout Record");
    btn.addActionListener(e -> LibrarySystem.INSTANCE.openSearchMemberCheckoutRecordWindow());
    return btn;
  }

  private static JButton getSearchOverDueBookButton() {
    JButton btn = new JButton("Search OverDue Book");
    btn.addActionListener(e -> LibrarySystem.INSTANCE.openSearchOverDueWindow());
    return btn;
  }

  @Override
  public boolean isInitialized() {
    return isInitialized;
  }

  @Override
  public void setInitialized(boolean val) {
    this.isInitialized = val;
  }

  public void setAuth(Auth role) {
    this.role = role;
    init();
  }

  public void clearContent() {
    removeAll();
    revalidate();
    repaint();
  }

  private MenuWindow() {}
}
