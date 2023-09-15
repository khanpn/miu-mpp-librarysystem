package com.miu.cs.librarysystem.ui.window;

import com.miu.cs.librarysystem.controller.ControllerInterface;
import com.miu.cs.librarysystem.controller.SystemController;
import com.miu.cs.librarysystem.dataaccess.User;
import com.miu.cs.librarysystem.store.AppStore;
import com.miu.cs.librarysystem.store.state.AppStatePath;
import com.miu.cs.librarysystem.store.state.LoginState;
import com.miu.cs.librarysystem.ui.panel.*;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

public class LibrarySystem extends JFrame implements LibWindow {
  /** */
  private static final long serialVersionUID = -8006557307706389790L;

  ControllerInterface ci = new SystemController();
  public static final LibrarySystem INSTANCE = new LibrarySystem();

  JPanel leftPanel;
  JPanel mainPanel;

  JSplitPane splitPane;

  String pathToImage;
  private boolean isInitialized = false;

  private User loggedInUser;

  public User getLoggedInUser() {
    return loggedInUser;
  }

  public void setLoggedInUser(User loggedInUser) {
    this.loggedInUser = loggedInUser;
  }

  private static LibWindow[] allWindows = {LibrarySystem.INSTANCE, MenuWindow.INSTANCE};

  public static void hideAllWindows() {
    for (LibWindow frame : allWindows) {
      frame.setVisible(false);
    }
  }

  private LibrarySystem() {
    leftPanel = new JPanel();
    leftPanel.setLayout(new GridLayout(10, 1));
    leftPanel.setBackground(Color.LIGHT_GRAY);
    leftPanel.setBorder(new EmptyBorder(15, 30, 10, 30));

    mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(1, 1));
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, mainPanel);
    splitPane.setDividerLocation(200);
    // splitPane.setEnabled(false);
    // splitPane.setDividerSize(0);
    getContentPane().add(splitPane);
  }

  public void init() {
    reloadContentPage();
    setPathToImage();
    insertSplashImage();

    setSize(1100, 700);
    splitPane.setDividerLocation(300);
    // pack();
    // setSize(660, 500);
    setInitialized(true);
  }

  private void reloadContentPage() {
    loggedInUser = AppStore.getState(AppStatePath.LOGIN, LoginState.class).getData().getAuthUser();
    MenuWindow.INSTANCE.setAuth(loggedInUser.getAuthorization());
    leftPanel = MenuWindow.INSTANCE;

    leftPanel.setLayout(new GridLayout(15, 1));
    leftPanel.setBackground(Color.LIGHT_GRAY);
    splitPane.setLeftComponent(leftPanel);
  }

  private void setPathToImage() {
    // String currDirectory = System.getProperty("user.dir");
    // // for Windows file system
    // pathToImage = currDirectory+"\\src\\main\\java\\librarysystem\\library.jpg";
    // // for unix file system
    //// pathToImage = currDirectory + "/src/librarysystem/library.jpg";
    pathToImage = getClass().getResource("/library.jpg").getFile();
  }

  private void insertSplashImage() {
    // repaint background after success login
    mainPanel.removeAll();
    mainPanel.validate();
    mainPanel.repaint();

    ImageIcon image = new ImageIcon(pathToImage);
    mainPanel.add(new JLabel(image));
    mainPanel.setBackground(Color.WHITE);
  }

  public void openListLibraryMemberWindow() {
    if (!(mainPanel instanceof AddMemberPanel)) {
      mainPanel = new AddMemberPanel();
      splitPane.setRightComponent(mainPanel);
      splitPane.setDividerLocation(300);
    }
  }

  public void openCheckoutBookWindow() {
    if (!(mainPanel instanceof CheckoutBookPanel)) {
      mainPanel = new CheckoutBookPanel();
      splitPane.setRightComponent(mainPanel);
      splitPane.setDividerLocation(300);
    }
  }

  public void openAddBookCopyWindow() {
    if (!(mainPanel instanceof BookshelfPanel)) {
      mainPanel = new BookshelfPanel();
      splitPane.setRightComponent(mainPanel);
      splitPane.setDividerLocation(300);
    }
  }

  public void openSearchMemberCheckoutRecordWindow() {
    if (!(mainPanel instanceof SearchMemberCheckoutRecordPanel)) {
      mainPanel = new SearchMemberCheckoutRecordPanel();
      splitPane.setRightComponent(mainPanel);
      splitPane.setDividerLocation(300);
    }
  }

  public void openSearchOverDueWindow() {
    if (!(mainPanel instanceof SearchOverDuePanel)) {
      mainPanel = new SearchOverDuePanel();
      splitPane.setRightComponent(mainPanel);
      splitPane.setDividerLocation(300);
    }
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
