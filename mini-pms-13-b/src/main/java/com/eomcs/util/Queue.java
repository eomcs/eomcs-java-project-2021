package com.eomcs.util;

public class Queue extends List implements Cloneable {

  public boolean offer(Object e) {
    this.add(e);
    return true;
  }

  public Object poll() {
    return this.delete(0);
  }

  @Override
  public Queue clone() throws CloneNotSupportedException {
    Queue queue = new Queue();
    Object[] values = this.toArray();
    for (Object value : values) {
      queue.offer(value);
    }
    return queue;
  }
}
