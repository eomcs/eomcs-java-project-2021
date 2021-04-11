package com.eomcs.pms.handler;

import com.eomcs.driver.Statement;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberAddHandler implements Command {

  Statement stmt;

  public MemberAddHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[회원 등록]");

    Member m = new Member();
    m.setName(Prompt.inputString("이름? "));
    m.setEmail(Prompt.inputString("이메일? "));
    m.setPassword(Prompt.inputString("암호? "));
    m.setPhoto(Prompt.inputString("사진? "));
    m.setTel(Prompt.inputString("전화? "));

    stmt.executeUpdate("member/insert", 
        String.format("%s,%s,%s,%s,%s", 
            m.getName(), m.getEmail(), m.getPassword(), m.getPhoto(), m.getTel()));

    System.out.println("회원을 등록하였습니다.");
  }
}






