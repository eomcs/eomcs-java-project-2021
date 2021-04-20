package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/project/delete")
public class ProjectDeleteHandler implements Command {

  ProjectService projectService;

  public ProjectDeleteHandler(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[프로젝트 삭제]");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      out.println("로그인 하지 않았습니다!");
      return;
    }

    int no = prompt.inputInt("번호? ");

    Project oldProject = projectService.get(no);

    if (oldProject == null) {
      out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    if (oldProject.getOwner().getNo() != loginUser.getNo()) {
      out.println("삭제 권한이 없습니다!");
      return;
    }

    String input = prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      out.println("프로젝트 삭제를 취소하였습니다.");
      return;
    }

    projectService.delete(no);
    out.println("프로젝트를 삭제하였습니다.");
  }
}








