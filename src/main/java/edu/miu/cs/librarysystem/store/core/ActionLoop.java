package edu.miu.cs.librarysystem.store.core;

import java.util.concurrent.atomic.AtomicBoolean;

public class ActionLoop {
  private final AtomicBoolean alive = new AtomicBoolean();

  public void start() {
    alive.set(true);
    while (alive.get()) {
      ActionQueues.get().ifPresent(Reducers::reduce);
    }
  }

  public void stop() {
    alive.set(false);
  }
}
