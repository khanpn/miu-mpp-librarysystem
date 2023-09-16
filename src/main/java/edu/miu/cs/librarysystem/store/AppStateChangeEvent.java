package edu.miu.cs.librarysystem.store;

import edu.miu.cs.librarysystem.store.state.AppState;

public class AppStateChangeEvent<T extends AppState<?>> {
  private T oldState;
  private T newState;

  public AppStateChangeEvent(T newState, T oldState) {
    this.oldState = oldState;
    this.newState = newState;
  }

  public T getOldState() {
    return oldState;
  }

  public T getNewState() {
    return newState;
  }
}
