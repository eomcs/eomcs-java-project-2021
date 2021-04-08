package com.eomcs.pms.handler;

import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.util.Prompt;

public class ProjectDeleteHandler implements Command {

  ProjectDao projectDao;
  TaskDao taskDao;

  public ProjectDeleteHandler(ProjectDao projectDao, TaskDao taskDao) {
    this.projectDao = projectDao;
    this.taskDao = taskDao;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 삭제]");

    int no = Prompt.inputInt("번호? ");

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("프로젝트 삭제를 취소하였습니다.");
      return;
    }

    // 1) 프로젝트의 작업들을 모두 삭제한다.
    taskDao.deleteByProjectNo(no);

    // 2) 프로젝트를 삭제한다.
    if (projectDao.delete(no) == 0) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");

    } else {
      System.out.println("프로젝트를 삭제하였습니다.");
    }
  }
}








