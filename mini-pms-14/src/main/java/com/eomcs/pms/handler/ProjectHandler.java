package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectHandler {

  static final int LENGTH = 100;

  // 의존 객체(dependency)를 담을 인스턴스 필드
  // - 메서드가 작업할 때 사용할 객체를 담는다.
  MemberHandler memberList;

  Project[] projects = new Project[LENGTH];
  int size = 0;

  // 생성자 정의
  // - ProjectHandler가 의존하는 객체를 반드시 주입하도록 강요한다.
  // - 다른 패키지에서 생성자를 호출할 수 있도록 공개한다.
  public ProjectHandler(MemberHandler memberHandler) {
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

    while (true) {
      String name = Prompt.inputString("만든이?(취소: 빈 문자열) ");
      if (name.length() == 0) {
        System.out.println("프로젝트 등록을 취소합니다.");
        return;
      } 
      if (this.memberList.exist(name)) {
        p.owner = name;
        break;
      }
      System.out.println("등록된 회원이 아닙니다.");
    }

    p.members = "";
    while (true) {
      String name = Prompt.inputString("팀원?(완료: 빈 문자열) ");
      if (name.length() == 0) {
        break;
      } else if (this.memberList.exist(name)) {
        if (!p.members.isEmpty()) {
          p.members += ",";
        }
        p.members += name;
      } else {
        System.out.println("등록된 회원이 아닙니다.");
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

}








