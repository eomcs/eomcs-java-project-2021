package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.BoardService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/board/delete")
public class BoardDeleteHandler implements Command {

  BoardService boardService;

  public BoardDeleteHandler(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[게시글 삭제]");

    int no = prompt.inputInt("번호? ");

    Board oldBoard = boardService.get(no);
    if (oldBoard == null) {
      out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (oldBoard.getWriter().getNo() != loginUser.getNo()) {
      out.println("삭제 권한이 없습니다!");
      return;
    }

    String input = prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      out.println("게시글 삭제를 취소하였습니다.");
      return;
    }

    boardService.delete(no);
    out.println("게시글을 삭제하였습니다.");
  }
}






