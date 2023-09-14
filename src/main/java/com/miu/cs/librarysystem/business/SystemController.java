package com.miu.cs.librarysystem.business;

import com.miu.cs.librarysystem.dataaccess.DataAccess;
import com.miu.cs.librarysystem.dataaccess.DataAccessFacade;
import com.miu.cs.librarysystem.dataaccess.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemController implements ControllerInterface {
  private static final SystemController INSTANCE = new SystemController();

  public static User login(BasicAuthCredentials credentials) throws LoginException {
    DataAccess da = new DataAccessFacade();
    HashMap<String, User> map = da.readUserMap();
    String id = credentials.getUsername();
    if (!map.containsKey(id)) {
      throw new LoginException("ID " + id + " not found");
    }
    String passwordFound = map.get(id).getPassword();
    if (!passwordFound.equals(credentials.getPassword())) {
      throw new LoginException("Password incorrect");
    }
    return map.get(id);
  }

  @Override
  public List<String> allMemberIds() {
    DataAccess da = new DataAccessFacade();
    List<String> retval = new ArrayList<>();
    retval.addAll(da.readMemberMap().keySet());
    return retval;
  }

  @Override
  public List<String> allBookIds() {
    DataAccess da = new DataAccessFacade();
    List<String> retval = new ArrayList<>();
    retval.addAll(da.readBooksMap().keySet());
    return retval;
  }
}
