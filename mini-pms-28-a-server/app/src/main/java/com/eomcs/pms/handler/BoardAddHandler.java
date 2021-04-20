package com.eomcs.pms.handler;

import java.io.PrintWriter;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.BoardService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/board/add")
public class BoardAddHandler implements Command {

  // 핸들러가 사용할 Service 객체
  BoardService boardService;

  // Service 객체는 이 클래스가 작업하는데 필수 객체이기 때문에
  // 생성자를 통해 반드시 주입 받도록 한다.
  public BoardAddHandler(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[게시글 등록]");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      out.println("로그인 하지 않았습니다!");
      return;
    }

    Board b = new Board();

    b.setTitle(prompt.inputString("제목? "));
    b.setContent(prompt.inputString("내용? "));

    // 작성자는 로그인 사용자이다.
    b.setWriter(loginUser);

    boardService.add(b);
    out.println("게시글을 등록하였습니다.");
  }
}






