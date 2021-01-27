package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskHandler {

  static final int LENGTH = 100;

  // 의존 객체(dependency)를 담을 인스턴스 필드
  // - 메서드가 작업할 때 사용할 객체를 담는다.
  MemberHandler memberList;

  Task[] tasks = new Task[LENGTH];
  int size = 0;

  // 생성자
  // - TaskHandler가 의존하는 객체를 반드시 주입하도록 강요한다.
  // - 다른 패키지에서 생성자를 호출할 수 있도록 공개한다.
  public TaskHandler(MemberHandler memberHandler) {
    this.memberList = memberHandler;
  }

  public void add() {
    System.out.println("[작업 등록]");

    Task t = new Task();
    t.no = Prompt.inputInt("번호? ");
    t.content = Prompt.inputString("내용? ");
    t.deadline = Prompt.inputDate("마감일? ");
    t.status = Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> ");

    while (true) {
      String name = Prompt.inputString("담당자?(취소: 빈 문자열) ");
      if (name.length() == 0) {
        System.out.println("작업 등록을 취소합니다.");
        return;
      } else if (this.memberList.exist(name)) {
        t.owner = name;
        break;
      } else {
        System.out.println("등록된 회원이 아닙니다.");
      }
    }

    this.tasks[this.size++] = t;
  }

  public void list() {
    System.out.println("[작업 목록]");

    for (int i = 0; i < this.size; i++) {
      Task t = this.tasks[i];

      String stateLabel = null;
      switch (t.status) {
        case 1:
          stateLabel = "진행중";
          break;
        case 2:
          stateLabel = "완료";
          break;
        default:
          stateLabel = "신규";
      }
      System.out.printf("%d, %s, %s, %s, %s\n", 
          t.no, t.content, t.deadline, stateLabel, t.owner);
    }
  }
}
