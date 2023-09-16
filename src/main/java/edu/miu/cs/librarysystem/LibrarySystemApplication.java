package edu.miu.cs.librarysystem;

import edu.miu.cs.librarysystem.dataaccess.DataAccessFacade;
import edu.miu.cs.librarysystem.dataaccess.TestData;
import edu.miu.cs.librarysystem.ui.window.LoginWindow;
import edu.miu.cs.librarysystem.util.Util;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class LibrarySystemApplication {
  public static void main(String[] args) {
    EventQueue.invokeLater(LibrarySystemApplication::run);
  }

  private static void run() {
    try {
      if (Objects.requireNonNull(DataAccessFacade.getDataPath().toFile().list()).length == 0) {
        TestData.insertTestData();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    LoginWindow loginWindow = new LoginWindow();
    Util.centerFrameOnDesktop(loginWindow);
    loginWindow.setVisible(true);

    // for testing
    //    AppStore.setState(
    //        new LoginState(new AuthenticationContextViewModel(new User("103", "111",
    // Auth.BOTH))));
  }
}
