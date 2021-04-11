package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectHandler_b {

  static final int LENGTH = 100;

  // 의존 객체(dependency)를 담을 인스턴스 필드
  // - 메서드가 작업할 때 사용할 객체를 담는다.
  MemberHandler memberList;

  Project[] projects = new Project[LENGTH];
  int size = 0;

  // 생성자 정의
  // - ProjectHandler가 의존하는 객체를 반드시 주입하도록 강요한다.
  // - 다른 패키지에서 생성자를 호출할 수 있도록 공개한다.
  public ProjectHandler_b(MemberHandler memberHandler) {
    this.memberList = memberHandler;
  }


  public void add() {
    System.out.println("[프로젝트 등록]");

    Project p = new Project();
    p.no = Prompt.inputInt("번호? ");
    p.title = Prompt.inputString("프로젝트명? ");
    p.content = Prompt.inputString("내용? ");
    p.startDate = Prompt.inputDate("시작일? ");
    p.endDate = Prompt.inputDate("종료일? ");

    p.owner = inputMember("만든이?(취소: 빈 문자열) ");
    if (p.owner == null) {
      System.out.println("프로젝트 입력을 취소합니다.");
      return;
    }

    p.members = "";
    while (true) {
      String name = inputMember("팀원?(완료: 빈 문자열) ");
      if (name == null) {
        break;
      } else {
        if (!p.members.isEmpty()) {
          p.members += ",";
        }
        p.members += name;
      }
    }

    this.projects[this.size++] = p;
  }

  public void list() {
    System.out.println("[프로젝트 목록]");

    for (int i = 0; i < size; i++) {
      Project p = projects[i];
      System.out.printf("%d, %s, %s, %s, %s, [%s]\n",
          p.no, p.title, p.startDate, p.endDate, p.owner, p.members);
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

    System.out.printf("프로젝트명: %s\n", project.title);
    System.out.printf("내용: %s\n", project.content);
    System.out.printf("시작일: %s\n", project.startDate);
    System.out.printf("종료일: %s\n", project.endDate);
    System.out.printf("관리자: %s\n", project.owner);
    System.out.printf("팀원: %s\n", project.members);

  }

  public void update() {
    System.out.println("[프로젝트 변경]");

    int no = Prompt.inputInt("번호? ");

    Project project = findByNo(no);
    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    String title = Prompt.inputString(String.format("프로젝트명(%s)? ", project.title));
    String content = Prompt.inputString(String.format("내용(%s)? ", project.content));
    Date startDate = Prompt.inputDate(String.format("시작일(%s)? ", project.startDate));
    Date endDate = Prompt.inputDate(String.format("종료일(%s)? ", project.endDate));

    String owner = inputMember(String.format("만든이(%s)?(취소: 빈 문자열) ", project.owner));
    if (owner == null) {
      System.out.println("프로젝트 변경을 취소합니다.");
      return;
    }

    String members = "";
    while (true) {
      String name = inputMember(String.format("팀원(%s)?(완료: 빈 문자열) ", project.members));
      if (name == null) {
        break;
      } else {
        if (!members.isEmpty()) {
          members += ",";
        }
        members += name;
      }
    }

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      project.title = title;
      project.content = content;
      project.startDate = startDate;
      project.endDate = endDate;
      project.owner = owner;
      project.members = members;

      System.out.println("프로젝트을 변경하였습니다.");

    } else {
      System.out.println("프로젝트 변경을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[프로젝트 삭제]");

    int no = Prompt.inputInt("번호? ");

    int i = indexOf(no);
    if (i == -1) {
      System.out.println("해당 번호의 프로젝트이 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      for (int x = i + 1; x < this.size; x++) {
        this.projects[x-1] = this.projects[x];
      }
      projects[--this.size] = null; // 앞으로 당긴 후 맨 뒤의 항목은 null로 설정한다.

      System.out.println("프로젝트을 삭제하였습니다.");

    } else {
      System.out.println("프로젝트 삭제를 취소하였습니다.");
    }

  }

  // 프로젝트 번호에 해당하는 인스턴스를 배열에서 찾아 그 인덱스를 리턴한다. 
  int indexOf(int projectNo) {
    for (int i = 0; i < this.size; i++) {
      Project project = this.projects[i];
      if (project.no == projectNo) {
        return i;
      }
    }
    return -1;
  }

  // 프로젝트 번호에 해당하는 인스턴스를 찾아 리턴한다.
  Project findByNo(int projectNo) {
    int i = indexOf(projectNo);
    if (i == -1) 
      return null;
    else 
      return this.projects[i];
  }

  String inputMember(String promptTitle) {
    while (true) {
      String name = Prompt.inputString(promptTitle);
      if (name.length() == 0) {
        return null;
      } 
      if (this.memberList.exist(name)) {
        return name;
      }
      System.out.println("등록된 회원이 아닙니다.");
    }
  }

}








