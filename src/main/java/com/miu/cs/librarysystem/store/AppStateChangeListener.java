package com.miu.cs.librarysystem.store;

import com.miu.cs.librarysystem.store.state.AppState;
import com.miu.cs.librarysystem.store.state.AppStatePath;

public interface AppStateChangeListener<T extends AppState<?>> {
  void onStateChanged(AppStateChangeEvent<T> event);

  AppStatePath getListeningStatePath();
}
