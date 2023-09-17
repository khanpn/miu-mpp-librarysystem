package edu.miu.cs.librarysystem.store.core;

import edu.miu.cs.librarysystem.store.core.state.State;
import edu.miu.cs.librarysystem.store.core.state.StatePath;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public final class Store {
  private static final Store INSTANCE = new Store();
  private final Map<StatePath, State<?>> appStateMap = new HashMap<>();
  private final Map<StatePath, Map<Object, StateChangeListener>> appStateChangeListenerMap =
      new HashMap<>();

  private Store() {}

  public static State<?> getState(StatePath statePath) {
    return INSTANCE.appStateMap.get(statePath);
  }

  @SuppressWarnings("unchecked")
  public static <E extends State<T>, T> E getState(
      StatePath statePath, Class<? extends State<T>> type) {
    return (E) type.cast(getState(statePath));
  }

  public static void registerOnStateChange(StatePath statePath, StateChangeListener listener) {
    Map<Object, StateChangeListener> listenerMap =
        INSTANCE.appStateChangeListenerMap.computeIfAbsent(statePath, k -> new HashMap<>());
    listenerMap.put(listener, listener);
  }

  public static void unregisterOnStateChange(StatePath statePath, StateChangeListener listener) {
    Map<Object, StateChangeListener> listenerMap =
        INSTANCE.appStateChangeListenerMap.computeIfAbsent(statePath, k -> new HashMap<>());
    listenerMap.remove(listener);
  }

  @SuppressWarnings("unchecked")
  static <T extends State<?>> void setState(T state) {
    StatePath statePath = state.getPath();
    Map<Object, StateChangeListener> listenerMap =
        INSTANCE.appStateChangeListenerMap.get(statePath);
    if (listenerMap != null) {
      State<?> oldState = getState(state.getPath());
      for (StateChangeListener<?> listener : listenerMap.values()) {
        listener.onStateChanged(new StateChangeEvent(state, oldState));
      }
    }
    INSTANCE.appStateMap.put(statePath, state);
  }
}
