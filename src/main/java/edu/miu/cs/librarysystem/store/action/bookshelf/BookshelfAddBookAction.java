package edu.miu.cs.librarysystem.store.action.bookshelf;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.store.core.action.Action;

public class BookshelfAddBookAction extends Action<Book> {
  public BookshelfAddBookAction(Book data) {
    super(data);
  }
}
