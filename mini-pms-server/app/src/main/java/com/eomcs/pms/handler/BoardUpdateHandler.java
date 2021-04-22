package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.BoardService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/board/update")
public class BoardUpdateHandler implements Command {

  BoardService boardService;

  public BoardUpdateHandler(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[게시글 변경]");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      out.println("로그인 하지 않았습니다!");
      return;
    }

    int no = prompt.inputInt("번호? ");

    Board oldBoard = boardService.get(no);
    if (oldBoard == null) {
      out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    if (oldBoard.getWriter().getNo() != loginUser.getNo()) {
      out.println("변경 권한이 없습니다!");
      return;
    }

    Board board = new Board();
    board.setNo(oldBoard.getNo());
    board.setTitle(prompt.inputString(String.format("제목(%s)? ", oldBoard.getTitle())));
    board.setContent(prompt.inputString(String.format("내용(%s)? ", oldBoard.getContent())));

    String input = prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      out.println("게시글 변경을 취소하였습니다.");
      return;
    }

    boardService.update(board);

    out.println("게시글을 변경하였습니다.");
  }
}






