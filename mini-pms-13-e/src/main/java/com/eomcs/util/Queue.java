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

  @Override
  public Iterator iterator() throws CloneNotSupportedException {
    return new QueueIterator(this);
  }

  private static class QueueIterator implements Iterator {
    Queue queue;

    public QueueIterator(Queue queue) throws CloneNotSupportedException {
      this.queue = queue.clone();
    }

    @Override
    public boolean hasNext() {
      return this.queue.size() > 0;
    }

    @Override
    public Object next() {
      return this.queue.poll();
    }

  }
}
