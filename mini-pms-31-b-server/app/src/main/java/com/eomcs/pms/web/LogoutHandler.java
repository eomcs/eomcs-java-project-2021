package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;

@SuppressWarnings("serial")
@WebServlet("/logout")
public class LogoutHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>로그인</title>");
    out.println("<meta http-equiv='Refresh' content='1;url=login'>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>로그아웃</h1>");

    Member member = (Member) request.getSession().getAttribute("loginUser");
    if (member == null) {
      out.println("<p>로그인 하지 않았습니다!</p>");

    } else {
      request.getSession().invalidate();
      out.printf("<p>%s 님 안녕히 가세요!</p>\n", member.getName());
    }

    out.println("</body>");
    out.println("</html>");

  }
}






