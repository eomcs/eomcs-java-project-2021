package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.Prompt;

@Component(value="/project/memberDelete")
public class ProjectMemberDeleteHandler implements Command {

  ProjectService projectService;

  public ProjectMemberDeleteHandler(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 멤버 삭제]");

    int no = Prompt.inputInt("프로젝트 번호? ");

    Project project = projectService.get(no);

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
    projectService.deleteMembers(no);

    System.out.println("프로젝트 멤버를 삭제하였습니다.");
  }
}






