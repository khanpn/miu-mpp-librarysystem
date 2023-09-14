package com.miu.cs.librarysystem.business;

import java.util.List;

public class BookshelfViewModel {
  private List<Book> books;
  private Book selectedBook;

  public BookshelfViewModel(List<Book> books) {
    this.books = books;
  }

  public BookshelfViewModel(List<Book> books, Book selectedBook) {
    this.books = books;
    this.selectedBook = selectedBook;
  }

  public List<Book> getBooks() {
    return books;
  }

  public Book getSelectedBook() {
    return selectedBook;
  }
}
