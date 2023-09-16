package edu.miu.cs.librarysystem.store.action.bookshelf;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.store.action.AppAction;

public class BookshelfSelectBookAction extends AppAction<Book> {
  public BookshelfSelectBookAction(Book data) {
    super(data);
  }
}
