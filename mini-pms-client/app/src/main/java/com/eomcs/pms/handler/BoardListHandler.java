package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;
import com.eomcs.stereotype.Component;

@Component(value="/board/list")
public class BoardListHandler implements Command {

  BoardService boardService;

  public BoardListHandler(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 목록]");

    List<Board> boards = boardService.list();

    for (Board b : boards) {
      System.out.printf("%d, %s, %s, %s, %d\n", 
          b.getNo(), 
          b.getTitle(), 
          b.getWriter().getName(),
          b.getRegisteredDate(),
          b.getViewCount());
    }
  }
}






