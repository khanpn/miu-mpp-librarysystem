package com.miu.cs.librarysystem.viewmodel;

import com.miu.cs.librarysystem.dataaccess.User;
import com.miu.cs.librarysystem.exception.LoginException;
import java.util.Date;

public class AuthenticationContextViewModel {
  private final User authUser;
  private Date loggedDate;
  private LoginException loginException;

  public AuthenticationContextViewModel(User authUser) {
    this.authUser = authUser;
    this.loggedDate = new Date();
  }

  public AuthenticationContextViewModel(LoginException loginException) {
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
