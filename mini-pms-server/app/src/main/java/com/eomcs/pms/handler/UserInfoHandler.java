package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Member;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;

@Component("/userInfo")
public class UserInfoHandler implements Command {

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();

    Member member = (Member) request.getSession().getAttribute("loginUser");
    if (member == null) {
      out.println("로그인 하지 않았습니다!");
      return;
    }

    out.printf("사용자번호: %d\n", member.getNo());
    out.printf("이름: %s\n", member.getName());
    out.printf("이메일: %s\n", member.getEmail());
    out.printf("사진: %s\n", member.getPhoto());
  }
}






