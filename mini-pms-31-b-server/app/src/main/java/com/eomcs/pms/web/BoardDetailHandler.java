package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.BoardService;

@SuppressWarnings("serial")
@WebServlet("/board/detail")
public class BoardDetailHandler extends HttpServlet {

  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    BoardService boardService = (BoardService) request.getServletContext().getAttribute("boardService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    int no = Integer.parseInt(request.getParameter("no"));

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>게시글 상세</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 상세보기</h1>");

    try {
      Board b = boardService.get(no);
      if (b == null) {
        out.println("<p>해당 번호의 게시글이 없습니다.</p>");
        out.println("</body>");
        out.println("</html>");
        return;
      }
      out.println("<form action='update' method='post'>");
      out.println("<table border='1'>");
      out.println("<tbody>");
      out.printf("<tr><th>번호</th>"
          + " <td><input type='text' name='no' value='%d' readonly></td></tr>\n", b.getNo());
      out.printf("<tr><th>제목</th>"
          + " <td><input name='title' type='text' value='%s'></td></tr>\n", b.getTitle());
      out.printf("<tr><th>내용</th>"
          + " <td><textarea name='content' rows='10' cols='60'>%s</textarea></td></tr>\n", b.getContent());
      out.printf("<tr><th>작성자</th> <td>%s</td></tr>\n", b.getWriter().getName());
      out.printf("<tr><th>등록일</th> <td>%s</td></tr>\n", formatter.format(b.getRegisteredDate()));
      out.printf("<tr><th>조회수</th> <td>%s</td></tr>\n", b.getViewCount());
      out.printf("<tr><th>좋아요</th> <td>%s</td></tr>\n", b.getLike());
      out.println("</tbody>");

      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      if (loginUser != null && b.getWriter().getNo() == loginUser.getNo()) {
        out.println("<tfoot>");
        out.println("<tr><td colspan='2'>");
        out.println("<input type='submit' value='변경'> "
            + "<a href='delete?no=" + b.getNo() + "'>삭제</a> ");
        out.println("</td></tr>");
        out.println("</tfoot>");
      }

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






