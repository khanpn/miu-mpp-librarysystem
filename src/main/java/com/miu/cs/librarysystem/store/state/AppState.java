package com.miu.cs.librarysystem.store.state;

public abstract class AppState<T> {
  private AppStatePath path;
  private T data;

  public AppState(AppStatePath path, T data) {
    this.path = path;
    this.data = data;
  }

  public AppStatePath getPath() {
    return path;
  }

  public T getData() {
    return data;
  }
}
