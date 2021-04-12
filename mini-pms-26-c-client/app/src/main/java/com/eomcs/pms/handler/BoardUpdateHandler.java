package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.Prompt;

@Component(value="/board/update")
public class BoardUpdateHandler implements Command {


  BoardService boardService;

  public BoardUpdateHandler(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 변경]");

    int no = Prompt.inputInt("번호? ");

    Board oldBoard = boardService.get(no);
    if (oldBoard == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    Board board = new Board();
    board.setNo(oldBoard.getNo());
    board.setTitle(Prompt.inputString(String.format("제목(%s)? ", oldBoard.getTitle())));
    board.setContent(Prompt.inputString(String.format("내용(%s)? ", oldBoard.getContent())));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("게시글 변경을 취소하였습니다.");
      return;
    }

    boardService.update(board);

    System.out.println("게시글을 변경하였습니다.");
  }
}






