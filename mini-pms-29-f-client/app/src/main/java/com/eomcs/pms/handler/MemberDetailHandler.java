package com.eomcs.pms.handler;

import com.eomcs.driver.Statement;
import com.eomcs.util.Prompt;

public class MemberDetailHandler implements Command {

  Statement stmt;

  public MemberDetailHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[회원 상세보기]");

    int no = Prompt.inputInt("번호? ");

    String[] fields = stmt.executeQuery("member/select", Integer.toString(no)).next().split(",");

    System.out.printf("이름: %s\n", fields[1]);
    System.out.printf("이메일: %s\n", fields[2]);
    System.out.printf("사진: %s\n", fields[3]);
    System.out.printf("전화: %s\n", fields[4]);
    System.out.printf("가입일: %s\n", fields[5]);
  }
}






