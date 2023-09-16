package edu.miu.cs.librarysystem.service;

import edu.miu.cs.librarysystem.business.*;
import edu.miu.cs.librarysystem.dataaccess.DataAccess;
import edu.miu.cs.librarysystem.dataaccess.DataAccessFacade;
import edu.miu.cs.librarysystem.exception.LibrarySystemException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BookService {
  private static final BookService INSTANCE = new BookService();
  private final DataAccess DA = new DataAccessFacade();

  private BookService() {}

  public static BookService getInstance() {
    return INSTANCE;
  }

  public Book save(Book book) {
    DA.saveBook(book);
    return book;
  }

  public Optional<Book> findById(String bookId) {
    return DA.findBookByIsbn(bookId);
  }

  public Collection<Book> getAllBooks() {
    return DA.readBooksMap().values();
  }

  public void checkBook(String memberId, String isbn) throws LibrarySystemException {
    DataAccess dao = new DataAccessFacade();
    // search member from data storage
    LibraryMember member =
        dao.findMemberById(memberId)
            .orElseThrow(
                () -> new LibrarySystemException("Member id ID: " + memberId + " not found."));

    // search book from storage using ISBN
    Book book =
        DA.findBookByIsbn(isbn)
            .orElseThrow(
                () -> new LibrarySystemException("Book with ISBN: " + isbn + " not found."));
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

  public List<CheckoutHistory> getCheckoutHistory() {
    Collection<LibraryMember> members = DA.readMemberMap().values();

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
}
