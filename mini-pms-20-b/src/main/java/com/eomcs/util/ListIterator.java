package com.eomcs.util;

public class ListIterator extends AbstractIterator {

  // 리스트에서 데이터를 꺼내려면 리스트 객체를 알아야 한다.
  List list;
  int cursor = 0;

  public ListIterator(List list) {
    // 리스트는 복제할 필요가 없다.
    this.list = list;
  }

  @Override
  public boolean hasNext() {
    return cursor < list.size();
  }

  @Override
  public Object next() {
    return list.get(cursor++);

    // 위의 문장은 컴파일하면 다음 문장으로 바뀐다.
    //    int temp = cursor;
    //    cursor = cursor + 1;
    //    return list.get(temp);
  }

}
