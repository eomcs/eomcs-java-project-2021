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

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>로그인</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>로그인</h1>");

    out.println("<form method='post'>");
    out.println("<table border='1'>");
    out.println("<tbody>");
    out.println("<tr><th>이메일</th><td><input name='email' type='email'></td></tr>");
    out.println("<tr><th>암호</th><td><input name='password' type='password'></td></tr>");
    out.println("</tbody>");

    out.println("<tfoot>");
    out.println("<tr><td colspan='2'><button>로그인</button></td></tr>");
    out.println("</tfoot>");
    out.println("</table>");
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");

  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>로그인</title>");


    try {
      request.setCharacterEncoding("UTF-8");
      String email = request.getParameter("email");
      String password = request.getParameter("password");

      Member member = memberService.get(email, password);
      if (member == null) {
        // 로그인 실패한다면 세션 객체의 모든 내용을 삭제한다.
        request.getSession().invalidate(); 
        out.println("<meta http-equiv='Refresh' content='1;url=login'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>로그인 결과</h1>");
        out.println("<p>사용자 정보가 맞지 않습니다.</p>");

      } else {
        // 로그인 성공한다면, 로그인 사용자 정보를 세션 객체에 보관한다.
        request.getSession().setAttribute("loginUser", member);      

        out.println("<meta http-equiv='Refresh' content='1;url=userInfo'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>로그인 결과</h1>");
        out.printf("<p>%s 님 환영합니다.</p>\n", member.getName());
      }


    } catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);

      out.println("</head>");
      out.println("<body>");
      out.println("<h1>로그인 오류</h1>");
      out.printf("<pre>%s</pre>\n", strWriter.toString());
    }

    out.println("</body>");
    out.println("</html>");
  }
}






