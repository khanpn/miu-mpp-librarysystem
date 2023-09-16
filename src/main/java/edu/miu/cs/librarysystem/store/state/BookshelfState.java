package edu.miu.cs.librarysystem.store.state;

import edu.miu.cs.librarysystem.viewmodel.BookshelfViewModel;

public class BookshelfState extends AppState<BookshelfViewModel> {
  public BookshelfState(BookshelfViewModel data) {
    super(AppStatePath.BOOKSHELF, data);
  }
}
