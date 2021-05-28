package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.BoardService;
import com.eomcs.util.Component;
import com.eomcs.util.PageController;

@Component("/board/delete")
public class BoardDeleteHandler implements PageController {

  BoardService boardService;

  public BoardDeleteHandler(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Board oldBoard = boardService.get(no);
    if (oldBoard == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    }

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (oldBoard.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("삭제 권한이 없습니다!");
    }

    boardService.delete(no);

    return "redirect:list";
  }
}






