package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.BoardService;

@Controller
public class BoardUpdateHandler {

  BoardService boardService;

  public BoardUpdateHandler(BoardService boardService) {
    this.boardService = boardService;
  }

  @RequestMapping("/board/update")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));

    Board oldBoard = boardService.get(no);
    if (oldBoard == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    } 

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (oldBoard.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("변경 권한이 없습니다!");
    }

    Board board = new Board();
    board.setNo(oldBoard.getNo());
    board.setTitle(request.getParameter("title"));
    board.setContent(request.getParameter("content"));
    boardService.update(board);

    return "redirect:list";
  }
}






