package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.driver.Statement;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskUpdateHandler implements Command {

  Statement stmt;
  MemberValidator memberValidator;

  public TaskUpdateHandler(Statement stmt, MemberValidator memberValidator) {
    this.stmt = stmt;
    this.memberValidator = memberValidator;
  }


  @Override
  public void service() throws Exception {
    System.out.println("[작업 변경]");

    int no = Prompt.inputInt("번호? ");

    String[] fields = stmt.executeQuery("task/select", Integer.toString(no)).next().split(",");

    String content = Prompt.inputString(String.format("내용(%s)? ", fields[1]));
    Date deadline = Prompt.inputDate(String.format("마감일(%s)? ", fields[2]));
    int stat = Prompt.inputInt(String.format(
        "상태(%s)?\n0: 신규\n1: 진행중\n2: 완료\n> ", 
        Task.getStatusLabel(Integer.parseInt(fields[3]))));
    String owner = memberValidator.inputMember(String.format("담당자(%s)?(취소: 빈 문자열) ", fields[4]));
    if(owner == null) {
      System.out.println("작업 변경을 취소합니다.");
      return;
    }

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("작업 변경을 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("task/update", 
        String.format("%s,%s,%s,%s,%s", no, content, deadline, stat, owner));

    System.out.println("작업을 변경하였습니다.");
  }

}
