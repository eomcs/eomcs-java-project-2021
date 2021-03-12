package com.eomcs.pms.handler;

import com.eomcs.driver.Statement;
import com.eomcs.util.Prompt;

public class TaskDeleteHandler implements Command {

  @Override
  public void service(Statement stmt) throws Exception {
    System.out.println("[작업 삭제]");

    int no = Prompt.inputInt("번호? ");

    stmt.executeQuery("task/select", Integer.toString(no));

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("작업 삭제를 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("task/delete", Integer.toString(no));

    System.out.println("작업을 삭제하였습니다.");
  }
}
