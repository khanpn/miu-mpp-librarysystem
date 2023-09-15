package com.miu.cs.librarysystem.viewmodel;

import com.miu.cs.librarysystem.business.Author;
import com.miu.cs.librarysystem.business.Book;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookshelfViewModel {
  private List<Book> books;
  private Book selectedBook;
  private Set<Author> authors;

  public BookshelfViewModel(List<Book> books) {
    this.books = books;
    this.authors =
        books.stream()
            .filter(book -> book.getAuthors() != null)
            .flatMap(book -> book.getAuthors().stream())
            .collect(Collectors.toSet());
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

  public Set<Author> getAuthors() {
    return authors;
  }
}
