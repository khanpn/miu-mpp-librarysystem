package com.miu.cs.librarysystem.store;

import com.miu.cs.librarysystem.store.action.AppAction;
import com.miu.cs.librarysystem.store.state.AppState;

public final class Dispatcher {
  private Dispatcher() {}

  public static <T extends AppState<?>> void dispatch(AppAction<?> action) {
    AppReducers.reduce(action);
  }
}
