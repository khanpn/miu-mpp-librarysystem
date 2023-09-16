package edu.miu.cs.librarysystem.store;

import edu.miu.cs.librarysystem.store.state.AppState;
import edu.miu.cs.librarysystem.store.state.AppStatePath;

public interface AppStateChangeListener<T extends AppState<?>> {
  void onStateChanged(AppStateChangeEvent<T> event);

  AppStatePath getListeningStatePath();
}
