package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/member/update")
public class MemberUpdateHandler implements Command {

  MemberService memberService;

  public MemberUpdateHandler(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[회원 변경]");

    int no = prompt.inputInt("번호? ");

    Member oldMember = memberService.get(no);

    if (oldMember == null) {
      out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    Member member = new Member();
    member.setNo(oldMember.getNo());
    member.setName(prompt.inputString(String.format("이름(%s)? ", oldMember.getName())));
    member.setEmail(prompt.inputString(String.format("이메일(%s)? ", oldMember.getEmail())));
    member.setPassword(prompt.inputString("새암호? "));
    member.setPhoto(prompt.inputString(String.format("사진(%s)? ", oldMember.getPhoto())));
    member.setTel(prompt.inputString(String.format("전화(%s)? ", oldMember.getTel())));

    String input = prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      out.println("회원 변경을 취소하였습니다.");
      return;
    }

    memberService.update(member);

    out.println("회원을 변경하였습니다.");
  }
}






