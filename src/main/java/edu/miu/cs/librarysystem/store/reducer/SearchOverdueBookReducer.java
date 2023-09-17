package edu.miu.cs.librarysystem.store.reducer;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.business.CheckoutRecordEntry;
import edu.miu.cs.librarysystem.business.LibraryMember;
import edu.miu.cs.librarysystem.service.BookService;
import edu.miu.cs.librarysystem.store.action.searchoverduebook.SearchOverdueBookClearSearchAction;
import edu.miu.cs.librarysystem.store.action.searchoverduebook.SearchOverdueBookSearchAction;
import edu.miu.cs.librarysystem.store.core.action.Action;
import edu.miu.cs.librarysystem.store.core.reducer.Reducer;
import edu.miu.cs.librarysystem.store.state.SearchOverdueBookState;
import edu.miu.cs.librarysystem.viewmodel.SearchOverdueBookViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchOverdueBookReducer implements Reducer<SearchOverdueBookState, Action<?>> {
  @Override
  public SearchOverdueBookState reduce(Action<?> action) {
    String bookId = null;
    Book book = null;
    List<Map<LibraryMember, List<CheckoutRecordEntry>>> overdueEntries = new ArrayList<>();
    if (action instanceof SearchOverdueBookSearchAction searchAction) {
      bookId = searchAction.getData();
      BookService bookService = BookService.getInstance();
      book = bookService.findById(bookId).orElse(null);
      if (book != null) {
        overdueEntries = bookService.getOverdueCheckoutRecordEntries(book);
      }
    } else if (action instanceof SearchOverdueBookClearSearchAction) {
      return new SearchOverdueBookState(new SearchOverdueBookViewModel(true));
    }
    return new SearchOverdueBookState(new SearchOverdueBookViewModel(bookId, book, overdueEntries));
  }

  @Override
  public <O extends Action<?>> boolean canReduce(O action) {
    return action instanceof SearchOverdueBookSearchAction
        || action instanceof SearchOverdueBookClearSearchAction;
  }
}
