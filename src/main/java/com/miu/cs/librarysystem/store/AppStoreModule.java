package com.miu.cs.librarysystem.store;

import com.miu.cs.librarysystem.store.reducer.BookshelfReducer;
import com.miu.cs.librarysystem.store.reducer.LoginReducer;
import com.miu.cs.librarysystem.store.reducer.Reducer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("rawtypes")
public final class AppStoreModule {
  private static final Set<Reducer> ACTIVE_REDUCERS = new HashSet<>();

  static {
    ACTIVE_REDUCERS.add(new LoginReducer());
    ACTIVE_REDUCERS.add(new BookshelfReducer());
  }

  private AppStoreModule() {}

  public static Set<Reducer> getActiveReducers() {
    return Collections.unmodifiableSet(ACTIVE_REDUCERS);
  }
}
