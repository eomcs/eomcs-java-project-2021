package com.eomcs.pms.handler;

import java.util.Iterator;
import com.eomcs.driver.Statement;
import com.eomcs.pms.domain.Task;

public class TaskListHandler implements Command {

  @Override
  public void service(Statement stmt) throws Exception {
    System.out.println("[작업 목록]");

    Iterator<String> results = stmt.executeQuery("task/selectall");

    while (results.hasNext()) {
      String[] fields = results.next().split(",");
      System.out.printf("%s, %s, %s, %s, %s\n", 
          fields[0], 
          fields[1], 
          fields[2],
          Task.getStatusLabel(Integer.parseInt(fields[3])),
          fields[4]);
    }
  }

}
