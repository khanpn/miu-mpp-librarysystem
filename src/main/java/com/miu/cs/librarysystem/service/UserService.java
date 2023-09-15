package com.miu.cs.librarysystem.service;

import com.miu.cs.librarysystem.dataaccess.DataAccess;
import com.miu.cs.librarysystem.dataaccess.DataAccessFacade;
import com.miu.cs.librarysystem.dataaccess.User;
import java.util.Optional;

public class UserService {
  private final DataAccess DA = new DataAccessFacade();
  private static final UserService INSTANCE = new UserService();

  private UserService() {}

  public static UserService getInstance() {
    return INSTANCE;
  }

  public Optional<User> findUser(String userId) {
    return Optional.ofNullable(DA.readUserMap().get(userId));
  }
}
