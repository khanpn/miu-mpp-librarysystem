package edu.miu.cs.librarysystem.ui.panel;

import edu.miu.cs.librarysystem.dataaccess.Auth;
import edu.miu.cs.librarysystem.ui.window.LibrarySystem;
import edu.miu.cs.librarysystem.ui.window.LoginWindow;
import edu.miu.cs.librarysystem.util.Util;
import javax.swing.*;

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
        add(getCopyAndAddBookButton());
        add(libraryMember());
      }
      case LIBRARIAN -> {
        add(getCheckoutButton());
        add(getSearchMemberCheckoutRecordButton());
        add(getSearchOverDueBookButton());
      }
      case BOTH -> {
        add(getCopyAndAddBookButton());
        add(libraryMember());
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
    JButton logoutButton = Util.buttonImage("/images/logout-color.png", "Log Out", 30, 30);
    Util.addButtonHover(logoutButton);
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

  private static JButton libraryMember() {
    JButton btn = Util.buttonImage("/images/add-member-color.png", "Library Member", 30, 30);
    Util.addButtonHover(btn);
    btn.addActionListener(e -> LibrarySystem.INSTANCE.openListLibraryMemberWindow());
    return btn;
  }

  private static JButton getCheckoutButton() {
    JButton btn = Util.buttonImage("/images/checkout-book-color.png", "Checkout Book", 30, 30);
    Util.addButtonHover(btn);
    btn.addActionListener(e -> LibrarySystem.INSTANCE.openCheckoutBookWindow());
    return btn;
  }

  private static JButton getCopyAndAddBookButton() {
    JButton btn = Util.buttonImage("/images/add-copy-book-color.png", "Bookshelf", 30, 30);
    Util.addButtonHover(btn);
    btn.addActionListener(e -> LibrarySystem.INSTANCE.openAddBookCopyWindow());
    return btn;
  }

  private static JButton getSearchMemberCheckoutRecordButton() {
    JButton btn =
        Util.buttonImage(
            "/images/search-checkout-color.png", "Search Member Checkout Record", 30, 30);
    Util.addButtonHover(btn);
    btn.addActionListener(e -> LibrarySystem.INSTANCE.openSearchMemberCheckoutRecordWindow());
    return btn;
  }

  private static JButton getSearchOverDueBookButton() {
    JButton btn =
        Util.buttonImage("/images/search-overdue-color.png", "Search Overdue Book", 30, 30);
    Util.addButtonHover(btn);
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
