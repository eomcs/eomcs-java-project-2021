package com.eomcs.pms.handler;

import java.util.Arrays;
import com.eomcs.pms.domain.Task;

public class TaskList {
  static final int LENGTH = 100;
  Task[] tasks = new Task[LENGTH];
  int size = 0;

  void add(Task t) {
    if (this.size == this.tasks.length) {
      tasks = Arrays.copyOf(this.tasks, this.size + (this.size >> 1));
    }
    this.tasks[this.size++] = t;
  }

  Task[] toArray() {
    Task[] arr = new Task[this.size];
    for (int i = 0; i < this.size; i++) {
      arr[i] = this.tasks[i];
    }
    return arr;
  }

  Task get(int taskNo) {
    int index = indexOf(taskNo);

    if (index == -1) 
      return null;

    return this.tasks[index];
  }

  void delete(int taskNo) {
    int index = indexOf(taskNo);

    if (index == -1)
      return;

    // 배열에서 뒷 번호의 게시글을 한 칸씩 앞으로 당긴다. 
    for (int x = index + 1; x < this.size; x++) {
      this.tasks[x-1] = this.tasks[x];
    }
    this.tasks[--this.size] = null;
  }

  int indexOf(int taskNo) {
    for (int i = 0; i < this.size; i++) {
      Task task = this.tasks[i];
      if (task.no == taskNo) {
        return i;
      }
    }
    return -1;
  }
}
