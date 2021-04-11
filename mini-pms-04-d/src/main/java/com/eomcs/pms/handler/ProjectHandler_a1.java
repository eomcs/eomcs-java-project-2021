package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.util.Prompt;

public class ProjectHandler_a1 {

  static class Project {
    int no;
    String title;
    String content;
    Date startDate;
    Date endDate;
    String owner;
    String members;  
  }

  static final int LENGTH = 100;
  static Project[] projects = new Project[LENGTH];
  static int size = 0;

  public static void add() {
    System.out.println("[프로젝트 등록]");

    Project p = new Project();
    p.no = Prompt.inputInt("번호? ");
    p.title = Prompt.inputString("프로젝트명? ");
    p.content = Prompt.inputString("내용? ");
    p.startDate = Prompt.inputDate("시작일? ");
    p.endDate = Prompt.inputDate("종료일? ");
    while (true) {
      String name = Prompt.inputString("만든이?(취소: 빈 문자열) ");
      for (int i = 0; i < MemberHandler.size; i++) {
        if (name.equals(MemberHandler.members[i].name)) {
          p.owner = name;
          break;
        }
      }
      if (p.owner != null) {
        break;
      }
      System.out.println("등록된 회원이 아닙니다.");
    }
    p.members = Prompt.inputString("팀원? ");

    projects[size++] = p;
  }

  public static void list() {
    System.out.println("[프로젝트 목록]");

    for (int i = 0; i < size; i++) {
      Project p = projects[i];
      System.out.printf("%d, %s, %s, %s, %s\n",
          p.no, p.title, p.startDate, p.endDate, p.owner);
    }
  }

}








