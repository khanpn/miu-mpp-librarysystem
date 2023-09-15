package com.miu.cs.librarysystem;

import com.miu.cs.librarysystem.ui.window.LoginWindow;
import com.miu.cs.librarysystem.util.Util;
import java.awt.*;

public class LibrarySystemApplication {
  public static void main(String[] args) {
    EventQueue.invokeLater(LibrarySystemApplication::run);
  }

  private static void run() {
    LoginWindow loginWindow = new LoginWindow();
    Util.centerFrameOnDesktop(loginWindow);
    loginWindow.setVisible(true);

    // for testing
    //    AppStore.setState(
    //        new LoginState(new AuthenticationContextViewModel(new User("103", "111",
    // Auth.BOTH))));
  }
}
