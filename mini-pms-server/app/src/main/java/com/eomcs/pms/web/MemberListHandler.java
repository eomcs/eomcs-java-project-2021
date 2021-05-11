package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@SuppressWarnings("serial")
@WebServlet("/member/list") 
public class MemberListHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 클라이언트가 /board/list 를 요청하면 톰캣 서버가 이 메서드를 호출한다. 

    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원</h1>");

    out.println("<p><a href='form.html'>새 회원</a></p>");

    try {
      List<Member> list = memberService.list(request.getParameter("keyword"));

      out.println("<table border='1'>");
      out.println("<thead>");
      out.println("<tr>");
      out.println("<th>번호</th> <th> </th><th>이름</th> <th>이메일</th> <th>전화</th>");
      out.println("</tr>");
      out.println("</thead>");
      out.println("<tbody>");

      for (Member m : list) {
        out.printf("<tr>"
            + " <td>%d</td>"
            + " <td><img src='%s'></td>"
            + " <td><a href='detail?no=%1$d'>%s</a></td>"
            + " <td>%s</td>"
            + " <td>%s</td> </tr>\n", 
            m.getNo(), 
            m.getPhoto() != null ? "../upload/" + m.getPhoto() + "_30x30.jpg" : "../images/person_30x30.jpg",
                m.getName(), 
                m.getEmail(),
                m.getTel());
      }
      out.println("</tbody>");
      out.println("</table>");

      out.println("<form action='list' method='get'>");
      String keyword = request.getParameter("keyword");
      out.printf("<input type='search' name='keyword' value='%s'> \n", 
          keyword != null ? keyword : "");
      out.println("<button>검색</button>");
      out.println("</form>");

    } catch (Exception e) {
      // 상세 오류 내용을 StringWriter로 출력한다.
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);

      // StringWriter 에 들어 있는 출력 내용을 꺼내 클라이언트로 보낸다.
      out.printf("<pre>%s</pre>\n", strWriter.toString());
    }

    out.println("</body>");
    out.println("</html>");
  }
}






