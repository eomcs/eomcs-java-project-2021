package com.eomcs.pms.handler;

import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/project/memberUpdate")
public class ProjectMemberUpdateHandler implements Command {

  ProjectService projectService;
  MemberValidator memberValidator;

  public ProjectMemberUpdateHandler(ProjectService projectService, MemberValidator memberValidator) {
    this.projectService = projectService;
    this.memberValidator = memberValidator;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[프로젝트 멤버 변경]");

    int no = prompt.inputInt("프로젝트 번호? ");

    Project project = projectService.get(no);

    if (project == null) {
      out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    out.printf("프로젝트 명: %s\n", project.getTitle());
    out.println("멤버:");
    for (Member m : project.getMembers()) {
      out.printf("  %s(%d)\n", m.getName(), m.getNo());
    }
    out.println("---------------------------");

    // 프로젝트 팀원 정보를 입력 받는다.
    out.println("프로젝트의 멤버를 새로 등록하세요.");
    List<Member> members = memberValidator.inputMembers("팀원?(완료: 빈 문자열) ", request, response);

    String input = prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      out.println("프로젝트의 멤버 변경을 취소하였습니다.");
      return;
    }

    // 프로젝트의 멤버를 변경한다.
    projectService.updateMembers(no, members);

    out.println("프로젝트 멤버를 변경하였습니다.");
  }
}






