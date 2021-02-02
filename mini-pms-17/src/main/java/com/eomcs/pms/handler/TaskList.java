package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Task;

public class TaskList {

  private Node first;
  private Node last;
  private int size = 0;  

  public void add(Task t) {
    Node node = new Node(t);

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

  public Task[] toArray() {
    Task[] arr = new Task[size];

    Node cursor = this.first;
    int i = 0;

    while (cursor != null) {
      arr[i++] = cursor.task;
      cursor = cursor.next;
    }
    return arr;
  }

  public Task get(int taskNo) {
    Node cursor = first;
    while (cursor != null) {
      Task t = cursor.task;
      if (t.getNo() == taskNo) {
        return t;
      }
      cursor = cursor.next;
    }
    return null;
  }

  public void delete(int taskNo) {
    Node cursor = first;
    while (cursor != null) {
      if (cursor.task.getNo() == taskNo) {
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
  }

  static class Node {
    Task task;
    Node next;
    Node prev;

    Node(Task t) {
      this.task = t;
    }
  }
}
