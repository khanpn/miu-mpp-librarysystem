package edu.miu.cs.librarysystem.store.action.bookshelf;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.store.action.AppAction;

public class BookshelfUpdateBookAction extends AppAction<Book> {
  public BookshelfUpdateBookAction(Book data) {
    super(data);
  }
}
