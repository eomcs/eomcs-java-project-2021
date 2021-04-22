package com.eomcs.pms.handler;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/board/detail")
public class BoardDetailHandler implements Command {

  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  BoardService boardService;

  public BoardDetailHandler(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    out.println("[게시글 상세보기]");

    int no = prompt.inputInt("번호? ");

    Board b = boardService.get(no);
    if (b == null) {
      out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    out.printf("제목: %s\n", b.getTitle());
    out.printf("내용: %s\n", b.getContent());
    out.printf("작성자: %s\n", b.getWriter().getName());
    out.printf("등록일: %s\n", formatter.format(b.getRegisteredDate()));
    out.printf("조회수: %s\n", b.getViewCount());
    out.printf("좋아요: %s\n", b.getLike());
  }
}






