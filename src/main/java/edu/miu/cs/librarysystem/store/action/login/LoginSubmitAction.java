package edu.miu.cs.librarysystem.store.action.login;

import edu.miu.cs.librarysystem.business.BasicAuthCredentials;
import edu.miu.cs.librarysystem.store.action.AppAction;

public class LoginSubmitAction extends AppAction<BasicAuthCredentials> {
  public LoginSubmitAction(BasicAuthCredentials data) {
    super(data);
  }
}
