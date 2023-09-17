package edu.miu.cs.librarysystem.store.action.login;

import edu.miu.cs.librarysystem.business.BasicAuthCredentials;
import edu.miu.cs.librarysystem.store.core.action.Action;

public class LoginSubmitAction extends Action<BasicAuthCredentials> {
  public LoginSubmitAction(BasicAuthCredentials data) {
    super(data);
  }
}
