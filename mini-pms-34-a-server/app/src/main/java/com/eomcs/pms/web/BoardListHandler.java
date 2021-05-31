package com.eomcs.pms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

@Controller
public class BoardListHandler {

  BoardService boardService;

  public BoardListHandler(BoardService boardService) {
    this.boardService = boardService;
  }

  @RequestMapping("/board/list")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String keyword = request.getParameter("keyword");
    List<Board> boards = null;
    if (keyword != null && keyword.length() > 0) {
      boards = boardService.search(keyword);
    } else {
      boards = boardService.list();
    }

    request.setAttribute("list", boards);

    return "/jsp/board/list.jsp";
  }
}






