package edu.miu.cs.librarysystem.store.reducer;

import edu.miu.cs.librarysystem.store.action.AppAction;
import edu.miu.cs.librarysystem.store.state.AppState;

public interface Reducer<T extends AppState<?>, A extends AppAction<?>> {
  T reduce(A action);

  <O extends AppAction<?>> boolean canReduce(O action);
}
