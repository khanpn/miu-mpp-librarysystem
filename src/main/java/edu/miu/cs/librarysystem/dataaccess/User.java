package edu.miu.cs.librarysystem.dataaccess;

import java.io.Serializable;

public final class User implements Serializable {

  private static final long serialVersionUID = 5147265048973262104L;

  private String id;
  private String password;
  private Auth authorization;

  public User(String id, String pass, Auth auth) {
    this.id = id;
    this.password = pass;
    this.authorization = auth;
  }

  public String getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

  public Auth getAuthorization() {
    return authorization;
  }

  @Override
  public String toString() {
    return "[" + id + ":" + password + ", " + authorization.toString() + "]";
  }
}
