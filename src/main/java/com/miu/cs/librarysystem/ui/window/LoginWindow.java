package com.miu.cs.librarysystem.ui.window;

import com.miu.cs.librarysystem.business.BasicAuthCredentials;
import com.miu.cs.librarysystem.store.AppStateChangeEvent;
import com.miu.cs.librarysystem.store.AppStateChangeListener;
import com.miu.cs.librarysystem.store.AppStore;
import com.miu.cs.librarysystem.store.state.AppStatePath;
import com.miu.cs.librarysystem.store.state.LoginState;
import com.miu.cs.librarysystem.system.LibrarySystem;
import com.miu.cs.librarysystem.system.TypographyUtils;
import com.miu.cs.librarysystem.system.Util;
import com.miu.cs.librarysystem.ui.listener.LoginSubmitActionListener;
import java.awt.*;
import javax.swing.*;

public class LoginWindow extends JFrame implements AppStateChangeListener<LoginState> {
  private JPanel contentPanel;
  private JPanel topPanel;
  private JLabel headingLabel;
  private JPanel centerPanel;
  private JLabel usernameLabel;
  private JTextField usernameField;
  private JPanel usernamePanel;
  private JPanel passwordPanel;
  private JLabel passwordLabel;
  private JTextField passwordField;
  private JPanel buttonPanel;
  private JButton loginButton;
  private JPanel formPanel;
  private JPanel messagePanel;
  private JLabel errorMessageLabel;

  public LoginWindow() throws HeadlessException {
    init();
    AppStore.registerOnStateChange(getListeningStatePath(), this);
  }

  private void init() {
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
  }

  @Override
  public void onStateChanged(AppStateChangeEvent<LoginState> event) {
    LoginState loginState = event.getNewState();
    if (!loginState.getData().isAuthenticated()) {
      errorMessageLabel.setText(loginState.getData().getLoginException().getMessage());
      return;
    }

    EventQueue.invokeLater(
        () -> {
          LibrarySystem.INSTANCE.setTitle("Sample Library Application");
          LibrarySystem.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          LibrarySystem.INSTANCE.init();
          Util.centerFrameOnDesktop(LibrarySystem.INSTANCE);
          LibrarySystem.INSTANCE.setVisible(true);
          AppStore.unregisterOnStateChange(getListeningStatePath(), this);
          dispose();
        });
  }

  @Override
  public AppStatePath getListeningStatePath() {
    return AppStatePath.LOGIN;
  }
}