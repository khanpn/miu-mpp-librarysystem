package edu.miu.cs.librarysystem;

import edu.miu.cs.librarysystem.dataaccess.DataAccessFacade;
import edu.miu.cs.librarysystem.dataaccess.TestData;
import edu.miu.cs.librarysystem.store.AppStoreModule;
import edu.miu.cs.librarysystem.ui.LibrarySystem;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class LibrarySystemApplication {
  public static void main(String[] args) {
    AppStoreModule.initialize();
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
    LibrarySystem.start();
  }
}
