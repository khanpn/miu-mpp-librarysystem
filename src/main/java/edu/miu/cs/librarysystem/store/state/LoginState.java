package edu.miu.cs.librarysystem.store.state;

import edu.miu.cs.librarysystem.viewmodel.AuthenticationContextViewModel;

public class LoginState extends AppState<AuthenticationContextViewModel> {
  public LoginState(AuthenticationContextViewModel data) {
    super(AppStatePath.LOGIN, data);
  }
}
