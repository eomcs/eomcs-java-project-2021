package com.eomcs.pms.web.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;
import com.google.gson.Gson;


@SuppressWarnings("serial")
@WebServlet("/json/board/list")
public class BoardListHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    BoardService boardService = (BoardService) request.getServletContext().getAttribute("boardService");

    // JSP가 게시글 목록을 출력할 때 사용할 데이터를 준비한다.  
    try {
      String keyword = request.getParameter("keyword");
      List<Board> boards = null;
      if (keyword != null && keyword.length() > 0) {
        boards = boardService.search(keyword);
      } else {
        boards = boardService.list();
      }

      // 클라이언트에게 JSON 형식으로 데이터를 보내기
      response.setContentType("application/json;charset=UTF-8");
      PrintWriter out = response.getWriter();

      out.print(new Gson().toJson(boards));

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}






