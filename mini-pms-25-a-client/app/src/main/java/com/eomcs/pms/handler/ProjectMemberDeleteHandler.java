package com.eomcs.pms.handler;

import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectMemberDeleteHandler implements Command {

  ProjectDao projectDao;

  public ProjectMemberDeleteHandler(ProjectDao projectDao) {
    this.projectDao = projectDao;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 멤버 삭제]");

    int no = Prompt.inputInt("프로젝트 번호? ");

    Project project = projectDao.findByNo(no);

    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    System.out.printf("프로젝트 명: %s\n", project.getTitle());
    System.out.println("멤버:");
    for (Member m : project.getMembers()) {
      System.out.printf("  %s(%d)\n", m.getName(), m.getNo());
    }
    System.out.println();

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("프로젝트의 멤버 삭제를 취소하였습니다.");
      return;
    }

    // 프로젝트의 기존 멤버를 모두 삭제한다.
    projectDao.deleteMembers(no);

    System.out.println("프로젝트 멤버를 삭제하였습니다.");
  }
}






