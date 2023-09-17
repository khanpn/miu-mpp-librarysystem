package edu.miu.cs.librarysystem.ui.window;

import edu.miu.cs.librarysystem.store.core.StateChangeEvent;
import edu.miu.cs.librarysystem.store.core.StateChangeListener;
import edu.miu.cs.librarysystem.store.core.state.StatePath;
import edu.miu.cs.librarysystem.store.state.AppStatePath;
import edu.miu.cs.librarysystem.store.state.MainWindowState;
import edu.miu.cs.librarysystem.ui.LibWindow;
import edu.miu.cs.librarysystem.ui.LibrarySystem;
import edu.miu.cs.librarysystem.ui.MenuItem;
import edu.miu.cs.librarysystem.ui.panel.*;
import edu.miu.cs.librarysystem.util.Util;
import edu.miu.cs.librarysystem.viewmodel.MainWindowViewModel;
import java.awt.*;
import java.util.Optional;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame implements LibWindow, StateChangeListener<MainWindowState> {

  JPanel leftPanel;
  JPanel mainPanel;

  JSplitPane splitPane;

  public void init() {
    setTitle("Library System Application");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    leftPanel = new JPanel();
    leftPanel.setLayout(new GridLayout(10, 1));
    leftPanel.setBackground(Color.LIGHT_GRAY);
    leftPanel.setBorder(new EmptyBorder(15, 30, 10, 30));

    mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(1, 1));
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, mainPanel);
    splitPane.setDividerLocation(200);
    getContentPane().add(splitPane);
    repaintMenuPanel();
    insertSplashImage();

    setSize(1100, 700);
    splitPane.setDividerLocation(300);
  }

  private void repaintMenuPanel() {
    LibrarySystem.unregisterStateChange(leftPanel);
    leftPanel = new MenuPanel();
    LibrarySystem.registerStateChange(leftPanel);
    leftPanel.setLayout(new GridLayout(15, 1));
    leftPanel.setBackground(Color.LIGHT_GRAY);
    splitPane.setLeftComponent(leftPanel);
  }

  private void insertSplashImage() {
    // repaint background after success login
    mainPanel.removeAll();
    mainPanel.validate();
    mainPanel.repaint();

    ImageIcon image = new ImageIcon(Util.getImage("/library.jpg"));
    mainPanel.add(new JLabel(image));
    mainPanel.setBackground(Color.WHITE);
  }

  @Override
  public void onStateChanged(StateChangeEvent<MainWindowState> event) {
    MainWindowViewModel viewModel = event.getNewState().getData();
    MenuItem previousMenuItem =
        Optional.ofNullable(event.getOldState())
            .map(state -> state.getData().getSelectedMenuItem())
            .orElse(null);
    MenuItem selectedMenuItem = viewModel.getSelectedMenuItem();
    if (previousMenuItem != selectedMenuItem) {
      handleMenuItemChange(selectedMenuItem);
    }
  }

  private void handleMenuItemChange(MenuItem menuItem) {
    switch (menuItem) {
      case BOOKSHELF -> changeContentPanel(new BookshelfPanel());
      case LIBRARY_MEMBER -> changeContentPanel(new LibraryMemberPanel());
      case CHECKOUT_BOOK -> changeContentPanel(new CheckoutBookPanel());
      case SEARCH_OVERDUE_BOOK -> changeContentPanel(new SearchOverdueBookPanel());
      case SEARCH_MEMBER_CHECKOUT_RECORD -> changeContentPanel(
          new SearchMemberCheckoutRecordPanel());
      case LOGOUT -> {
        LibrarySystem.openWindow(LoginWindow.class);
        LibrarySystem.closeWindow(MainWindow.class);
      }
    }
  }

  private void changeContentPanel(JPanel contentPanel) {
    LibrarySystem.unregisterStateChange(mainPanel);
    LibrarySystem.registerStateChange(mainPanel);
    mainPanel = contentPanel;
    splitPane.setRightComponent(mainPanel);
    splitPane.setDividerLocation(300);
  }

  @Override
  public StatePath getListeningStatePath() {
    return AppStatePath.MAIN_WINDOW;
  }
}
