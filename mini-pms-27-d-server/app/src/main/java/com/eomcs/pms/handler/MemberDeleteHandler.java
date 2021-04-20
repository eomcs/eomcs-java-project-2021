package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.service.MemberService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/member/delete")
public class MemberDeleteHandler implements Command {

  MemberService memberService;

  public MemberDeleteHandler(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[회원 삭제]");

    int no = prompt.inputInt("번호? ");

    String input = prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      out.println("회원 삭제를 취소하였습니다.");
      return;
    }

    if (memberService.delete(no) == 0) {
      out.println("해당 번호의 회원이 없습니다.");
    } else {
      out.println("회원을 삭제하였습니다.");
    }
  }
}






