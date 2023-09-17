package edu.miu.cs.librarysystem.viewmodel;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.business.CheckoutRecordEntry;
import edu.miu.cs.librarysystem.business.LibraryMember;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchOverdueBookViewModel {
  private final String bookId;
  private final Book book;
  private final List<Map<LibraryMember, List<CheckoutRecordEntry>>> overdueCheckoutRecordEntries;
  private final boolean clearSearch;

  public SearchOverdueBookViewModel(boolean clearSearch) {
    this.clearSearch = clearSearch;
    this.bookId = null;
    this.book = null;
    this.overdueCheckoutRecordEntries = new ArrayList<>();
  }

  public SearchOverdueBookViewModel(
      String bookId,
      Book book,
      List<Map<LibraryMember, List<CheckoutRecordEntry>>> overdueCheckoutRecordEntries) {
    this.bookId = bookId;
    this.book = book;
    this.overdueCheckoutRecordEntries = overdueCheckoutRecordEntries;
    this.clearSearch = false;
  }

  public String getBookId() {
    return bookId;
  }

  public Book getBook() {
    return book;
  }

  public List<Map<LibraryMember, List<CheckoutRecordEntry>>> getOverdueCheckoutRecordEntries() {
    return overdueCheckoutRecordEntries;
  }

  public boolean isClearSearch() {
    return clearSearch;
  }
}
