package com.miu.cs.librarysystem.system;

import com.miu.cs.librarysystem.dataaccess.DataAccess;
import com.miu.cs.librarysystem.dataaccess.DataAccessFacade;
import com.miu.cs.librarysystem.dataaccess.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginWindow extends JPanel implements LibWindow {
  /** */
  private static final long serialVersionUID = -1819843712126120428L;

  public static final LoginWindow INSTANCE = new LoginWindow();

  private boolean isInitialized = false;
  private JTextField username;
  private JPasswordField password;

  public void init() {
    clearContent();
    JLabel loginLabel = new JLabel("Sign In:");
    Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
    add(loginLabel);

    JSeparator s = new JSeparator();
    s.setOrientation(SwingConstants.HORIZONTAL);
    add(s);

    username = new JTextField(45);
    username.setMaximumSize(username.getPreferredSize());
    JLabel uLabel = new JLabel("Username:");
    //    uLabel.setFont(Util.makeSmallFont(uLabel.getFont()));
    //    add(uLabel);
    //    add(username);

    JPanel userControls = new JPanel(new BorderLayout(5, 5));
    JPanel userControlsLabels = new JPanel(new GridLayout(0, 1, 1, 1));
    JPanel userControlsFields = new JPanel(new GridLayout(0, 1, 1, 1));
    userControlsLabels.add(uLabel);
    userControlsFields.add(username);
    userControls.add(userControlsLabels, BorderLayout.WEST);
    userControls.add(userControlsFields, BorderLayout.CENTER);
    userControls.setBackground(Color.LIGHT_GRAY);
    userControlsFields.setBackground(Color.LIGHT_GRAY);
    userControlsLabels.setBackground(Color.LIGHT_GRAY);

    //    userControls.setBounds(5, 5, 100, 10);
    //    userControlsLabels.setBounds(5, 5, 100, 10);
    //    userControlsFields.setBounds(5, 5, 100, 10);

    add(userControls);

    password = new JPasswordField(45);
    //    password.setMaximumSize(password.getPreferredSize());
    //    password.setMargin(new Insets(0, 0, 0, 0));
    //    password.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    JLabel pLabel = new JLabel("Password:");
    //    pLabel.setFont(Util.makeSmallFont(pLabel.getFont()));
    //    add(pLabel);
    //    add(password);

    JPanel passwordControls = new JPanel(new BorderLayout(6, 6));
    JPanel passwordControlsLabels = new JPanel(new GridLayout(0, 1, 3, 3));
    JPanel passwordControlsFields = new JPanel(new GridLayout(0, 1, 3, 3));
    passwordControlsLabels.add(pLabel);
    passwordControlsFields.add(password);
    passwordControls.add(passwordControlsLabels, BorderLayout.WEST);
    passwordControls.add(passwordControlsFields, BorderLayout.CENTER);
    passwordControls.setBackground(Color.LIGHT_GRAY);
    passwordControlsLabels.setBackground(Color.LIGHT_GRAY);
    passwordControlsFields.setBackground(Color.LIGHT_GRAY);
    add(passwordControls);

    JButton loginButton = new JButton("Sign In");
    //    addLoginButtonListener(loginButton);
    //    add(loginButton);

    JPanel buttonControl = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 10));
    buttonControl.setBackground(Color.LIGHT_GRAY);

    buttonControl.add(loginButton);
    add(buttonControl);
    addLoginButtonListener(loginButton);

    setAlignmentY(Component.LEFT_ALIGNMENT);
    setInitialized(true);
  }

  public void clearContent() {
    removeAll();
    revalidate();
    repaint();
  }

  /* This class is a singleton */
  private LoginWindow() {
    init();
  }

  private void addLoginButtonListener(JButton butn) {
    butn.addActionListener(
        evt -> {
          String userId = username.getText();
          String userPass = password.getText();
          if (userId == null || userId.isBlank() || userPass == null || userPass.isBlank()) {
            JOptionPane.showMessageDialog(
                this, "Invalid username or password", "", JOptionPane.ERROR_MESSAGE);
            return;
          }
          DataAccess da = new DataAccessFacade();
          Map<String, User> users = da.readUserMap();
          if (!users.containsKey(userId)) {
            JOptionPane.showMessageDialog(this, "Invalid username", "", JOptionPane.ERROR_MESSAGE);
            return;
          }
          User user = users.get(userId);
          if (!userPass.equals(user.getPassword())) {
            JOptionPane.showMessageDialog(this, "Invalid password", "", JOptionPane.ERROR_MESSAGE);
            return;
          }
          username.setText("");
          password.setText("");
          System.out.println(user.getAuthorization());
          LibrarySystem.INSTANCE.setLoggedInUser(user);
          LibrarySystem.INSTANCE.init();
        });
  }

  @Override
  public boolean isInitialized() {
    return isInitialized;
  }

  @Override
  public void setInitialized(boolean val) {
    isInitialized = val;
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(
        () -> {
          LibrarySystem.INSTANCE.setTitle("Library System Application");
          LibrarySystem.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          LibrarySystem.INSTANCE.init();
          centerFrameOnDesktop(LibrarySystem.INSTANCE);
          LibrarySystem.INSTANCE.setVisible(true);
        });
  }

  public static void centerFrameOnDesktop(Component f) {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    int height = toolkit.getScreenSize().height;
    int width = toolkit.getScreenSize().width;
    int frameHeight = f.getSize().height;
    int frameWidth = f.getSize().width;
    f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
  }
}
