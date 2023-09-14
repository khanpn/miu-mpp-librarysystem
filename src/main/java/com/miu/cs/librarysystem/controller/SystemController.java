package com.miu.cs.librarysystem.controller;

import com.miu.cs.librarysystem.business.*;
import com.miu.cs.librarysystem.dataaccess.DataAccess;
import com.miu.cs.librarysystem.dataaccess.DataAccessFacade;
import com.miu.cs.librarysystem.dataaccess.User;
import com.miu.cs.librarysystem.exception.LibrarySystemException;
import com.miu.cs.librarysystem.exception.LoginException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SystemController implements ControllerInterface {
  private static final SystemController INSTANCE = new SystemController();

  private DataAccess da = new DataAccessFacade();

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

  @Override
  public void saveMember(LibraryMember member) {
    da.saveNewMember(member);
  }

  @Override
  public List<LibraryMember> allLibraryMembers() {
    return da.readMemberMap().values().stream().toList();
  }

  @Override
  public void deleteMember(String memberId) {
    da.deleteMember(memberId);
  }

  @Override
  public LibraryMember getLibraryMemberById(String memberId) {
    Collection<LibraryMember> members = da.readMemberMap().values();
    for (LibraryMember member : members) {
      if (member.getMemberId().equals(memberId)) {
        return member;
      }
    }
    return null;
  }

  @Override
  public List<CheckoutHistory> getCheckoutHistory() {
    Collection<LibraryMember> members = da.readMemberMap().values();

    List<CheckoutRecord> records =
        members.stream()
            .map(LibraryMember::getCheckoutRecords)
            .filter(checkoutRecords -> checkoutRecords.size() > 0)
            .flatMap(List::stream)
            .toList();

    List<CheckoutHistory> history = new ArrayList<>();
    records.forEach(
        record ->
            record
                .getEntries()
                .forEach(
                    entry ->
                        history.add(
                            new CheckoutHistory(
                                entry.getCopy(),
                                record.getMember(),
                                entry.getCheckoutDate(),
                                entry.getDueDate()))));

    return history;
  }

  @Override
  public Book getBookByISBN(String isbn) {
    return da.findBookByIsbn(isbn).orElse(null);
  }

  @Override
  public void saveBook(Book book) {
    da.saveBook(book);
  }

  @Override
  public LibraryMember findMemberById(String memberId) {
    return da.findMemberById(memberId).orElse(null);
  }

  @Override
  public Collection<Book> allBooks() {
    return da.readBooksMap().values();
  }

  public static Collection<Book> getAllBooks() {
    return INSTANCE.allBooks();
  }

  @Override
  public void checkBook(String memberId, String isbn) throws LibrarySystemException {
    DataAccess dao = new DataAccessFacade();
    // search member from data storage
    LibraryMember member =
        dao.findMemberById(memberId)
            .orElseThrow(
                () -> new LibrarySystemException("Member id ID: " + memberId + " not found."));

    // search book from storage using ISBN
    Book book = getBookByISBN(isbn);
    if (book == null) {
      throw new LibrarySystemException("Book with ISBN: " + isbn + " not found.");
    }
    // Check if the book is available
    if (!book.isAvailable()) {
      throw new LibrarySystemException("Book is not available for checkout");
    }

    // call nextNextAvailableCopy
    BookCopy copy = book.getNextAvailableCopy();

    // call checkout method from a member
    // mark the copy that is not available
    // create checkoutEntry
    // Add checkoutEntry to CheckoutRecord
    member.checkout(copy, book.getMaxCheckoutLength());
    // save member
    dao.saveNewMember(member);
    // save book
    dao.saveBook(book);
  }
}
