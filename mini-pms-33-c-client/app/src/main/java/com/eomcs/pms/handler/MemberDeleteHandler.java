package com.eomcs.pms.handler;

import com.eomcs.pms.service.MemberService;
import com.eomcs.util.Prompt;

public class MemberDeleteHandler implements Command {

  MemberService memberService;

  public MemberDeleteHandler(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[회원 삭제]");

    int no = Prompt.inputInt("번호? ");

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("회원 삭제를 취소하였습니다.");
      return;
    }

    if (memberService.delete(no) == 0) {
      System.out.println("해당 번호의 회원이 없습니다.");
    } else {
      System.out.println("회원을 삭제하였습니다.");
    }
  }
}






