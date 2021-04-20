package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/project/add")
public class ProjectAddHandler implements Command {

  ProjectService projectService;
  MemberValidator memberValidator;

  public ProjectAddHandler(ProjectService projectService, MemberValidator memberValidator) {
    this.projectService = projectService;
    this.memberValidator = memberValidator;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[프로젝트 등록]");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      out.println("로그인 하지 않았습니다!");
      return;
    }

    Project p = new Project();
    p.setTitle(prompt.inputString("프로젝트명? "));
    p.setContent(prompt.inputString("내용? "));
    p.setStartDate(prompt.inputDate("시작일? "));
    p.setEndDate(prompt.inputDate("종료일? "));
    p.setOwner(loginUser);
    p.setMembers(memberValidator.inputMembers("팀원?(완료: 빈 문자열) ", request, response));

    projectService.add(p);

    out.println("프로젝트를 등록했습니다.");
  }
}








