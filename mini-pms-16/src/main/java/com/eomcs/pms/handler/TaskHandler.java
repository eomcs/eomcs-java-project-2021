package com.eomcs.pms.handler;

import java.sql.Date;
import java.util.Iterator;
import java.util.LinkedList;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskHandler {

  private LinkedList<Task> taskList = new LinkedList<>();

  private MemberHandler memberHandler;

  public TaskHandler(MemberHandler memberHandler) {
    this.memberHandler = memberHandler;
  }

  public void add() {
    System.out.println("[작업 등록]");

    Task t = new Task();
    t.setNo(Prompt.inputInt("번호? "));
    t.setContent(Prompt.inputString("내용? "));
    t.setDeadline(Prompt.inputDate("마감일? "));
    t.setStatus(Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> "));

    t.setOwner(memberHandler.inputMember("담당자?(취소: 빈 문자열) "));
    if (t.getOwner() == null) {
      System.out.println("작업 등록을 취소하였습니다.");
      return;
    }

    taskList.add(t);
    System.out.println("작업을 등록했습니다.");
  }

  public void list() throws CloneNotSupportedException {
    System.out.println("[작업 목록]");

    Iterator<Task> iterator = taskList.iterator();

    while (iterator.hasNext()) {
      Task t = iterator.next();
      System.out.printf("%d, %s, %s, %s, %s\n", 
          t.getNo(), t.getContent(), t.getDeadline(), getStatusLabel(t.getStatus()), t.getOwner());
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

    System.out.printf("내용: %s\n", task.getContent());
    System.out.printf("마감일: %s\n", task.getDeadline());
    System.out.printf("상태: %s\n", getStatusLabel(task.getStatus()));
    System.out.printf("담당자: %s\n", task.getOwner());

  }



  public void update() {
    System.out.println("[작업 변경]");

    int no = Prompt.inputInt("번호? ");

    Task task = findByNo(no);
    if (task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }

    String content = Prompt.inputString(String.format("내용(%s)? ", task.getContent()));
    Date deadline = Prompt.inputDate(String.format("마감일(%s)? ", task.getDeadline()));
    int status = Prompt.inputInt(String.format(
        "상태(%s)?\n0: 신규\n1: 진행중\n2: 완료\n> ", getStatusLabel(task.getStatus())));
    String owner = memberHandler.inputMember(String.format("담당자(%s)?(취소: 빈 문자열) ", task.getOwner()));
    if(owner == null) {
      System.out.println("작업 변경을 취소합니다.");
      return;
    }

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      task.setContent(content);
      task.setDeadline(deadline);
      task.setStatus(status);
      task.setOwner(owner);
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
      taskList.remove(task);
      System.out.println("작업을 삭제하였습니다.");

    } else {
      System.out.println("작업 삭제를 취소하였습니다.");
    }

  }

  private String getStatusLabel(int status) {
    switch (status) {
      case 1:
        return "진행중";
      case 2:
        return "완료";
      default:
        return "신규";
    }
  }

  private Task findByNo(int taskNo) {
    Task[] list = taskList.toArray(new Task[taskList.size()]);
    for (Task t : list) {
      if (t.getNo() == taskNo) {
        return t;
      }
    }
    return null;
  }
}
