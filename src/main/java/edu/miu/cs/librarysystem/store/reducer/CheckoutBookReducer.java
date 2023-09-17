package edu.miu.cs.librarysystem.store.reducer;

import edu.miu.cs.librarysystem.business.Book;
import edu.miu.cs.librarysystem.business.CheckoutHistory;
import edu.miu.cs.librarysystem.business.LibraryMember;
import edu.miu.cs.librarysystem.exception.LibrarySystemException;
import edu.miu.cs.librarysystem.service.BookService;
import edu.miu.cs.librarysystem.service.LibraryMemberService;
import edu.miu.cs.librarysystem.store.action.checkoutbook.CheckoutBookCheckoutBookAction;
import edu.miu.cs.librarysystem.store.action.checkoutbook.CheckoutBookRefreshAction;
import edu.miu.cs.librarysystem.store.core.action.Action;
import edu.miu.cs.librarysystem.store.core.reducer.Reducer;
import edu.miu.cs.librarysystem.store.state.CheckoutBookState;
import edu.miu.cs.librarysystem.viewmodel.CheckoutBookViewModel;
import java.util.List;

public class CheckoutBookReducer implements Reducer<CheckoutBookState, Action<?>> {
  @Override
  public CheckoutBookState reduce(Action<?> action) {
    if (action instanceof CheckoutBookRefreshAction) {
      return reloadState();
    } else if (action instanceof CheckoutBookCheckoutBookAction checkoutBookAction) {
      String[] ids = checkoutBookAction.getData().split(",");
      try {
        BookService.getInstance().checkBook(ids[0], ids[1]);
        return reloadState();
      } catch (LibrarySystemException e) {
        return new CheckoutBookState(new CheckoutBookViewModel(e));
      }
    }
    return new CheckoutBookState(
        new CheckoutBookViewModel(
            new LibrarySystemException("Unexpected action: " + action.getClass())));
  }

  @Override
  public <O extends Action<?>> boolean canReduce(O action) {
    return action instanceof CheckoutBookRefreshAction
        || action instanceof CheckoutBookCheckoutBookAction;
  }

  private CheckoutBookState reloadState() {
    List<LibraryMember> allMembers = LibraryMemberService.getInstance().getAllMembers();
    List<Book> allBooks = BookService.getInstance().getAllBooks();
    List<CheckoutHistory> checkoutHistories = BookService.getInstance().getCheckoutHistory();
    return new CheckoutBookState(
        new CheckoutBookViewModel(allMembers, allBooks, checkoutHistories));
  }
}
