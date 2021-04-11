package com.eomcs.util;

public class StackIterator extends AbstractIterator {

  // 스택에서 데이터를 꺼내려면 스택 객체를 알아야 한다.
  Stack stack;

  public StackIterator(Stack stack) throws CloneNotSupportedException {
    // 원본 스택의 값을 변경하지 않기 위해 복제해서 사용한다.
    this.stack = stack.clone();
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
