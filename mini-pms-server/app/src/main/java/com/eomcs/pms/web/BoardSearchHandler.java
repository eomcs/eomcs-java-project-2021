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
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

@SuppressWarnings("serial")
@WebServlet("/board/search")
public class BoardSearchHandler extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    BoardService boardService = (BoardService) request.getServletContext().getAttribute("boardService");

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    try {
      String keyword = request.getParameter("keyword");

      if (keyword.length() == 0) {
        out.println("검색어를 입력하세요.");
        return;
      }

      List<Board> list = boardService.search(keyword);

      if (list.size() == 0) {
        out.println("검색어에 해당하는 게시글이 없습니다.");
        return;
      }

      for (Board b : list) {
        out.printf("%d, %s, %s, %s, %d\n", 
            b.getNo(), 
            b.getTitle(), 
            b.getWriter().getName(),
            b.getRegisteredDate(),
            b.getViewCount());
      }

    } catch (Exception e) {
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);
      out.println(strWriter.toString());
    }
  }
}






