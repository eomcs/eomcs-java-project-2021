package com.eomcs.pms.handler;

import java.util.ArrayList;
import java.util.Iterator;
import com.eomcs.pms.domain.Board;

public class BoardListHandler {

  private ArrayList<Board> boardList = new ArrayList<>();

  public void list() throws CloneNotSupportedException {
    System.out.println("[게시글 목록]");

    Iterator<Board> iterator = boardList.iterator();

    while (iterator.hasNext()) {
      Board b = iterator.next();
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






