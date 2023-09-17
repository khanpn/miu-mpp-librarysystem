package edu.miu.cs.librarysystem.store.state;

import edu.miu.cs.librarysystem.store.core.state.State;
import edu.miu.cs.librarysystem.viewmodel.AuthenticationContextViewModel;

public class LoginState extends State<AuthenticationContextViewModel> {
  public LoginState(AuthenticationContextViewModel data) {
    super(AppStatePath.LOGIN, data);
  }
}
