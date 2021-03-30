package com.eomcs.pms.handler;

import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardUpdateHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 변경]");

    int no = Prompt.inputInt("번호? ");

    Board board = BoardDao.findByNo(no);
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    board.setTitle(Prompt.inputString(String.format("제목(%s)? ", board.getTitle())));
    board.setContent(Prompt.inputString(String.format("내용(%s)? ", board.getContent())));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("게시글 변경을 취소하였습니다.");
      return;
    }

    BoardDao.update(board);

    System.out.println("게시글을 변경하였습니다.");
  }
}






