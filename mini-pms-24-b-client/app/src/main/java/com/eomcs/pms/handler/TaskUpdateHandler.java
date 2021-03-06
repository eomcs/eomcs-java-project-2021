package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskUpdateHandler implements Command {

  TaskDao taskDao;
  ProjectDao projectDao;
  MemberValidator memberValidator;

  public TaskUpdateHandler(TaskDao taskDao, ProjectDao projectDao, MemberValidator memberValidator) {
    this.taskDao = taskDao;
    this.projectDao = projectDao;
    this.memberValidator = memberValidator;
  }


  @Override
  public void service() throws Exception {
    System.out.println("[작업 변경]");

    int no = Prompt.inputInt("번호? ");

    Task oldTask = taskDao.findByNo(no);
    if (oldTask == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }

    System.out.printf("현재 프로젝트: %s\n", oldTask.getProjectTitle());

    List<Project> projects = projectDao.findByKeyword(null, null);
    System.out.println("프로젝트들:");
    if (projects.size() == 0) {
      System.out.println("현재 등록된 프로젝트가 없습니다!");
      return;
    }
    for (Project p : projects) {
      System.out.printf("  %d, %s\n", p.getNo(), p.getTitle());
    }

    // 현재 작업이 소속된 프로젝트를 변경한다.
    int selectedProjectNo = 0;
    loop: while (true) {
      try {
        selectedProjectNo = Prompt.inputInt("변경할 프로젝트 번호?(취소: 0) ");
        if (selectedProjectNo == 0) {
          System.out.println("기존 프로젝트를 유지합니다.");
          break loop;
        }
        for (Project p : projects) {
          if (p.getNo() == selectedProjectNo) {
            break loop;
          }
        }
        System.out.println("유효하지 않은 프로젝트 번호 입니다.");

      } catch (Exception e) {
        System.out.println("숫자를 입력하세요!");
      }
    }

    Task task = new Task();
    task.setNo(no);

    if (selectedProjectNo != 0) {
      task.setProjectNo(selectedProjectNo);
    }

    // 사용자에게서 변경할 데이터를 입력 받는다.
    task.setContent(Prompt.inputString(String.format("내용(%s)? ", oldTask.getContent())));
    task.setDeadline(Prompt.inputDate(String.format("마감일(%s)? ", oldTask.getDeadline())));
    task.setStatus(Prompt.inputInt(String.format(
        "상태(%s)?\n0: 신규\n1: 진행중\n2: 완료\n> ",
        Task.getStatusLabel(oldTask.getStatus()))));
    task.setOwner(memberValidator.inputMember(
        String.format("담당자(%s)?(취소: 빈 문자열) ", oldTask.getOwner().getName())));

    if(task.getOwner() == null) {
      System.out.println("작업 변경을 취소합니다.");
      return;
    }

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("작업 변경을 취소하였습니다.");
      return;
    }

    // DBMS에게 게시글 변경을 요청한다.
    taskDao.update(task);

    System.out.println("작업을 변경하였습니다.");
  }
}
