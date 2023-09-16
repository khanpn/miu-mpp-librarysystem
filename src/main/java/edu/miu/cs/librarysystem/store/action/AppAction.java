package edu.miu.cs.librarysystem.store.action;

public abstract class AppAction<T> {
  private final T data;

  public AppAction(T data) {
    this.data = data;
  }

  public T getData() {
    return data;
  }
}
