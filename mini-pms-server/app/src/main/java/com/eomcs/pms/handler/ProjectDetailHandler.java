package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.Prompt;

@Component(value="/project/detail")
public class ProjectDetailHandler implements Command {

  ProjectService projectService;

  public ProjectDetailHandler(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 상세보기]");

    int no = Prompt.inputInt("번호? ");

    Project project = projectService.get(no);

    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    System.out.printf("프로젝트명: %s\n", project.getTitle());
    System.out.printf("내용: %s\n", project.getContent());
    System.out.printf("시작일: %s\n", project.getStartDate());
    System.out.printf("종료일: %s\n", project.getEndDate());
    System.out.printf("관리자: %s\n", project.getOwner().getName());

    // 프로젝트의 팀원 목록 가져오기
    StringBuilder strBuilder = new StringBuilder();
    List<Member> members = project.getMembers();
    for (Member m : members) {
      if (strBuilder.length() > 0) {
        strBuilder.append("/");
      }
      strBuilder.append(m.getName());
    }
    System.out.printf("팀원: %s\n", strBuilder.toString());
  }
}








