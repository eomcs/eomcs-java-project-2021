package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskHandler {


  MemberHandler memberList;

  Node first;
  Node last;
  int size = 0;  

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

    t.owner = inputMember("담당자?(취소: 빈 문자열) ");
    if (t.owner == null) {
      System.out.println("작업 등록을 취소하였습니다.");
      return;
    }

    Node node = new Node(t);

    if (last == null) {
      last = node;
      first = node;
    } else { 
      last.next = node; 
      node.prev = last; 
      last = node; 
    }

    this.size++;
    System.out.println("작업을 등록하였습니다.");
  }

  public void list() {
    System.out.println("[작업 목록]");

    Node cursor = first;

    while (cursor != null) {
      Task t = cursor.task;

      System.out.printf("%d, %s, %s, %s, %s\n", 
          t.no, t.content, t.deadline, getStatusLabel(t.status), t.owner);

      cursor = cursor.next;
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
    System.out.printf("상태: %s\n", getStatusLabel(task.status));
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
    int status = Prompt.inputInt(String.format(
        "상태(%s)?\n0: 신규\n1: 진행중\n2: 완료\n> ", getStatusLabel(task.status)));
    String owner = inputMember(String.format("담당자(%s)?(취소: 빈 문자열) ", task.owner));
    if(owner == null) {
      System.out.println("작업 변경을 취소합니다.");
      return;
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

    Task task = findByNo(no);
    if (task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      Node cursor = first;
      while (cursor != null) {
        if (cursor.task == task) {
          if (first == last) {
            first = last = null;
            break;
          }
          if (cursor == first) {
            first = cursor.next;
            cursor.prev = null;
          } else {
            cursor.prev.next = cursor.next;
            if (cursor.next != null) {
              cursor.next.prev = cursor.prev;
            }
          }
          if (cursor == last) {
            last = cursor.prev;
          }
          this.size--;
          break;
        }
        cursor = cursor.next;
      }

      System.out.println("작업을 삭제하였습니다.");

    } else {
      System.out.println("작업 삭제를 취소하였습니다.");
    }

  }

  // 작업 번호에 해당하는 인스턴스를 찾아 리턴한다.
  Task findByNo(int taskNo) {
    Node cursor = first;
    while (cursor != null) {
      Task t = cursor.task;
      if (t.no == taskNo) {
        return t;
      }
      cursor = cursor.next;
    }
    return null;
  }

  String inputMember(String promptTitle) {
    while (true) {
      String name = Prompt.inputString(promptTitle);
      if (name.length() == 0) {
        return null;
      } else if (this.memberList.exist(name)) {
        return name;
      } else {
        System.out.println("등록된 회원이 아닙니다.");
      }
    }
  }

  String getStatusLabel(int status) {
    switch (status) {
      case 1:
        return "진행중";
      case 2:
        return "완료";
      default:
        return "신규";
    }
  }

  static class Node {
    Task task;
    Node next;
    Node prev;

    Node(Task t) {
      this.task = t;
    }
  }
}
