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
@WebServlet("/member/detail")
public class MemberDetailHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 정보</h1>");

    try {
      int no = Integer.parseInt(request.getParameter("no"));

      Member m = memberService.get(no);

      if (m == null) {
        out.println("<p>해당 번호의 회원이 없습니다.</p>");
        out.println("</body>");
        out.println("</html>");
        return;
      }

      out.println("<form action='update' method='post' enctype='multipart/form-data'>");
      out.println("<table border='1'>");
      out.println("<tbody>");
      out.printf("<tr><th>번호</th>"
          + " <td><input type='text' name='no' value='%d' readonly></td></tr>\n", m.getNo());
      out.printf("<tr><th>이름</th>"
          + " <td><input name='name' type='text' value='%s'></td></tr>\n", m.getName());
      out.printf("<tr><th>이메일</th>"
          + " <td><input name='email' type='email' value='%s'></td></tr>\n", m.getEmail());
      out.println("<tr><th>암호</th> <td><input name='password' type='password'></td></tr>");
      out.printf("<tr><th>전화</th>"
          + " <td><input name='tel' type='tel' value='%s'></td></tr>\n", 
          m.getTel() != null ? m.getTel() : "");
      out.printf("<tr><th>가입일</th> <td>%s</td></tr>\n", m.getRegisteredDate());
      out.printf("<tr><th>사진</th> <td>"
          + "<a href='%s'><img src='%s'></a><br>"
          + "<input name='photo' type='file'></td></tr>\n",
          m.getPhoto() != null ? "../upload/" + m.getPhoto() : "",
              m.getPhoto() != null ? "../upload/" + m.getPhoto() + "_80x80.jpg" : "../images/person_80x80.jpg");
      out.println("</tbody>");

      // 회원 관리를 관리자가 할 경우 모든 회원의 정보 변경 가능
      //Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      //if (loginUser != null && m.getNo() == loginUser.getNo()) {
      out.println("<tfoot>");
      out.println("<tr><td colspan='2'>");
      out.println("<input type='submit' value='변경'> "
          + "<a href='delete?no=" + m.getNo() + "'>삭제</a> ");
      out.println("</td></tr>");
      out.println("</tfoot>");
      //}

      out.println("</table>");
      out.println("</form>");


    } catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);
      out.printf("<pre>%s</pre>\n", strWriter.toString());
    }
    out.println("<p><a href='list'>목록</a></p>");

    out.println("</body>");
    out.println("</html>");
  }
}






