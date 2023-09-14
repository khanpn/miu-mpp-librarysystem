package com.miu.cs.librarysystem.system;

import com.miu.cs.librarysystem.dataaccess.Auth;
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
        add(getUserListButton());
      }
      case LIBRARIAN -> {
        add(getCheckoutButton());
        add(getSearchMemberButton());
        add(getSearchBookButton());
      }
      case BOTH -> {
        add(getListBooksButton());
        add(getUserListButton());
        add(getCheckoutButton());
        add(getSearchBookButton());
        add(getSearchMemberButton());
      }
    }

    JSeparator separator = new JSeparator();
    separator.setOrientation(SwingConstants.HORIZONTAL);
    add(separator);

    add(getLogoutButton());
    setInitialized(true);
  }

  private static JButton getLogoutButton() {
    JButton logoutButton = new JButton("Sign Out");
    logoutButton.addActionListener(
        e -> {
          LibrarySystem.INSTANCE.setLoggedInUser(null);
          LibrarySystem.INSTANCE.init();
        });
    return logoutButton;
  }

  private static JButton getUserListButton() {
    JButton usersButton = new JButton("Members");
    return usersButton;
  }

  private static JButton getCheckoutButton() {
    JButton checkoutButton = new JButton("Checkout Book");
    return checkoutButton;
  }

  private static JButton getListBooksButton() {
    JButton booksButton = new JButton("Add Book");
    return booksButton;
  }

  private static JButton getSearchMemberButton() {
    JButton booksButton = new JButton("Search Member");
    return booksButton;
  }

  private static JButton getSearchBookButton() {
    JButton booksButton = new JButton("Search Book");
    return booksButton;
  }

  @Override
  public boolean isInitialized() {
    return false;
  }

  @Override
  public void setInitialized(boolean val) {}

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
