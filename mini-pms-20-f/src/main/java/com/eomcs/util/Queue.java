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
    Queue clone = this.clone();
    return clone.new QueueIterator();
  }

  private class QueueIterator implements Iterator {
    // 내장 필드 Queue.this 에는 iterator() 메서드에서 생성한 Queue 복제판이 들어 있다.

    @Override
    public boolean hasNext() {
      return Queue.this.size() > 0;
    }

    @Override
    public Object next() {
      return Queue.this.poll();
    }
  }
}
