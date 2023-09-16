package edu.miu.cs.librarysystem.store;

import edu.miu.cs.librarysystem.store.action.AppAction;
import edu.miu.cs.librarysystem.store.reducer.Reducer;

public final class AppReducers {
  private AppReducers() {}

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <A extends AppAction<?>> void reduce(A action) {
    for (Reducer reducer : AppStoreModule.getActiveReducers()) {
      if (reducer.canReduce(action)) {
        AppStore.setState(reducer.reduce(action));
      }
    }
  }
}
