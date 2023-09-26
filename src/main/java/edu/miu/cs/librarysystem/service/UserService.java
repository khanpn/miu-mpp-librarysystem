package edu.miu.cs.librarysystem.service;

import edu.miu.cs.librarysystem.dataaccess.DataAccess;
import edu.miu.cs.librarysystem.dataaccess.DataAccessFacade;
import edu.miu.cs.librarysystem.dataaccess.User;
import java.util.Optional;

public class UserService {
  private final DataAccess DA;
  private static final UserService INSTANCE = new UserService();

  private UserService() {
    DA = DataAccessFacade.getInstance();
  }

  public static UserService getInstance() {
    return INSTANCE;
  }

  public Optional<User> findUser(String userId) {
    return Optional.ofNullable(DA.readUserMap().get(userId));
  }
}
