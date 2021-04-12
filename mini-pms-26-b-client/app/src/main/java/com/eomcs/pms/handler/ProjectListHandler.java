package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

public class ProjectListHandler implements Command {

  ProjectService projectService;

  public ProjectListHandler(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 목록]");

    List<Project> projects = projectService.list();

    for (Project p : projects) {

      // 1) 프로젝트의 팀원 목록 가져오기
      StringBuilder strBuilder = new StringBuilder();
      List<Member> members = p.getMembers();
      for (Member m : members) {
        if (strBuilder.length() > 0) {
          strBuilder.append("/");
        }
        strBuilder.append(m.getName());
      }

      // 2) 프로젝트 정보를 출력
      System.out.printf("%d, %s, %s, %s, %s, [%s]\n", 
          p.getNo(), 
          p.getTitle(), 
          p.getStartDate(),
          p.getEndDate(),
          p.getOwner().getName(),
          strBuilder.toString());
    }
  }
}








