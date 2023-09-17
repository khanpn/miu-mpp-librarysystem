package edu.miu.cs.librarysystem.store.core;

import edu.miu.cs.librarysystem.store.core.action.Action;
import edu.miu.cs.librarysystem.store.core.reducer.Reducer;

public final class Reducers {
  private Reducers() {}

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <A extends Action<?>> void reduce(A action) {
    for (Reducer reducer : StoreModule.getActiveReducers()) {
      if (reducer.canReduce(action)) {
        Store.setState(reducer.reduce(action));
      }
    }
  }
}
