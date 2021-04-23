package com.eomcs.pms.handler;

import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/task/update")
public class TaskUpdateHandler implements Command {

  TaskService taskService;
  ProjectService projectService;
  MemberValidator memberValidator;

  public TaskUpdateHandler(TaskService taskService, ProjectService projectService, MemberValidator memberValidator) {
    this.taskService = taskService;
    this.projectService = projectService;
    this.memberValidator = memberValidator;
  }


  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[작업 변경]");

    int no = prompt.inputInt("번호? ");

    Task oldTask = taskService.get(no);
    if (oldTask == null) {
      out.println("해당 번호의 작업이 없습니다.");
      return;
    }

    out.printf("현재 프로젝트: %s\n", oldTask.getProjectTitle());

    List<Project> projects = projectService.list();
    out.println("프로젝트들:");
    if (projects.size() == 0) {
      out.println("현재 등록된 프로젝트가 없습니다!");
      return;
    }
    for (Project p : projects) {
      out.printf("  %d, %s\n", p.getNo(), p.getTitle());
    }

    // 현재 작업이 소속된 프로젝트를 변경한다.
    int selectedProjectNo = 0;
    loop: while (true) {
      try {
        selectedProjectNo = prompt.inputInt("변경할 프로젝트 번호?(취소: 0) ");
        if (selectedProjectNo == 0) {
          out.println("기존 프로젝트를 유지합니다.");
          break loop;
        }
        for (Project p : projects) {
          if (p.getNo() == selectedProjectNo) {
            break loop;
          }
        }
        out.println("유효하지 않은 프로젝트 번호 입니다.");

      } catch (Exception e) {
        out.println("숫자를 입력하세요!");
      }
    }

    Task task = new Task();
    task.setNo(no);

    if (selectedProjectNo != 0) {
      task.setProjectNo(selectedProjectNo);
    }

    // 사용자에게서 변경할 데이터를 입력 받는다.
    task.setContent(prompt.inputString(String.format("내용(%s)? ", oldTask.getContent())));
    task.setDeadline(prompt.inputDate(String.format("마감일(%s)? ", oldTask.getDeadline())));
    task.setStatus(prompt.inputInt(String.format(
        "상태(%s)?\n0: 신규\n1: 진행중\n2: 완료\n> ",
        Task.getStatusLabel(oldTask.getStatus()))));
    task.setOwner(memberValidator.inputMember(
        String.format("담당자(%s)?(취소: 빈 문자열) ", oldTask.getOwner().getName()), request, response));

    if(task.getOwner() == null) {
      out.println("작업 변경을 취소합니다.");
      return;
    }

    String input = prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      out.println("작업 변경을 취소하였습니다.");
      return;
    }

    // DBMS에게 게시글 변경을 요청한다.
    taskService.update(task);

    out.println("작업을 변경하였습니다.");
  }
}
