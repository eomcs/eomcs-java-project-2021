package com.eomcs.pms.handler;

import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;
import com.eomcs.stereotype.Component;
import com.eomcs.util.CommandRequest;
import com.eomcs.util.CommandResponse;
import com.eomcs.util.Prompt;

@Component("/board/search")
public class BoardSearchHandler implements Command {

  BoardService boardService;

  public BoardSearchHandler(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public void service(CommandRequest request, CommandResponse response) throws Exception {
    PrintWriter out = response.getWriter();
    Prompt prompt = request.getPrompt();

    String keyword = prompt.inputString("검색어? ");

    if (keyword.length() == 0) {
      out.println("검색어를 입력하세요.");
      return;
    }

    List<Board> list = boardService.search(keyword);

    if (list.size() == 0) {
      out.println("검색어에 해당하는 게시글이 없습니다.");
      return;
    }

    for (Board b : list) {
      out.printf("%d, %s, %s, %s, %d\n", 
          b.getNo(), 
          b.getTitle(), 
          b.getWriter().getName(),
          b.getRegisteredDate(),
          b.getViewCount());
    }
  }
}






