package com.miu.cs.librarysystem.store.reducer;

import com.miu.cs.librarysystem.business.BasicAuthCredentials;
import com.miu.cs.librarysystem.dataaccess.User;
import com.miu.cs.librarysystem.exception.LoginException;
import com.miu.cs.librarysystem.service.LoginService;
import com.miu.cs.librarysystem.store.action.AppAction;
import com.miu.cs.librarysystem.store.action.login.LoginSubmitAction;
import com.miu.cs.librarysystem.store.state.LoginState;
import com.miu.cs.librarysystem.viewmodel.AuthenticationContextViewModel;

public class LoginReducer implements Reducer<LoginState, LoginSubmitAction> {

  @Override
  public LoginState reduce(LoginSubmitAction action) {
    BasicAuthCredentials basicAuthCredentials = action.getData();
    User authUser;
    try {
      authUser = LoginService.getInstance().login(basicAuthCredentials);
    } catch (LoginException e) {
      return new LoginState(new AuthenticationContextViewModel(e));
    }
    return new LoginState(new AuthenticationContextViewModel(authUser));
  }

  @Override
  public <O extends AppAction<?>> boolean canReduce(O action) {
    return action instanceof LoginSubmitAction;
  }
}
