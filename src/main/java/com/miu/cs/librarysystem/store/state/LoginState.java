package com.miu.cs.librarysystem.store.state;

import com.miu.cs.librarysystem.business.AuthenticationContext;

public class LoginState extends AppState<AuthenticationContext> {
  public LoginState(AuthenticationContext data) {
    super(AppStatePath.LOGIN, data);
  }
}
