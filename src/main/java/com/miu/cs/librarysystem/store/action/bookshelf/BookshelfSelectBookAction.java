package com.miu.cs.librarysystem.store.action.bookshelf;

import com.miu.cs.librarysystem.business.Book;
import com.miu.cs.librarysystem.store.action.AppAction;

public class BookshelfSelectBookAction extends AppAction<Book> {
  public BookshelfSelectBookAction(Book data) {
    super(data);
  }
}
