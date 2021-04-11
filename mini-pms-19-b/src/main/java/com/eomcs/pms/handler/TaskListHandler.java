package com.eomcs.pms.handler;

import java.util.Iterator;
import java.util.List;
import com.eomcs.pms.domain.Task;

public class TaskListHandler extends AbstractTaskHandler {

  public TaskListHandler(List<Task> taskList) {
    super(taskList);
  }

  @Override
  public void service() {
    System.out.println("[작업 목록]");

    Iterator<Task> iterator = taskList.iterator();

    while (iterator.hasNext()) {
      Task t = iterator.next();
      System.out.printf("%d, %s, %s, %s, %s\n", 
          t.getNo(), t.getContent(), t.getDeadline(), getStatusLabel(t.getStatus()), t.getOwner());
    }
  }
}
