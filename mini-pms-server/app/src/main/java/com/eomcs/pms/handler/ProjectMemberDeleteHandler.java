package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/project/memberDelete")
public class ProjectMemberDeleteHandler implements Command {

  ProjectService projectService;

  public ProjectMemberDeleteHandler(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[프로젝트 멤버 삭제]");

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

    String input = prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      out.println("프로젝트의 멤버 삭제를 취소하였습니다.");
      return;
    }

    // 프로젝트의 기존 멤버를 모두 삭제한다.
    projectService.deleteMembers(no);

    out.println("프로젝트 멤버를 삭제하였습니다.");
  }
}






