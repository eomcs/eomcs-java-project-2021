package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberUpdateHandler extends AbstractMemberHandler {

  public MemberUpdateHandler(List<Member> memberList) {
    super(memberList);
  }

  @Override
  public void service() {
    System.out.println("[회원 변경]");

    int no = Prompt.inputInt("번호? ");

    Member member = findByNo(no);
    if (member == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    String name = Prompt.inputString(String.format("이름(%s)? ", member.getName()));
    String email = Prompt.inputString(String.format("이메일(%s)? ", member.getEmail()));
    String photo = Prompt.inputString(String.format("사진(%s)? ", member.getPhoto()));
    String tel = Prompt.inputString(String.format("전화(%s)? ", member.getTel()));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      member.setName(name);
      member.setEmail(email);
      member.setPhoto(photo);
      member.setTel(tel);
      System.out.println("회원을 변경하였습니다.");

    } else {
      System.out.println("회원 변경을 취소하였습니다.");
    }
  }
}






