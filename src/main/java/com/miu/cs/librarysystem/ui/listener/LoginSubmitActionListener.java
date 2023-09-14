package com.miu.cs.librarysystem.ui.listener;

import com.miu.cs.librarysystem.business.BasicAuthCredentials;
import com.miu.cs.librarysystem.store.Dispatcher;
import com.miu.cs.librarysystem.store.action.LoginSubmitAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class LoginSubmitActionListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    Dispatcher.dispatch(new LoginSubmitAction(getCredentials()));
  }

  public abstract BasicAuthCredentials getCredentials();
}
