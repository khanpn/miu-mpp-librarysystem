package com.miu.cs.librarysystem.business;

import com.miu.cs.librarysystem.dataaccess.User;
import java.util.Date;

public class AuthenticationContext {
  private final User authUser;
  private Date loggedDate;
  private LoginException loginException;

  public AuthenticationContext(User authUser) {
    this.authUser = authUser;
    this.loggedDate = new Date();
  }

  public AuthenticationContext(LoginException loginException) {
    this.authUser = null;
    this.loginException = loginException;
  }

  public boolean isAuthenticated() {
    return authUser != null && loginException == null;
  }

  public User getAuthUser() {
    return authUser;
  }

  public Date getLoggedDate() {
    return loggedDate;
  }

  public LoginException getLoginException() {
    return loginException;
  }
}
