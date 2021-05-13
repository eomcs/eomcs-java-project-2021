package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;


@SuppressWarnings("serial")
@WebServlet("/board/list")
public class BoardListHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 클라이언트가 /board/list 를 요청하면 톰캣 서버가 이 메서드를 호출한다. 

    BoardService boardService = (BoardService) request.getServletContext().getAttribute("boardService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>게시글 목록</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 목록</h1>");

    out.println("<p><a href='form.html'>새 글</a></p>");

    try {
      List<Board> boards = boardService.list();

      out.println("<table border='1'>");
      out.println("<thead>");
      out.println("<tr>");
      out.println("<th>번호</th> <th>제목</th> <th>작성자</th> <th>등록일</th> <th>조회수</th>");
      out.println("</tr>");
      out.println("</thead>");
      out.println("<tbody>");

      for (Board b : boards) {
        out.printf("<tr>"
            + " <td>%d</td>"
            + " <td><a href='detail?no=%1$d'>%s</a></td>"
            + " <td>%s</td>"
            + " <td>%s</td>"
            + " <td>%d</td> </tr>\n", 
            b.getNo(), 
            b.getTitle(), 
            b.getWriter().getName(),
            b.getRegisteredDate(),
            b.getViewCount());
      }
      out.println("</tbody>");
      out.println("</table>");

      // 포워딩을 테스트하기 위해 일부러 예외를 발생시킴
      //      if (request.getParameter("okok") == null) {
      //        throw new Exception("테스트하기 위해 일부러 오류를 발생시킨다.");
      //      }

      out.println("<form action='search' method='get'>");
      out.println("<input type='text' name='keyword'> ");
      out.println("<button>검색</button>");
      out.println("</form>");


    } catch (Exception e) {
      // 예외가 발생하면 예외 정보를 ServletRequest 보관소에 담는다.
      request.setAttribute("exception", e);

      // 예외 내용을 출력하기 위해 ErrorHandler 에게 실행을 넘긴다.(포워딩)
      // - 이때 이 서블릿 버퍼로 출력한 내용은 모두 버려질 것이다.
      RequestDispatcher 요청배달자 = request.getRequestDispatcher("/error");
      요청배달자.forward(request, response);

      System.out.println("========> 오호라!!! ");
      // 다른 서블릿으로 포워딩 한 이후, 그 서블릿의 실행을 마친 후 되돌아 온다.
      // 되돌아 온 이후에 클라이언트로 출력하는 작업은 모두 무시된다.
      // 그러니 다음 문장을 실행할 이유가 없다.
      // 따라서 forwarding을 수행하고 리턴한 이후에 의미없이 명령을 실행하지 않도록 
      // 이 메서드를 종료하게 만드는 것이 좋다.
      return;
    }

    out.println("</body>");
    out.println("</html>");
  }
}






