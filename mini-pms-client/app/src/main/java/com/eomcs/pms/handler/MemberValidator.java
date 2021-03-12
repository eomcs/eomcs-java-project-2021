package com.eomcs.pms.handler;

import com.eomcs.driver.Statement;
import com.eomcs.util.Prompt;

public class MemberValidator {

  public static String inputMember(String promptTitle, Statement stmt) throws Exception{

    while (true) {
      String name = Prompt.inputString(promptTitle);
      if (name.length() == 0) {
        return null;
      } 

      try {
        return stmt.executeQuery("member/selectByName", name).next().split(",")[1];
      } catch (Exception e) {
        System.out.println("등록된 회원이 아닙니다.");
      }
    }
  }

  public static String inputMembers(String promptTitle, Statement stmt) throws Exception{
    String members = "";
    while (true) {
      String name = inputMember(promptTitle, stmt);
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






