package edu.miu.cs.librarysystem.ui.panel;

import edu.miu.cs.librarysystem.dataaccess.Auth;
import edu.miu.cs.librarysystem.store.core.Store;
import edu.miu.cs.librarysystem.store.state.AppStatePath;
import edu.miu.cs.librarysystem.store.state.LoginState;
import edu.miu.cs.librarysystem.ui.MenuItem;
import javax.swing.*;

public class MenuPanel extends JPanel implements LibPanel {
  public void init() {
    Auth role =
        Store.getState(AppStatePath.LOGIN, LoginState.class)
            .getData()
            .getAuthUser()
            .getAuthorization();
    clearContent();

    switch (role) {
      case ADMIN -> {
        add(MenuItem.BOOKSHELF.create());
        add(MenuItem.LIBRARY_MEMBER.create());
      }
      case LIBRARIAN -> {
        add(MenuItem.CHECKOUT_BOOK.create());
        add(MenuItem.SEARCH_MEMBER_CHECKOUT_RECORD.create());
        add(MenuItem.SEARCH_OVERDUE_BOOK.create());
      }
      case BOTH -> {
        add(MenuItem.BOOKSHELF.create());
        add(MenuItem.LIBRARY_MEMBER.create());
        add(MenuItem.CHECKOUT_BOOK.create());
        add(MenuItem.SEARCH_MEMBER_CHECKOUT_RECORD.create());
        add(MenuItem.SEARCH_OVERDUE_BOOK.create());
      }
    }

    JSeparator separator = new JSeparator();
    separator.setOrientation(SwingConstants.HORIZONTAL);
    add(separator);

    add(MenuItem.LOGOUT.create());
  }

  public void clearContent() {
    removeAll();
    revalidate();
    repaint();
  }
}
