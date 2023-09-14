package com.miu.cs.librarysystem;

import com.miu.cs.librarysystem.system.Util;
import com.miu.cs.librarysystem.ui.window.LoginWindow;
import java.awt.EventQueue;

public class LibrarySystemApplication {
  public static void main(String[] args) {
    EventQueue.invokeLater(
        () -> {
          LoginWindow loginWindow = new LoginWindow();
          Util.centerFrameOnDesktop(loginWindow);
          loginWindow.setVisible(true);
        });
  }
}
