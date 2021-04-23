package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.service.TaskService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/task/delete")
public class TaskDeleteHandler implements Command {

  TaskService taskService;

  public TaskDeleteHandler(TaskService taskService) {
    this.taskService = taskService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[작업 삭제]");

    int no = prompt.inputInt("번호? ");

    String input = prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      out.println("작업 삭제를 취소하였습니다.");
      return;
    }

    if (taskService.delete(no) == 0) {
      out.println("해당 번호의 작업이 없습니다.");
    } else {
      out.println("작업을 삭제하였습니다.");
    }
  }
}
