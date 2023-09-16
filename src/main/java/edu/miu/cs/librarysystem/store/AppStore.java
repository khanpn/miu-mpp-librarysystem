package edu.miu.cs.librarysystem.store;

import edu.miu.cs.librarysystem.store.state.AppState;
import edu.miu.cs.librarysystem.store.state.AppStatePath;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public final class AppStore {
  private static final AppStore INSTANCE = new AppStore();
  private final Map<AppStatePath, AppState<?>> appStateMap = new HashMap<>();
  private final Map<AppStatePath, Map<Object, AppStateChangeListener>> appStateChangeListenerMap =
      new HashMap<>();

  private AppStore() {}

  public static AppState<?> getState(AppStatePath statePath) {
    return INSTANCE.appStateMap.get(statePath);
  }

  @SuppressWarnings("unchecked")
  public static <E extends AppState<T>, T> E getState(
      AppStatePath statePath, Class<? extends AppState<T>> type) {
    return (E) type.cast(getState(statePath));
  }

  public static void registerOnStateChange(
      AppStatePath statePath, AppStateChangeListener listener) {
    Map<Object, AppStateChangeListener> listenerMap =
        INSTANCE.appStateChangeListenerMap.computeIfAbsent(statePath, k -> new HashMap<>());
    listenerMap.put(listener, listener);
  }

  public static void unregisterOnStateChange(
      AppStatePath statePath, AppStateChangeListener listener) {
    Map<Object, AppStateChangeListener> listenerMap =
        INSTANCE.appStateChangeListenerMap.computeIfAbsent(statePath, k -> new HashMap<>());
    listenerMap.remove(listener);
  }

  @SuppressWarnings("unchecked")
  static <T extends AppState<?>> void setState(T state) {
    AppStatePath statePath = state.getPath();
    Map<Object, AppStateChangeListener> listenerMap =
        INSTANCE.appStateChangeListenerMap.get(statePath);
    if (listenerMap != null) {
      AppState<?> oldState = getState(state.getPath());
      for (AppStateChangeListener<?> listener : listenerMap.values()) {
        listener.onStateChanged(new AppStateChangeEvent(state, oldState));
      }
    }
    INSTANCE.appStateMap.put(statePath, state);
  }
}
