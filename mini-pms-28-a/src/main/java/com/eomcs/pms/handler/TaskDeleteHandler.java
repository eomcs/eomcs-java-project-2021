package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskDeleteHandler extends AbstractTaskHandler {

  public TaskDeleteHandler(List<Task> taskList) {
    super(taskList);
  }

  @Override
  public void service() {
    System.out.println("[작업 삭제]");

    int no = Prompt.inputInt("번호? ");

    Task task = findByNo(no);
    if (task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      taskList.remove(task);
      System.out.println("작업을 삭제하였습니다.");

    } else {
      System.out.println("작업 삭제를 취소하였습니다.");
    }

  }
}
