package com.miu.cs.librarysystem.store.action;

import com.miu.cs.librarysystem.business.BasicAuthCredentials;

public class LoginSubmitAction extends AppAction<BasicAuthCredentials> {
  public LoginSubmitAction(BasicAuthCredentials data) {
    super(data);
  }
}
