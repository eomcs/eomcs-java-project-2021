package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@SuppressWarnings("serial")
@WebServlet("/login")
public class LoginHandler extends HttpServlet {

  // Servlet 인터페이스의 오리지널 메서드를 재정의 하는 대신에
  // HttpServlet 클래스에서 추가한 service() 메서드를 재정의한다.
  // => 톰캣 서버 
  //       호출 ----> service(ServletRequest, ServletResponse)
  //                    호출 ----> service(HttpServletRequest, HttpServletResponse)
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("[로그인]");

    String email = request.getParameter("email");
    String password = request.getParameter("password");

    try {
      Member member = memberService.get(email, password);
      if (member == null) {
        out.println("사용자 정보가 맞지 않습니다.");
        // 로그인 실패한다면 세션 객체의 모든 내용을 삭제한다.
        request.getSession().invalidate();  
        return;
      }

      // 로그인 성공한다면, 로그인 사용자 정보를 세션 객체에 보관한다.
      request.getSession().setAttribute("loginUser", member);

      out.printf("%s 님 환영합니다.\n", member.getName());

    } catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);
      out.println(strWriter.toString());
    } 
  }
}






