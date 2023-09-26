package edu.miu.cs.librarysystem.ui;

import edu.miu.cs.librarysystem.store.action.mainwindow.MainWindowMenuSelectItemAction;
import edu.miu.cs.librarysystem.store.core.Dispatcher;
import edu.miu.cs.librarysystem.util.UiUtils;
import javax.swing.*;

public enum MenuItem {
  BOOKSHELF("Bookshelf", "/images/add-copy-book-color.png", 30, 30) {
    @Override
    public JComponent create() {
      JButton button =
          UiUtils.buttonImage(getIconPath(), getName(), getIconWidth(), getIconHeight());
      UiUtils.addButtonHover(button);
      button.addActionListener(e -> Dispatcher.dispatch(new MainWindowMenuSelectItemAction(this)));
      return button;
    }
  },
  LIBRARY_MEMBER("Library Member", "/images/add-member-color.png", 30, 30) {
    @Override
    public JComponent create() {
      JButton button =
          UiUtils.buttonImage(getIconPath(), getName(), getIconWidth(), getIconHeight());
      UiUtils.addButtonHover(button);
      button.addActionListener(e -> Dispatcher.dispatch(new MainWindowMenuSelectItemAction(this)));
      return button;
    }
  },
  CHECKOUT_BOOK("Checkout Book", "/images/checkout-book-color.png", 30, 30) {
    @Override
    public JComponent create() {
      JButton button =
          UiUtils.buttonImage(getIconPath(), getName(), getIconWidth(), getIconHeight());
      UiUtils.addButtonHover(button);
      button.addActionListener(e -> Dispatcher.dispatch(new MainWindowMenuSelectItemAction(this)));
      return button;
    }
  },
  SEARCH_OVERDUE_BOOK("Search Overdue Book", "/images/search-overdue-color.png", 30, 30) {
    @Override
    public JComponent create() {
      JButton button =
          UiUtils.buttonImage(getIconPath(), getName(), getIconWidth(), getIconHeight());
      UiUtils.addButtonHover(button);
      button.addActionListener(e -> Dispatcher.dispatch(new MainWindowMenuSelectItemAction(this)));
      return button;
    }
  },
  SEARCH_MEMBER_CHECKOUT_RECORD(
      "Search Member Checkout Record", "/images/search-checkout-color.png", 30, 30) {
    @Override
    public JComponent create() {
      JButton button =
          UiUtils.buttonImage(getIconPath(), getName(), getIconWidth(), getIconHeight());
      UiUtils.addButtonHover(button);
      button.addActionListener(e -> Dispatcher.dispatch(new MainWindowMenuSelectItemAction(this)));
      return button;
    }
  },
  LOGOUT("Logout", "/images/logout-color.png", 30, 30) {
    @Override
    public JComponent create() {
      JButton button =
          UiUtils.buttonImage(getIconPath(), getName(), getIconWidth(), getIconHeight());
      UiUtils.addButtonHover(button);
      button.addActionListener(e -> Dispatcher.dispatch(new MainWindowMenuSelectItemAction(this)));
      return button;
    }
  };
  private final String name;
  private final String iconPath;
  private final int iconWidth;
  private final int iconHeight;

  MenuItem(String name, String iconPath, int iconWidth, int iconHeight) {
    this.name = name;
    this.iconPath = iconPath;
    this.iconWidth = iconWidth;
    this.iconHeight = iconHeight;
  }

  public abstract JComponent create();

  public String getName() {
    return name;
  }

  public String getIconPath() {
    return iconPath;
  }

  public int getIconWidth() {
    return iconWidth;
  }

  public int getIconHeight() {
    return iconHeight;
  }
}
