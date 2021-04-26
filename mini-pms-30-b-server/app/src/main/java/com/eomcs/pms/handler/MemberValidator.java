package com.eomcs.pms.handler;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

public class MemberValidator {

  MemberService memberService;

  public MemberValidator(MemberService memberService) {
    this.memberService = memberService;
  }

  public Member inputMember(String promptTitle, CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    while (true) {
      String name = prompt.inputString(promptTitle);
      if (name.length() == 0) {
        return null;
      } 

      Member m = memberService.search(name);
      if (m != null) {
        return m;
      }
      out.println("등록되지 않은 회원입니다.");
    }
  }

  public List<Member> inputMembers(String promptTitle, CommandRequest request, CommandResponse response) throws Exception {
    ArrayList<Member> members = new ArrayList<>();

    while (true) {
      Member member = inputMember(promptTitle, request, response);
      if (member == null) {
        return members;
      } else {
        members.add(member);
      }
    }
  }

}






