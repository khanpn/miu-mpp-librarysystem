package com.miu.cs.librarysystem.store.action.bookshelf;

import com.miu.cs.librarysystem.business.Book;
import com.miu.cs.librarysystem.store.action.AppAction;

public class BookshelfAddBookAction extends AppAction<Book> {
  public BookshelfAddBookAction(Book data) {
    super(data);
  }
}
