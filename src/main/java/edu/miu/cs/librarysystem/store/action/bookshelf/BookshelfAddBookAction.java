package edu.miu.cs.librarysystem.store.action.bookshelf;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.store.action.AppAction;

public class BookshelfAddBookAction extends AppAction<Book> {
  public BookshelfAddBookAction(Book data) {
    super(data);
  }
}
