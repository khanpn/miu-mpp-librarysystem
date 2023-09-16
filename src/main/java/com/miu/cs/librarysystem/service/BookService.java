package com.miu.cs.librarysystem.service;

import com.miu.cs.librarysystem.business.Book;
import com.miu.cs.librarysystem.dataaccess.DataAccess;
import com.miu.cs.librarysystem.dataaccess.DataAccessFacade;
import java.util.Collection;

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

  public Collection<Book> getAllBooks() {
    return DA.readBooksMap().values();
  }
}
