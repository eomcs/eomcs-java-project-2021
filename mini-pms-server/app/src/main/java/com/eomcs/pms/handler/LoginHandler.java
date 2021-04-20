package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/login")
public class LoginHandler implements Command {

  MemberService memberService;

  public LoginHandler(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[로그인]");

    String email = prompt.inputString("이메일? ");
    String password = prompt.inputString("암호? ");

    Member member = memberService.get(email, password);
    if (member == null) {
      out.println("사용자 정보가 맞지 않습니다.");
      // 로그인 실패한다면 세션 객체의 모든 내용을 삭제한다.
      request.getSession().invalidate(); // 
      return;
    }

    // 로그인 성공한다면, 로그인 사용자 정보를 세션 객체에 보관한다.
    request.getSession().setAttribute("loginUser", member);

    out.printf("%s 님 환영합니다.\n", member.getName());
  }
}






