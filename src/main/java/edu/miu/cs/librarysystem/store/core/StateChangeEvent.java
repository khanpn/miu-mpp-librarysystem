package edu.miu.cs.librarysystem.store.core;

import edu.miu.cs.librarysystem.store.core.state.State;

public class StateChangeEvent<T extends State<?>> {
  private T oldState;
  private T newState;

  public StateChangeEvent(T newState, T oldState) {
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
