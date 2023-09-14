package com.miu.cs.librarysystem.business;

public class BasicAuthCredentials {
  private String username;
  private String password;

  public BasicAuthCredentials(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
