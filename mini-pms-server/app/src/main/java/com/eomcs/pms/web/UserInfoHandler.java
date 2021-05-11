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
@WebServlet("/userInfo")
public class UserInfoHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>사용자 정보</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>사용자 정보</h1>");

    Member m = (Member) request.getSession().getAttribute("loginUser");
    if (m == null) {
      out.println("<p>로그인 하지 않았습니다!</p>");

    } else {
      out.println("<table border='1'>");
      out.println("<tbody>");
      out.printf("<tr><th>사용자 번호</th> <td>%d</td></tr>\n", m.getNo());
      out.printf("<tr><th>이름</th> <td>%s</td></tr>\n", m.getName());
      out.printf("<tr><th>이메일</th> <td>%s</td></tr>\n", m.getEmail());
      out.printf("<tr><th>사진</th> <td><a href='%s'><img src='%s'></a></td></tr>\n",
          m.getPhoto() != null ? "upload/" + m.getPhoto() : "",
              m.getPhoto() != null ? "upload/" + m.getPhoto() + "_80x80.jpg" : "images/person_80x80.jpg");

      out.println("</tbody></table>");
    }

    out.println("</body>");
    out.println("</html>");
  }
}






