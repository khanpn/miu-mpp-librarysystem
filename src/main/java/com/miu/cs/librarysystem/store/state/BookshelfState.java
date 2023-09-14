package com.miu.cs.librarysystem.store.state;

import com.miu.cs.librarysystem.business.BookshelfViewModel;

public class BookshelfState extends AppState<BookshelfViewModel> {
  public BookshelfState(BookshelfViewModel data) {
    super(AppStatePath.BOOKSHELF, data);
  }
}
