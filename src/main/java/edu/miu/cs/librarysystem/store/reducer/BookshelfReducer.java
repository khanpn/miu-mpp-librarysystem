package edu.miu.cs.librarysystem.store.reducer;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.service.BookService;
import edu.miu.cs.librarysystem.store.action.bookshelf.*;
import edu.miu.cs.librarysystem.store.core.Store;
import edu.miu.cs.librarysystem.store.core.action.Action;
import edu.miu.cs.librarysystem.store.core.reducer.Reducer;
import edu.miu.cs.librarysystem.store.state.AppStatePath;
import edu.miu.cs.librarysystem.store.state.BookshelfState;
import edu.miu.cs.librarysystem.viewmodel.BookshelfViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookshelfReducer implements Reducer<BookshelfState, Action<?>> {
  @Override
  public BookshelfState reduce(Action<?> action) {
    if (action instanceof BookshelfSelectBookAction selectBookAction) {
      Book selectedBook = selectBookAction.getData();
      BookshelfState currentState = Store.getState(AppStatePath.BOOKSHELF, BookshelfState.class);
      BookshelfViewModel currentViewModel = currentState.getData();
      return new BookshelfState(new BookshelfViewModel(currentViewModel.getBooks(), selectedBook));
    } else if (action instanceof BookshelfLoadBooksAction) {
      return reloadState();
    } else if (action instanceof BookshelfFilterAction filterAction) {
      List<Book> books =
          loadBook().stream()
              .filter(book -> book.getIsbn().contains(filterAction.getData()))
              .toList();
      return new BookshelfState(new BookshelfViewModel(books));
    } else if (action instanceof BookshelfAddBookAction addBookAction) {
      BookService.getInstance().save(addBookAction.getData());
      return reloadState();
    } else if (action instanceof BookshelfUpdateBookAction updateBookAction) {
      BookService.getInstance().save(updateBookAction.getData());
      return reloadState();
    }
    return new BookshelfState(new BookshelfViewModel(new ArrayList<>()));
  }

  private BookshelfState reloadState() {
    return new BookshelfState(new BookshelfViewModel(loadBook()));
  }

  private List<Book> loadBook() {
    Collection<Book> bookCollection = BookService.getInstance().getAllBooks();
    return new ArrayList<>(bookCollection);
  }

  @Override
  public <O extends Action<?>> boolean canReduce(O action) {
    return action instanceof BookshelfLoadBooksAction
        || action instanceof BookshelfSelectBookAction
        || action instanceof BookshelfFilterAction
        || action instanceof BookshelfAddBookAction
        || action instanceof BookshelfUpdateBookAction;
  }
}
