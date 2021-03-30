package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardSearchHandler implements Command {

  @Override
  public void service() throws Exception {
    String keyword = Prompt.inputString("검색어? ");

    if (keyword.length() == 0) {
      System.out.println("검색어를 입력하세요.");
      return;
    }

    List<Board> list = BoardDao.findByKeyword(keyword);

    if (list.size() == 0) {
      System.out.println("검색어에 해당하는 게시글이 없습니다.");
      return;
    }

    for (Board b : list) {
      System.out.printf("%d, %s, %s, %s, %d\n", 
          b.getNo(), 
          b.getTitle(), 
          b.getWriter().getName(),
          b.getRegisteredDate(),
          b.getViewCount());
    }
  }
}






