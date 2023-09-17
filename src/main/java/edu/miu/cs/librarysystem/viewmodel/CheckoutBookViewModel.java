package edu.miu.cs.librarysystem.viewmodel;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.business.CheckoutHistory;
import edu.miu.cs.librarysystem.business.LibraryMember;
import edu.miu.cs.librarysystem.exception.LibrarySystemException;
import java.util.List;

public class CheckoutBookViewModel {
  private final List<LibraryMember> libraryMembers;
  private final List<Book> books;
  private final List<CheckoutHistory> checkoutHistories;

  private final LibrarySystemException exception;
  private final boolean checkout = false;

  public CheckoutBookViewModel(
      List<LibraryMember> libraryMembers,
      List<Book> books,
      List<CheckoutHistory> checkoutHistories) {
    this.libraryMembers = libraryMembers;
    this.books = books;
    this.checkoutHistories = checkoutHistories;
    this.exception = null;
  }

  public CheckoutBookViewModel(boolean checkout) {
    if (!checkout) {
      throw new IllegalArgumentException(
          "This usecase is for checked out book only, passed argument must be always true");
    }
    this.exception = null;
    this.libraryMembers = null;
    this.books = null;
    this.checkoutHistories = null;
  }

  public CheckoutBookViewModel(LibrarySystemException exception) {
    this.exception = exception;
    this.libraryMembers = null;
    this.books = null;
    this.checkoutHistories = null;
  }

  public List<LibraryMember> getLibraryMembers() {
    return libraryMembers;
  }

  public List<Book> getBooks() {
    return books;
  }

  public List<CheckoutHistory> getCheckoutHistories() {
    return checkoutHistories;
  }

  public LibrarySystemException getException() {
    return exception;
  }

  public boolean isCheckout() {
    return checkout;
  }
}
