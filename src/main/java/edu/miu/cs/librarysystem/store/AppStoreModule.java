package edu.miu.cs.librarysystem.store;

import edu.miu.cs.librarysystem.store.core.StoreModule;
import edu.miu.cs.librarysystem.store.core.reducer.Reducer;
import edu.miu.cs.librarysystem.store.reducer.BookshelfReducer;
import edu.miu.cs.librarysystem.store.reducer.LibraryMemberReducer;
import edu.miu.cs.librarysystem.store.reducer.LoginReducer;
import java.util.HashSet;
import java.util.Set;

public class AppStoreModule {

  private static final Set<Reducer> ACTIVE_REDUCERS = new HashSet<>();

  static {
    ACTIVE_REDUCERS.add(new LoginReducer());
    ACTIVE_REDUCERS.add(new BookshelfReducer());
    ACTIVE_REDUCERS.add(new LibraryMemberReducer());
  }

  public static void initialize() {
    StoreModule.initialize(ACTIVE_REDUCERS);
  }
}
