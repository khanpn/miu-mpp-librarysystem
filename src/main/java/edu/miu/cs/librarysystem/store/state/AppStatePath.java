package edu.miu.cs.librarysystem.store.state;

import edu.miu.cs.librarysystem.store.core.state.StatePath;

public enum AppStatePath implements StatePath {
  LOGIN("login"),
  BOOKSHELF("bookshelf");
  private final String value;

  AppStatePath(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
