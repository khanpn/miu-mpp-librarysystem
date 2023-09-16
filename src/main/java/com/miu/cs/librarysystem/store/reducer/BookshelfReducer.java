package com.miu.cs.librarysystem.store.reducer;

import com.miu.cs.librarysystem.business.Book;
import com.miu.cs.librarysystem.service.BookService;
import com.miu.cs.librarysystem.store.AppStore;
import com.miu.cs.librarysystem.store.action.AppAction;
import com.miu.cs.librarysystem.store.action.bookshelf.*;
import com.miu.cs.librarysystem.store.state.AppStatePath;
import com.miu.cs.librarysystem.store.state.BookshelfState;
import com.miu.cs.librarysystem.viewmodel.BookshelfViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookshelfReducer implements Reducer<BookshelfState, AppAction<?>> {
  @Override
  public BookshelfState reduce(AppAction<?> action) {
    if (action instanceof BookshelfSelectBookAction selectBookAction) {
      Book selectedBook = selectBookAction.getData();
      BookshelfState currentState = AppStore.getState(AppStatePath.BOOKSHELF, BookshelfState.class);
      BookshelfViewModel currentViewModel = currentState.getData();
      return new BookshelfState(new BookshelfViewModel(currentViewModel.getBooks(), selectedBook));
    } else if (action instanceof BookshelfLoadBooksAction) {
      List<Book> books = loadBook();
      return new BookshelfState(new BookshelfViewModel(books));
    } else if (action instanceof BookshelfFilterAction filterAction) {
      List<Book> books =
          loadBook().stream()
              .filter(book -> book.getIsbn().contains(filterAction.getData()))
              .toList();
      return new BookshelfState(new BookshelfViewModel(books));
    } else if (action instanceof BookshelfAddBookAction addBookAction) {
      BookService.getInstance().save(addBookAction.getData());
      return new BookshelfState(new BookshelfViewModel(loadBook()));
    } else if (action instanceof BookshelfUpdateBookAction updateBookAction) {
      BookService.getInstance().save(updateBookAction.getData());
      return new BookshelfState(new BookshelfViewModel(loadBook()));
    }
    throw new IllegalArgumentException("Unexpected action: " + action.getClass());
  }

  private List<Book> loadBook() {
    Collection<Book> bookCollection = BookService.getInstance().getAllBooks();
    return new ArrayList<>(bookCollection);
  }

  @Override
  public <O extends AppAction<?>> boolean canReduce(O action) {
    return action instanceof BookshelfLoadBooksAction
        || action instanceof BookshelfSelectBookAction
        || action instanceof BookshelfFilterAction
        || action instanceof BookshelfAddBookAction
        || action instanceof BookshelfUpdateBookAction;
  }
}
