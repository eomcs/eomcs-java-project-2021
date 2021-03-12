package com.eomcs.pms.handler;

import java.util.Iterator;
import com.eomcs.driver.Statement;

public class MemberListHandler implements Command {

  Statement stmt;

  public MemberListHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[회원 목록]");

    Iterator<String> results = stmt.executeQuery("member/selectall");

    while (results.hasNext()) {
      String[] fields = results.next().split(",");
      System.out.printf("%s, %s, %s, %s, %s\n",
          fields[0], 
          fields[1], 
          fields[2],
          fields[3],
          fields[4]);
    }
  }
}






