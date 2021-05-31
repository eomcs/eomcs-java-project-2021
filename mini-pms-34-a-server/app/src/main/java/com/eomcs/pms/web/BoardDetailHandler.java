package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

@Controller
public class BoardDetailHandler {

  BoardService boardService;

  public BoardDetailHandler(BoardService boardService) {
    this.boardService = boardService;
  }

  @RequestMapping("/board/detail")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    Board board = boardService.get(no);
    request.setAttribute("board", board);
    return "/jsp/board/detail.jsp";
  }
}






