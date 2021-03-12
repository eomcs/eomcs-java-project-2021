package com.eomcs.pms.handler;

import com.eomcs.driver.Statement;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskAddHandler implements Command {

  @Override
  public void service(Statement stmt) throws Exception {
    System.out.println("[작업 등록]");

    Task t = new Task();
    t.setContent(Prompt.inputString("내용? "));
    t.setDeadline(Prompt.inputDate("마감일? "));
    t.setStatus(Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> "));

    t.setOwner(MemberValidator.inputMember("담당자?(취소: 빈 문자열) ", stmt));
    if (t.getOwner() == null) {
      System.out.println("작업 등록을 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("task/insert", 
        String.format("%s,%s,%s,%s", 
            t.getContent(),
            t.getDeadline(),
            t.getStatus(),
            t.getOwner()));

    System.out.println("작업을 등록했습니다.");
  }
}
