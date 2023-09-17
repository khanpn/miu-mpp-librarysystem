package edu.miu.cs.librarysystem.ui.window;

import edu.miu.cs.librarysystem.business.BasicAuthCredentials;
import edu.miu.cs.librarysystem.store.core.StateChangeEvent;
import edu.miu.cs.librarysystem.store.core.StateChangeListener;
import edu.miu.cs.librarysystem.store.core.state.StatePath;
import edu.miu.cs.librarysystem.store.state.AppStatePath;
import edu.miu.cs.librarysystem.store.state.LoginState;
import edu.miu.cs.librarysystem.ui.LibWindow;
import edu.miu.cs.librarysystem.ui.LibrarySystem;
import edu.miu.cs.librarysystem.ui.listener.LoginSubmitActionListener;
import edu.miu.cs.librarysystem.util.TypographyUtils;
import edu.miu.cs.librarysystem.util.Util;
import java.awt.*;
import javax.swing.*;

public class LoginWindow extends JFrame implements LibWindow, StateChangeListener<LoginState> {
  private JPanel contentPanel;
  private JLabel headingLabel;
  private JTextField usernameField;
  private JTextField passwordField;
  private JButton loginButton;
  private JLabel errorMessageLabel;

  public void init() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(500, 300);
    setTitle("Login to Library System");
    setContentPane(contentPanel);
    Util.centerFrameOnDesktop(this);
    TypographyUtils.applyHeadingStyle(headingLabel);
    TypographyUtils.applyDangerStyle(errorMessageLabel);
    errorMessageLabel.setText("");
    loginButton.addActionListener(
        new LoginSubmitActionListener() {
          @Override
          public BasicAuthCredentials getCredentials() {
            return new BasicAuthCredentials(usernameField.getText(), passwordField.getText());
          }
        });
    Util.addButtonHover(loginButton);
  }

  @Override
  public void onStateChanged(StateChangeEvent<LoginState> event) {
    LoginState loginState = event.getNewState();
    if (!loginState.getData().isAuthenticated()) {
      errorMessageLabel.setText(loginState.getData().getLoginException().getMessage());
      return;
    }

    EventQueue.invokeLater(
        () -> {
          LibrarySystem.openWindow(MainWindow.class);
          LibrarySystem.closeWindow(getClass());
        });
  }

  @Override
  public StatePath getListeningStatePath() {
    return AppStatePath.LOGIN;
  }
}
