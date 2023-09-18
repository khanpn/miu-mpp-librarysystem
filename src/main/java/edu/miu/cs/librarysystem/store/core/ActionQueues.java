package edu.miu.cs.librarysystem.store.core;

import edu.miu.cs.librarysystem.store.core.action.Action;
import java.util.Optional;
import java.util.concurrent.SynchronousQueue;

public class ActionQueues {
  private static final SynchronousQueue<Action<?>> ACTION_QUEUE = new SynchronousQueue<>();

  static void put(Action<?> action) {
    try {
      ACTION_QUEUE.put(action);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  static Optional<Action<?>> poll() {
    return Optional.ofNullable(ACTION_QUEUE.poll());
  }
}
