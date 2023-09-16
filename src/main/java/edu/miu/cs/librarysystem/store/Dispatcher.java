package edu.miu.cs.librarysystem.store;

import edu.miu.cs.librarysystem.store.action.AppAction;
import edu.miu.cs.librarysystem.store.state.AppState;

public final class Dispatcher {
  private Dispatcher() {}

  public static <T extends AppState<?>> void dispatch(AppAction<?> action) {
    AppReducers.reduce(action);
  }
}
