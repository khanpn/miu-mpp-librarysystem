package com.miu.cs.librarysystem.store.action.bookshelf;

import com.miu.cs.librarysystem.business.Book;
import com.miu.cs.librarysystem.store.action.AppAction;

public class BookshelfUpdateBookAction extends AppAction<Book> {
  public BookshelfUpdateBookAction(Book data) {
    super(data);
  }
}
