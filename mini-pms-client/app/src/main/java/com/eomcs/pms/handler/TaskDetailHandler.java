package com.eomcs.pms.handler;

import com.eomcs.driver.Statement;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskDetailHandler implements Command {

  Statement stmt;

  public TaskDetailHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[작업 상세보기]");

    int no = Prompt.inputInt("번호? ");

    String[] fields = stmt.executeQuery("task/select", Integer.toString(no)).next().split(",");

    System.out.printf("내용: %s\n", fields[1]);
    System.out.printf("마감일: %s\n", fields[2]);
    System.out.printf("상태: %s\n", Task.getStatusLabel(Integer.parseInt(fields[3])));
    System.out.printf("담당자: %s\n", fields[4]);
  }

}
