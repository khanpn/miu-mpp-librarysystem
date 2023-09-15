package com.miu.cs.librarysystem.service;

import com.miu.cs.librarysystem.business.BasicAuthCredentials;
import com.miu.cs.librarysystem.dataaccess.User;
import com.miu.cs.librarysystem.exception.LoginException;
import java.util.Optional;

public class LoginService {
  private static final LoginService INSTANCE = new LoginService();

  private final UserService userService;

  private LoginService() {
    userService = UserService.getInstance();
  }

  public User login(BasicAuthCredentials credentials) throws LoginException {
    String userId = credentials.getUsername();
    Optional<User> userOpt = userService.findUser(userId);
    if (userOpt.isEmpty()) {
      throw new LoginException("ID " + userId + " not found");
    }
    User user = userOpt.get();
    if (!userOpt.get().getPassword().equals(credentials.getPassword())) {
      throw new LoginException("Password incorrect");
    }
    return user;
  }

  public static LoginService getInstance() {
    return INSTANCE;
  }
}
