package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.util.Prompt;

public class ProjectAddHandler implements Command {

  ProjectService projectService;
  MemberValidator memberValidator;

  public ProjectAddHandler(ProjectService projectService, MemberValidator memberValidator) {
    this.projectService = projectService;
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 등록]");

    Project p = new Project();
    p.setTitle(Prompt.inputString("프로젝트명? "));
    p.setContent(Prompt.inputString("내용? "));
    p.setStartDate(Prompt.inputDate("시작일? "));
    p.setEndDate(Prompt.inputDate("종료일? "));

    p.setOwner(memberValidator.inputMember("만든이?(취소: 빈 문자열) "));
    if (p.getOwner() == null) {
      System.out.println("프로젝트 입력을 취소합니다.");
      return;
    }

    p.setMembers(memberValidator.inputMembers("팀원?(완료: 빈 문자열) "));

    projectService.add(p);

    System.out.println("프로젝트를 등록했습니다.");
  }
}








