package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@SuppressWarnings("serial")
@WebServlet("/login")
public class LoginHandler extends GenericServlet {

  @Override
  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("[로그인]");

    String email = request.getParameter("email");
    String password = request.getParameter("password");

    try {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      Member member = memberService.get(email, password);
      if (member == null) {
        out.println("사용자 정보가 맞지 않습니다.");
        // 로그인 실패한다면 세션 객체의 모든 내용을 삭제한다.
        httpRequest.getSession().invalidate(); // 
        return;
      }

      // 로그인 성공한다면, 로그인 사용자 정보를 세션 객체에 보관한다.
      httpRequest.getSession().setAttribute("loginUser", member);

      out.printf("%s 님 환영합니다.\n", member.getName());

    } catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);
      out.println(strWriter.toString());
    } 
  }
}






