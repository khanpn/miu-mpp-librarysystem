package com.miu.cs.librarysystem.store.reducer;

import com.miu.cs.librarysystem.business.AuthenticationContext;
import com.miu.cs.librarysystem.business.BasicAuthCredentials;
import com.miu.cs.librarysystem.business.LoginException;
import com.miu.cs.librarysystem.business.SystemController;
import com.miu.cs.librarysystem.dataaccess.User;
import com.miu.cs.librarysystem.store.action.AppAction;
import com.miu.cs.librarysystem.store.action.LoginSubmitAction;
import com.miu.cs.librarysystem.store.state.LoginState;

public class LoginReducer implements Reducer<LoginState, LoginSubmitAction> {

  @Override
  public LoginState reduce(LoginSubmitAction action) {
    BasicAuthCredentials basicAuthCredentials = action.getData();
    User authUser;
    try {
      authUser = SystemController.login(basicAuthCredentials);
    } catch (LoginException e) {
      return new LoginState(new AuthenticationContext(e));
    }
    return new LoginState(new AuthenticationContext(authUser));
  }

  @Override
  public <O extends AppAction<?>> boolean canReduce(O action) {
    return action instanceof LoginSubmitAction;
  }
}
