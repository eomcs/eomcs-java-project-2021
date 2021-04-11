package com.eomcs.util;

public class Stack01 extends List implements Cloneable {

  public Object push(Object item) {
    // 수퍼 클래스 List의 메서드를 사용하여 항목을 추가한다.
    this.add(item);
    return item;
  }

  public Object pop() {
    // 수퍼 클래스 List의 메서드를 사용하여 항목을 꺼낸다.
    return this.delete(this.size - 1);
  }

  @Override
  public Stack01 clone() throws CloneNotSupportedException {

    // Stack deep copy
    // 1) 새 스택 객체를 만든다.
    Stack01 stack = new Stack01();

    // 2) 기존 스택의 값을 가져와서 새 스택에 넣는다.
    for (int i = 0; i < this.size; i++) {
      stack.push(this.get(i));
    }

    // 3) 복제한 스택을 리턴한다.
    return stack;
  }

  @Override
  public Iterator iterator() throws CloneNotSupportedException {
    class StackIterator implements Iterator {
      Stack01 stack;

      public StackIterator() throws CloneNotSupportedException {
        this.stack = Stack01.this.clone();
      }
      @Override
      public boolean hasNext() {
        return this.stack.size() > 0;
      }

      @Override
      public Object next() {
        return this.stack.pop();
      }
    }
    return new StackIterator();
  }


}
