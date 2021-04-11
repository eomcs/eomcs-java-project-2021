package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskHandler_a {

  static final int LENGTH = 100;

  // 의존 객체(dependency)를 담을 인스턴스 필드
  // - 메서드가 작업할 때 사용할 객체를 담는다.
  MemberHandler memberList;

  Task[] tasks = new Task[LENGTH];
  int size = 0;

  // 생성자
  // - TaskHandler가 의존하는 객체를 반드시 주입하도록 강요한다.
  // - 다른 패키지에서 생성자를 호출할 수 있도록 공개한다.
  public TaskHandler_a(MemberHandler memberHandler) {
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

  public void detail() {
    System.out.println("[작업 상세보기]");

    int no = Prompt.inputInt("번호? ");

    Task task = findByNo(no);
    if (task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }

    System.out.printf("내용: %s\n", task.content);
    System.out.printf("마감일: %s\n", task.deadline);

    String stateLabel = null;
    switch (task.status) {
      case 1:
        stateLabel = "진행중";
        break;
      case 2:
        stateLabel = "완료";
        break;
      default:
        stateLabel = "신규";
    }
    System.out.printf("상태: %s\n", stateLabel);
    System.out.printf("담당자: %s\n", task.owner);

  }

  public void update() {
    System.out.println("[작업 변경]");

    int no = Prompt.inputInt("번호? ");

    Task task = findByNo(no);
    if (task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }

    String content = Prompt.inputString(String.format("내용(%s)? ", task.content));
    Date deadline = Prompt.inputDate(String.format("마감일(%s)? ", task.deadline));

    String stateLabel = null;
    switch (task.status) {
      case 1:
        stateLabel = "진행중";
        break;
      case 2:
        stateLabel = "완료";
        break;
      default:
        stateLabel = "신규";
    }
    int status = Prompt.inputInt(
        String.format("상태(%s)?\n0: 신규\n1: 진행중\n2: 완료\n> ", stateLabel));

    String owner = null;
    while (true) {
      owner = Prompt.inputString(String.format("담당자(%s)?(취소: 빈 문자열) ", task.owner));
      if (owner.length() == 0) {
        System.out.println("작업 등록을 취소합니다.");
        return;
      } else if (this.memberList.exist(owner)) {
        break;
      } else {
        System.out.println("등록된 회원이 아닙니다.");
      }
    }

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      task.content = content;
      task.deadline = deadline;
      task.status = status;
      task.owner = owner;
      System.out.println("작업을 변경하였습니다.");

    } else {
      System.out.println("작업 변경을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[작업 삭제]");

    int no = Prompt.inputInt("번호? ");

    int i = indexOf(no);
    if (i == -1) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      for (int x = i + 1; x < this.size; x++) {
        this.tasks[x-1] = this.tasks[x];
      }
      tasks[--this.size] = null; // 앞으로 당긴 후 맨 뒤의 항목은 null로 설정한다.

      System.out.println("작업을 삭제하였습니다.");

    } else {
      System.out.println("작업 삭제를 취소하였습니다.");
    }

  }

  // 작업 번호에 해당하는 인스턴스를 배열에서 찾아 그 인덱스를 리턴한다. 
  int indexOf(int taskNo) {
    for (int i = 0; i < this.size; i++) {
      Task task = this.tasks[i];
      if (task.no == taskNo) {
        return i;
      }
    }
    return -1;
  }

  // 작업 번호에 해당하는 인스턴스를 찾아 리턴한다.
  Task findByNo(int taskNo) {
    int i = indexOf(taskNo);
    if (i == -1) 
      return null;
    else 
      return this.tasks[i];
  }
}
