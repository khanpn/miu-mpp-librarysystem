package edu.miu.cs.librarysystem.store.core;

import edu.miu.cs.librarysystem.store.core.action.Action;
import edu.miu.cs.librarysystem.store.core.state.State;

public final class Dispatcher {
  private Dispatcher() {}

  public static <T extends State<?>> void dispatch(Action<?> action) {
    Reducers.reduce(action);
  }
}
