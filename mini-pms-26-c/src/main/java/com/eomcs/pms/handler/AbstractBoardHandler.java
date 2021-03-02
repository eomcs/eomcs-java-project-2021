package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Board;

// 이 클래스를 상속 받는 서브 클래스는 
// 반드시 Command 규칙을 따르도록 강제한다.
public abstract class AbstractBoardHandler implements Command {

  // 특정 클래스를 지정하기 보다는 
  // List 규칙에 따라 사용할 수 있는 객체를 요구하라!
  // 그러면 훨씬 코드가 유연해진다.
  protected List<Board> boardList;

  public AbstractBoardHandler(List<Board> boardList) {
    this.boardList = boardList;
  }

  protected Board findByNo(int boardNo) {
    Board[] list = boardList.toArray(new Board[0]);
    for (Board b : list) {
      if (b.getNo() == boardNo) {
        return b;
      }
    }
    return null;
  }

}






