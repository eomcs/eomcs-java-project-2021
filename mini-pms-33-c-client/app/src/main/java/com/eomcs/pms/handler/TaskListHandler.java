package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;
import com.eomcs.util.Prompt;

public class TaskListHandler implements Command {

  TaskService taskService;

  public TaskListHandler(TaskService taskService) {
    this.taskService = taskService;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[작업 목록]");

    String input = Prompt.inputString("프로젝트 번호?(전체: 빈 문자열 또는 0) ");

    // 1) 사용자가 입력한 문자열을 프로젝트 번호로 바꾼다.
    int projectNo = 0;
    try {
      if (input.length() != 0) {
        projectNo = Integer.parseInt(input);
      }
    }catch (Exception e) {
      System.out.println("프로젝트 번호를 입력하세요.");
      return;
    }

    // 2) 해당 프로젝트에 소속된 작업 목록을 가져온다.
    List<Task> tasks = null;
    if (projectNo == 0) {
      tasks = taskService.list();
    } else {
      tasks = taskService.listOfProject(projectNo);
    }

    if (tasks.size() == 0) {
      System.out.println("해당 번호의 프로젝트가 없거나 또는 등록된 작업이 없습니다.");
      return;
    }

    projectNo = 0;
    for (Task t : tasks) {
      if (projectNo != t.getProjectNo()) {
        System.out.printf("'%s' 작업 목록: \n", t.getProjectTitle());
        projectNo = t.getProjectNo();
      }
      System.out.printf("%d, %s, %s, %s, %s\n", 
          t.getNo(), 
          t.getContent(), 
          t.getDeadline(),
          t.getOwner().getName(),
          Task.getStatusLabel(t.getStatus()));
    }
  }
}
