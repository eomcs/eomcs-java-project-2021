package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/task/detail")
public class TaskDetailHandler implements Command {

  TaskService taskService;

  public TaskDetailHandler(TaskService taskService) {
    this.taskService = taskService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[작업 상세보기]");

    int no = prompt.inputInt("번호? ");

    Task task = taskService.get(no);

    if (task == null) {
      out.println("해당 번호의 작업이 없습니다.");
      return;
    }

    out.printf("프로젝트: %s\n", task.getProjectTitle());
    out.printf("내용: %s\n", task.getContent());
    out.printf("마감일: %s\n", task.getDeadline());
    out.printf("상태: %s\n", Task.getStatusLabel(task.getStatus()));
    out.printf("담당자: %s\n", task.getOwner().getName());
  }
}
