package edu.miu.cs.librarysystem.store;

import edu.miu.cs.librarysystem.store.core.StoreModule;
import edu.miu.cs.librarysystem.store.core.reducer.Reducer;
import edu.miu.cs.librarysystem.store.reducer.*;
import java.util.HashSet;
import java.util.Set;

public class AppStoreModule {

  @SuppressWarnings("rawtypes")
  private static final Set<Reducer> ACTIVE_REDUCERS = new HashSet<>();

  static {
    ACTIVE_REDUCERS.add(new LoginReducer());
    ACTIVE_REDUCERS.add(new BookshelfReducer());
    ACTIVE_REDUCERS.add(new LibraryMemberReducer());
    ACTIVE_REDUCERS.add(new CheckoutBookReducer());
    ACTIVE_REDUCERS.add(new CheckoutRecordReducer());
    ACTIVE_REDUCERS.add(new SearchOverdueBookReducer());
    ACTIVE_REDUCERS.add(new MainWindowReducer());
  }

  public static void initialize() {
    StoreModule.initialize(ACTIVE_REDUCERS);
  }
}
