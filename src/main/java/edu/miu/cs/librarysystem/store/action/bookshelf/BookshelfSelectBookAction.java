package edu.miu.cs.librarysystem.store.action.bookshelf;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.store.core.action.Action;

public class BookshelfSelectBookAction extends Action<Book> {
  public BookshelfSelectBookAction(Book data) {
    super(data);
  }
}
