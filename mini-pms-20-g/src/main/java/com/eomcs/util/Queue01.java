package com.eomcs.util;

public class Queue01 extends List implements Cloneable {

  public boolean offer(Object e) {
    this.add(e);
    return true;
  }

  public Object poll() {
    return this.delete(0);
  }

  @Override
  public Queue01 clone() throws CloneNotSupportedException {
    Queue01 queue = new Queue01();
    Object[] values = this.toArray();
    for (Object value : values) {
      queue.offer(value);
    }
    return queue;
  }

  @Override
  public Iterator iterator() throws CloneNotSupportedException {
    class QueueIterator implements Iterator {
      Queue01 queue;

      public QueueIterator() throws CloneNotSupportedException {
        this.queue = Queue01.this.clone();
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

    return new QueueIterator();
  }


}
