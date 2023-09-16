package edu.miu.cs.librarysystem.ui.listener;

import edu.miu.cs.librarysystem.business.BasicAuthCredentials;
import edu.miu.cs.librarysystem.store.Dispatcher;
import edu.miu.cs.librarysystem.store.action.login.LoginSubmitAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class LoginSubmitActionListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    Dispatcher.dispatch(new LoginSubmitAction(getCredentials()));
  }

  public abstract BasicAuthCredentials getCredentials();
}
