package com.eomcs.pms.handler;

import com.eomcs.driver.Statement;
import com.eomcs.util.Prompt;

public class MemberValidator {

  Statement stmt;

  public MemberValidator(Statement stmt) {
    this.stmt = stmt;
  }

  public String inputMember(String promptTitle) {

    while (true) {
      String name = Prompt.inputString(promptTitle);
      if (name.length() == 0) {
        return null;
      } 

      try {
        return this.stmt.executeQuery("member/selectByName", name).next().split(",")[1];
      } catch (Exception e) {
        System.out.println("등록된 회원이 아닙니다.");
      }
    }
  }

  public String inputMembers(String promptTitle) {
    String members = "";
    while (true) {
      String name = inputMember(promptTitle);
      if (name == null) {
        return members;
      } else {
        if (!members.isEmpty()) {
          members += "/";
        }
        members += name;
      }
    }
  }

}






