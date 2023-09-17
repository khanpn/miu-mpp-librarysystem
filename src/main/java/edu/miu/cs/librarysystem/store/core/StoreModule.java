package edu.miu.cs.librarysystem.store.core;

import edu.miu.cs.librarysystem.store.core.reducer.Reducer;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

@SuppressWarnings("rawtypes")
public final class StoreModule {
  private static final Set<Reducer> ACTIVE_REDUCERS = new HashSet<>();

  public static Set<Reducer> getActiveReducers() {
    return ACTIVE_REDUCERS;
  }

  public static void initialize(Set<Reducer> reducers) {
    Executors.newSingleThreadExecutor().execute(() -> new ActionLoop().start());
    if (reducers == null) {
      return;
    }
    ACTIVE_REDUCERS.addAll(reducers);
  }
}
