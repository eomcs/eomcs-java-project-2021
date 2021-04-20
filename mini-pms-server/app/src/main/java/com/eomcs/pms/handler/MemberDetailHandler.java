package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/member/detail")
public class MemberDetailHandler implements Command {

  MemberService memberService;

  public MemberDetailHandler(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[회원 상세보기]");

    int no = prompt.inputInt("번호? ");

    Member m = memberService.get(no);

    if (m == null) {
      out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    out.printf("이름: %s\n", m.getName());
    out.printf("이메일: %s\n", m.getEmail());
    out.printf("사진: %s\n", m.getPhoto());
    out.printf("전화: %s\n", m.getTel());
    out.printf("가입일: %s\n", m.getRegisteredDate());
  }
}






