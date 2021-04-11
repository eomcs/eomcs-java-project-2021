package com.eomcs.pms.handler;

import java.sql.Date;
import java.util.List;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectUpdateHandler extends AbstractProjectHandler {

  private MemberValidatorHandler memberValidatorHandler;

  public ProjectUpdateHandler(List<Project> projectList, MemberValidatorHandler memberValidatorHandler) {
    super(projectList);
    this.memberValidatorHandler = memberValidatorHandler;
  }

  @Override
  public void service() {
    System.out.println("[프로젝트 변경]");

    int no = Prompt.inputInt("번호? ");

    Project project = findByNo(no);
    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    String title = Prompt.inputString(String.format("프로젝트명(%s)? ", project.getTitle()));
    String content = Prompt.inputString(String.format("내용(%s)? ", project.getContent()));
    Date startDate = Prompt.inputDate(String.format("시작일(%s)? ", project.getStartDate()));
    Date endDate = Prompt.inputDate(String.format("종료일(%s)? ", project.getEndDate()));

    String owner = memberValidatorHandler.inputMember(String.format("만든이(%s)?(취소: 빈 문자열) ", project.getOwner()));
    if (owner == null) {
      System.out.println("프로젝트 변경을 취소합니다.");
      return;
    }

    String members = memberValidatorHandler.inputMembers(
        String.format("팀원(%s)?(완료: 빈 문자열) ", project.getMembers()));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      project.setTitle(title);
      project.setContent(content);
      project.setStartDate(startDate);
      project.setEndDate(endDate);
      project.setOwner(owner);
      project.setMembers(members);

      System.out.println("프로젝트을 변경하였습니다.");

    } else {
      System.out.println("프로젝트 변경을 취소하였습니다.");
    }
  }

}








