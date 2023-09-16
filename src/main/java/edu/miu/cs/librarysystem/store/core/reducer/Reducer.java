package edu.miu.cs.librarysystem.store.core.reducer;

import edu.miu.cs.librarysystem.store.core.action.Action;
import edu.miu.cs.librarysystem.store.core.state.State;

public interface Reducer<T extends State<?>, A extends Action<?>> {
  T reduce(A action);

  <O extends Action<?>> boolean canReduce(O action);
}
