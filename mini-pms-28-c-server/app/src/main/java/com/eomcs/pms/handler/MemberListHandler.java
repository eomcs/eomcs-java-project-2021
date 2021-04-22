package com.eomcs.pms.handler;

import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;

@Component("/member/list")
public class MemberListHandler implements Command {

  MemberService memberService;

  public MemberListHandler(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    out.println("[회원 목록]");

    List<Member> list = memberService.list();

    for (Member m : list) {
      out.printf("%d, %s, %s, %s, %s\n", 
          m.getNo(), 
          m.getName(), 
          m.getEmail(),
          m.getPhoto(),
          m.getTel());
    }
  }
}






