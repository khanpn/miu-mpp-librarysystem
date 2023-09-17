package edu.miu.cs.librarysystem.store.core.action;

public abstract class Action<T> {
  private final T data;

  public Action(T data) {
    this.data = data;
  }

  public T getData() {
    return data;
  }
}
