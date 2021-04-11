package com.eomcs.pms.handler;

import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberAddHandler implements Command {

  // 핸들러가 사용할 DAO : 의존 객체(dependency)
  MemberDao memberDao;

  // DAO 객체는 이 클래스가 작업하는데 필수 객체이기 때문에
  // 생성자를 통해 반드시 주입 받도록 한다.
  public MemberAddHandler(MemberDao memberDao) {
    this.memberDao = memberDao;
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

    memberDao.insert(m);

    System.out.println("회원을 등록하였습니다.");

  }
}






