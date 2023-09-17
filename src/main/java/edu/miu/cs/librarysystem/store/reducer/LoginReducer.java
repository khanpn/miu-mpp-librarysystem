package edu.miu.cs.librarysystem.store.reducer;

import edu.miu.cs.librarysystem.business.BasicAuthCredentials;
import edu.miu.cs.librarysystem.dataaccess.User;
import edu.miu.cs.librarysystem.exception.LoginException;
import edu.miu.cs.librarysystem.service.LoginService;
import edu.miu.cs.librarysystem.store.action.login.LoginSubmitAction;
import edu.miu.cs.librarysystem.store.core.action.Action;
import edu.miu.cs.librarysystem.store.core.reducer.Reducer;
import edu.miu.cs.librarysystem.store.state.LoginState;
import edu.miu.cs.librarysystem.viewmodel.AuthenticationContextViewModel;

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
  public <O extends Action<?>> boolean canReduce(O action) {
    return action instanceof LoginSubmitAction;
  }
}
