package com.miu.cs.librarysystem.dataaccess;

import com.miu.cs.librarysystem.business.Book;
import com.miu.cs.librarysystem.business.LibraryMember;
import java.util.HashMap;

public interface DataAccess {
  public HashMap<String, Book> readBooksMap();

  public HashMap<String, User> readUserMap();

  public HashMap<String, LibraryMember> readMemberMap();

  public void saveNewMember(LibraryMember member);
}
