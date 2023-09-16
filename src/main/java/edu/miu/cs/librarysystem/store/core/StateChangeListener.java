package edu.miu.cs.librarysystem.store.core;

import edu.miu.cs.librarysystem.store.core.state.State;
import edu.miu.cs.librarysystem.store.core.state.StatePath;

public interface StateChangeListener<T extends State<?>> {
  void onStateChanged(StateChangeEvent<T> event);

  StatePath getListeningStatePath();
}
