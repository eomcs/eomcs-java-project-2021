package com.eomcs.util;

public class Queue<E> extends List<E> implements Cloneable {

  public boolean offer(E e) {
    this.add(e);
    return true;
  }

  public E poll() {
    return this.delete(0);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Queue<E> clone() throws CloneNotSupportedException {
    Queue<E> queue = new Queue<>();
    Object[] values = this.toArray();
    for (Object value : values) {
      queue.offer((E)value);
    }
    return queue;
  }

  @Override
  public Iterator<E> iterator() throws CloneNotSupportedException {
    Queue<E> queue = this.clone();

    return new Iterator<E>() {

      @Override
      public boolean hasNext() {
        return queue.size() > 0;
      }

      @Override
      public E next() {
        return queue.poll();
      }
    };
  }


}
