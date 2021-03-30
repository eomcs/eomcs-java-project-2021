package com.eomcs.pms.handler;

import java.util.ArrayList;
import java.util.List;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberValidator {

  // 핸들러가 사용할 DAO : 의존 객체(dependency)
  MemberDao memberDao;

  // DAO 객체는 이 클래스가 작업하는데 필수 객체이기 때문에
  // 생성자를 통해 반드시 주입 받도록 한다.
  public MemberValidator(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  public Member inputMember(String promptTitle) throws Exception {
    while (true) {
      String name = Prompt.inputString(promptTitle);
      if (name.length() == 0) {
        return null;
      } 

      Member m = memberDao.findByName(name);
      if (m != null) {
        return m;
      }
      System.out.println("등록되지 않은 회원입니다.");
    }
  }

  public List<Member> inputMembers(String promptTitle) throws Exception {
    ArrayList<Member> members = new ArrayList<>();

    while (true) {
      Member member = inputMember(promptTitle);
      if (member == null) {
        return members;
      } else {
        members.add(member);
      }
    }
  }

}






