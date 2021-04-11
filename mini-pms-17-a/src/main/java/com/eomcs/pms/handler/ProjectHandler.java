package com.eomcs.pms.handler;

import java.sql.Date;
import java.util.Iterator;
import java.util.LinkedList;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectHandler {

  private LinkedList<Project> projectList = new LinkedList<>();

  private MemberHandler memberHandler;

  public ProjectHandler(MemberHandler memberHandler) {
    this.memberHandler = memberHandler;
  }

  public void add() {
    System.out.println("[프로젝트 등록]");

    Project p = new Project();
    p.setNo(Prompt.inputInt("번호? "));
    p.setTitle(Prompt.inputString("프로젝트명? "));
    p.setContent(Prompt.inputString("내용? "));
    p.setStartDate(Prompt.inputDate("시작일? "));
    p.setEndDate(Prompt.inputDate("종료일? "));

    p.setOwner(memberHandler.inputMember("만든이?(취소: 빈 문자열) "));
    if (p.getOwner() == null) {
      System.out.println("프로젝트 입력을 취소합니다.");
      return;
    }

    p.setMembers(memberHandler.inputMembers("팀원?(완료: 빈 문자열) "));

    projectList.add(p);

    System.out.println("프로젝트를 등록했습니다.");
  }

  public void list() throws CloneNotSupportedException {
    System.out.println("[프로젝트 목록]");

    Iterator<Project> iterator = projectList.iterator();

    while (iterator.hasNext()) {
      Project p = iterator.next();
      System.out.printf("%d, %s, %s, %s, %s, [%s]\n",
          p.getNo(), p.getTitle(), p.getStartDate(), p.getEndDate(), p.getOwner(), p.getMembers());
    }
  }

  public void detail() {
    System.out.println("[프로젝트 상세보기]");

    int no = Prompt.inputInt("번호? ");

    Project project = findByNo(no);
    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    System.out.printf("프로젝트명: %s\n", project.getTitle());
    System.out.printf("내용: %s\n", project.getContent());
    System.out.printf("시작일: %s\n", project.getStartDate());
    System.out.printf("종료일: %s\n", project.getEndDate());
    System.out.printf("관리자: %s\n", project.getOwner());
    System.out.printf("팀원: %s\n", project.getMembers());

  }

  public void update() {
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

    String owner = memberHandler.inputMember(String.format("만든이(%s)?(취소: 빈 문자열) ", project.getOwner()));
    if (owner == null) {
      System.out.println("프로젝트 변경을 취소합니다.");
      return;
    }

    String members = memberHandler.inputMembers(
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

  public void delete() {
    System.out.println("[프로젝트 삭제]");

    int no = Prompt.inputInt("번호? ");

    Project project = findByNo(no);
    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      projectList.remove(project);
      System.out.println("프로젝트을 삭제하였습니다.");

    } else {
      System.out.println("프로젝트 삭제를 취소하였습니다.");
    }

  }

  private Project findByNo(int projectNo) {
    Project[] list = projectList.toArray(new Project[projectList.size()]);
    for (Project p : list) {
      if (p.getNo() == projectNo) {
        return p;
      }
    }
    return null;
  }

}








