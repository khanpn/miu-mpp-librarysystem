package edu.miu.cs.librarysystem.store.action.bookshelf;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.store.core.action.Action;

public class BookshelfUpdateBookAction extends Action<Book> {
  public BookshelfUpdateBookAction(Book data) {
    super(data);
  }
}
