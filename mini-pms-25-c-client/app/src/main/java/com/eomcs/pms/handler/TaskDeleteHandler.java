package com.eomcs.pms.handler;

import com.eomcs.pms.service.TaskService;
import com.eomcs.util.Prompt;

public class TaskDeleteHandler implements Command {

  TaskService taskService;

  public TaskDeleteHandler(TaskService taskService) {
    this.taskService = taskService;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[작업 삭제]");

    int no = Prompt.inputInt("번호? ");

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("작업 삭제를 취소하였습니다.");
      return;
    }

    if (taskService.delete(no) == 0) {
      System.out.println("해당 번호의 작업이 없습니다.");
    } else {
      System.out.println("작업을 삭제하였습니다.");
    }
  }
}
