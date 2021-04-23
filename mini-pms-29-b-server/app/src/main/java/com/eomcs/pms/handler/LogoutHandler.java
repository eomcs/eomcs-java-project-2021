package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Member;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;

@Component("/logout")
public class LogoutHandler implements Command {

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();

    Member member = (Member) request.getSession().getAttribute("loginUser");
    if (member == null) {
      out.println("로그인 하지 않았습니다!");
      return;
    }

    request.getSession().invalidate();

    out.printf("%s 님 안녕히 가세요!\n", member.getName());
  }
}






