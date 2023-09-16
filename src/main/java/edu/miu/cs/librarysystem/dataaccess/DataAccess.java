package edu.miu.cs.librarysystem.dataaccess;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.business.LibraryMember;
import java.util.HashMap;
import java.util.Optional;

public interface DataAccess {
  HashMap<String, Book> readBooksMap();

  HashMap<String, User> readUserMap();

  HashMap<String, LibraryMember> readMemberMap();

  LibraryMember saveNewMember(LibraryMember member);

  LibraryMember deleteMember(String memberId);

  void saveBook(Book book);

  Optional<Book> findBookByIsbn(String isbn);

  Optional<LibraryMember> findMemberById(String memberId);
}
