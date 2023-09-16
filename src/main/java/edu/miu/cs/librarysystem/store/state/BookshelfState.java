package edu.miu.cs.librarysystem.store.state;

import edu.miu.cs.librarysystem.store.core.state.State;
import edu.miu.cs.librarysystem.viewmodel.BookshelfViewModel;

public class BookshelfState extends State<BookshelfViewModel> {
  public BookshelfState(BookshelfViewModel data) {
    super(AppStatePath.BOOKSHELF, data);
  }
}
