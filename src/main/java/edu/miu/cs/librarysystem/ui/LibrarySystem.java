package edu.miu.cs.librarysystem.ui;

import edu.miu.cs.librarysystem.store.core.StateChangeListener;
import edu.miu.cs.librarysystem.store.core.Store;
import edu.miu.cs.librarysystem.ui.window.LibWindow;
import edu.miu.cs.librarysystem.ui.window.LoginWindow;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class LibrarySystem {
  private static final Map<Class<?>, LibWindow> CREATED_WINDOWS = new HashMap<>();

  private LibrarySystem() {}

  public static void start() {
    openWindow(LoginWindow.class);
  }

  public static <T extends LibWindow> void openWindow(Class<T> libWindowClass) {
    try {
      if (CREATED_WINDOWS.containsKey(libWindowClass)) {
        closeWindow(libWindowClass);
      }
      LibWindow libWindow = libWindowClass.getDeclaredConstructor().newInstance();
      CREATED_WINDOWS.put(libWindowClass, libWindow);
      openWindow(libWindow);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static <T extends LibWindow> void openWindow(T libWindow) {
    registerStateChange(libWindow);
    libWindow.init();
    libWindow.setVisible(true);
  }

  public static <T extends LibWindow> void closeWindow(Class<T> libWindowClass) {
    Optional.ofNullable(CREATED_WINDOWS.remove(libWindowClass))
        .ifPresent(
            libWindow -> {
              unregisterStateChange(libWindow);
              libWindow.dispose();
            });
  }

  public static void registerStateChange(Object object) {
    if (object instanceof StateChangeListener<?> stateChangeListener) {
      Store.registerOnStateChange(stateChangeListener.getListeningStatePath(), stateChangeListener);
    }
  }

  public static void unregisterStateChange(Object object) {
    if (object instanceof StateChangeListener<?> stateChangeListener) {
      Store.unregisterOnStateChange(
          stateChangeListener.getListeningStatePath(), stateChangeListener);
    }
  }
}
