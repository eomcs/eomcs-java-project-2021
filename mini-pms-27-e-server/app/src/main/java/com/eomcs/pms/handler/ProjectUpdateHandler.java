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

@Component("/project/update")
public class ProjectUpdateHandler implements Command {

  ProjectService projectService;
  MemberValidator memberValidator;

  public ProjectUpdateHandler(ProjectService projectService, MemberValidator memberValidator) {
    this.projectService = projectService;
    this.memberValidator = memberValidator;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[프로젝트 변경]");

    int no = prompt.inputInt("번호? ");

    Project oldProject = projectService.get(no);

    if (oldProject == null) {
      out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    // 사용자에게서 변경할 데이터를 입력 받는다.
    Project project = new Project();
    project.setNo(no);
    project.setTitle(prompt.inputString(
        String.format("프로젝트명(%s)? ", oldProject.getTitle())));
    project.setContent(prompt.inputString(
        String.format("내용(%s)? ", oldProject.getContent())));
    project.setStartDate(prompt.inputDate(
        String.format("시작일(%s)? ", oldProject.getStartDate())));
    project.setEndDate(prompt.inputDate(
        String.format("종료일(%s)? ", oldProject.getEndDate())));
    project.setOwner(memberValidator.inputMember(
        String.format("만든이(%s)?(취소: 빈 문자열) ", oldProject.getOwner().getName()), request, response));

    if (project.getOwner() == null) {
      out.println("프로젝트 변경을 취소합니다.");
      return;
    }

    // 프로젝트 팀원 정보를 입력 받는다.
    StringBuilder strBuilder = new StringBuilder();
    List<Member> members = oldProject.getMembers();
    for (Member m : members) {
      if (strBuilder.length() > 0) {
        strBuilder.append("/");
      }
      strBuilder.append(m.getName());
    }

    project.setMembers(memberValidator.inputMembers(
        String.format("팀원(%s)?(완료: 빈 문자열) ", strBuilder), request, response));

    String input = prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      out.println("프로젝트 변경을 취소하였습니다.");
      return;
    }

    // DBMS에게 프로젝트 변경을 요청한다.
    projectService.update(project);

    out.println("프로젝트을 변경하였습니다.");
  }
}






