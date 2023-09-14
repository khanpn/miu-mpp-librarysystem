package com.miu.cs.librarysystem.store.state;

public enum AppStatePath {
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
