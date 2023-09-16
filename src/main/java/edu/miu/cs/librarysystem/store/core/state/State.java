package edu.miu.cs.librarysystem.store.core.state;

public abstract class State<T> {
  private StatePath path;
  private T data;

  public State(StatePath path, T data) {
    this.path = path;
    this.data = data;
  }

  public StatePath getPath() {
    return path;
  }

  public T getData() {
    return data;
  }
}
