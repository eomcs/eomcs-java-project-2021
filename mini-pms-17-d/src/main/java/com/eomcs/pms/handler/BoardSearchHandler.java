package com.eomcs.pms.handler;

import java.util.ArrayList;
import java.util.List;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardSearchHandler extends AbstractBoardHandler {

  public BoardSearchHandler(List<Board> boardList) {
    super(boardList);
  }

  @Override
  public void service() {
    String keyword = Prompt.inputString("검색어? ");

    if (keyword.length() == 0) {
      System.out.println("검색어를 입력하세요.");
      return;
    }

    // 검색 결과를 담을 컬렉션을 준비한다.
    ArrayList<Board> list = new ArrayList<>();

    // 전체 게시글을 가져와서 검색어를 포함하는 게시글을 찾는다.
    Board[] boards = boardList.toArray(new Board[boardList.size()]);
    for (Board b : boards) {
      if (b.getTitle().contains(keyword) ||
          b.getContent().contains(keyword) ||
          b.getWriter().contains(keyword)) {
        list.add(b);
      }
    }

    if (list.isEmpty()) {
      System.out.println("검색어에 해당하는 게시글이 없습니다.");
      return;
    }

    // 검색 결과를 출력한다.
    for (Board b : list) {
      System.out.printf("%d, %s, %s, %s, %d, %d\n", 
          b.getNo(), 
          b.getTitle(), 
          b.getRegisteredDate(), 
          b.getWriter(), 
          b.getViewCount(),
          b.getLike());
    }

  }

}






