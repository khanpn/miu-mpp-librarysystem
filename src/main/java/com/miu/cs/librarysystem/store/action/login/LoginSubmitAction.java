package com.miu.cs.librarysystem.store.action.login;

import com.miu.cs.librarysystem.business.BasicAuthCredentials;
import com.miu.cs.librarysystem.store.action.AppAction;

public class LoginSubmitAction extends AppAction<BasicAuthCredentials> {
  public LoginSubmitAction(BasicAuthCredentials data) {
    super(data);
  }
}
