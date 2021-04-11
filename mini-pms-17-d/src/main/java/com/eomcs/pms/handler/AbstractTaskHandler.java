package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Task;

public abstract class AbstractTaskHandler implements Command {

  protected List<Task> taskList;

  public AbstractTaskHandler(List<Task> taskList) {
    this.taskList = taskList;
  }

  protected String getStatusLabel(int status) {
    switch (status) {
      case 1:
        return "진행중";
      case 2:
        return "완료";
      default:
        return "신규";
    }
  }

  protected Task findByNo(int taskNo) {
    Task[] list = taskList.toArray(new Task[taskList.size()]);
    for (Task t : list) {
      if (t.getNo() == taskNo) {
        return t;
      }
    }
    return null;
  }
}
