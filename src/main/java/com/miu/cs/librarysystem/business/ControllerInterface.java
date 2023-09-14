package com.miu.cs.librarysystem.business;

import java.util.Collection;
import java.util.List;

public interface ControllerInterface {

  List<String> allMemberIds();

  List<String> allBookIds();

  List<LibraryMember> allLibraryMembers();

  void deleteMember(String memberId);

  void saveMember(LibraryMember member);

  LibraryMember getLibraryMemberById(String memberId);

  Collection<Book> allBooks();

  List<CheckoutHistory> getCheckoutHistory();

  Book getBookByISBN(String isbn);

  void saveBook(Book book);

  LibraryMember findMemberById(String memberId);

  void checkBook(String memberId, String isbn) throws LibrarySystemException;
}
