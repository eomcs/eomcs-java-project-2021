package com.eomcs.pms.handler;

import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberUpdateHandler implements Command {

  // 핸들러가 사용할 DAO : 의존 객체(dependency)
  MemberDao memberDao;

  // DAO 객체는 이 클래스가 작업하는데 필수 객체이기 때문에
  // 생성자를 통해 반드시 주입 받도록 한다.
  public MemberUpdateHandler(MemberDao memberDao) {
    this.memberDao = memberDao;
  }
  @Override
  public void service() throws Exception {
    System.out.println("[회원 변경]");

    int no = Prompt.inputInt("번호? ");

    Member oldMember = memberDao.findByNo(no);

    if (oldMember == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    Member member = new Member();
    member.setNo(oldMember.getNo());
    member.setName(Prompt.inputString(String.format("이름(%s)? ", oldMember.getName())));
    member.setEmail(Prompt.inputString(String.format("이메일(%s)? ", oldMember.getEmail())));
    member.setPassword(Prompt.inputString("새암호? "));
    member.setPhoto(Prompt.inputString(String.format("사진(%s)? ", oldMember.getPhoto())));
    member.setTel(Prompt.inputString(String.format("전화(%s)? ", oldMember.getTel())));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("회원 변경을 취소하였습니다.");
      return;
    }

    memberDao.update(member);

    System.out.println("회원을 변경하였습니다.");
  }
}






