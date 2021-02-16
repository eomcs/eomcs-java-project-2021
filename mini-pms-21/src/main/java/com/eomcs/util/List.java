package com.eomcs.util;

import java.lang.reflect.Array;

public class List<E> {

  private Node<E> first;
  private Node<E> last;
  protected int size = 0;  

  public void add(E obj) {
    Node<E> node = new Node<>(obj);

    if (last == null) { // 연결 리스트의 첫 항목이라면,
      last = node;
      first = node;
    } else { // 연결리스트에 이미 항목이 있다면, 
      last.next = node; // 현재 마지막 상자의 다음 상자가 새 상자를 가리키게 한다.
      node.prev = last; // 새 상자에서 이전 상자로서 현재 마지막 상자를 가리키게 한다. 
      last = node; // 새 상자가 마지막 상자가 되게 한다.
    }

    size++;
  }

  public Object[] toArray() {
    Object[] arr = new Object[size];

    Node<E> cursor = this.first;
    int i = 0;

    while (cursor != null) {
      arr[i++] = cursor.obj;
      cursor = cursor.next;
    }
    return arr;
  }

  // 제네릭에서 지정한 타입의 배열을 만들어 리턴한다.
  // @SuppressWarning 
  // - 컴파일러가 타입이 맞는지 확인할 수 없는 경우 경고를 띄우는 데
  //   `그 경고를 띄우지 말라고 지정하고 싶다면`
  //   다음 애노테이션을 붙인다.
  @SuppressWarnings("unchecked")  
  public E[] toArray(E[] arr) {

    if (arr.length < size) {
      // 파라미터로 받은 배열이 현재 저장된 항목의 크기 보다 작을 경우
      // 새 배열을 만든다.
      arr = (E[]) Array.newInstance(arr.getClass().getComponentType(), size);
    }

    Node<E> cursor = this.first;
    for (int i = 0; i < size; i++) {
      arr[i] = cursor.obj;
      cursor = cursor.next;
    }
    return arr;
  }

  public E get(int index) {
    if (index < 0 || index >= this.size) {
      return null;
    }

    int count = 0;
    Node<E> cursor = first;
    while (cursor != null) {
      if (index == count++) {
        return cursor.obj;
      }
      cursor = cursor.next;
    }
    return null;
  }

  public boolean delete(E obj) {
    Node<E> cursor = first;
    while (cursor != null) {
      if (cursor.obj.equals(obj)) {
        this.size--;
        if (first == last) {
          first = last = null;
          return true;
        }
        if (cursor == first) {
          first = cursor.next;
          cursor.prev = null;
        } else {
          cursor.prev.next = cursor.next;
          if (cursor.next != null) {
            cursor.next.prev = cursor.prev;
          }
        }
        if (cursor == last) {
          last = cursor.prev;
        }
        return true;
      }
      cursor = cursor.next;
    }
    return false;
  }

  public E delete(int index) {
    if (index < 0 || index >= this.size) {
      return null;
    }

    E deleted = null;
    int count = 0;
    Node<E> cursor = first;
    while (cursor != null) {
      if (index == count++) {
        deleted = cursor.obj; // 삭제될 항목을 보관해 둔다.
        this.size--;
        if (first == last) {
          first = last = null;
          break;
        }
        if (cursor == first) {
          first = cursor.next;
          cursor.prev = null;
        } else {
          cursor.prev.next = cursor.next;
          if (cursor.next != null) {
            cursor.next.prev = cursor.prev;
          }
        }
        if (cursor == last) {
          last = cursor.prev;
        }
        break;
      }
      cursor = cursor.next;
    }
    return deleted;
  }

  public int indexOf(E obj) {
    int index = 0;
    Node<E> cursor = first;

    while (cursor != null) {
      if (cursor.obj == obj) {
        return index;
      }
      cursor = cursor.next;
      index++;
    }
    return -1;
  }

  public int size() {
    return this.size;
  }

  private static class Node<T> {
    T obj;
    Node<T> next;
    Node<T> prev;

    Node(T obj) {
      this.obj = obj;
    }
  }

  public Iterator<E> iterator() throws CloneNotSupportedException {
    return new Iterator<E>() {
      int cursor = 0;

      @Override
      public boolean hasNext() {
        return cursor < List.this.size();
      }

      @Override
      public E next() {
        return List.this.get(cursor++);
      }
    };
  }



}
