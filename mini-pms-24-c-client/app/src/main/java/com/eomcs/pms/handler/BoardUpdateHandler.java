package com.eomcs.pms.handler;

import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardUpdateHandler implements Command {


  // 핸들러가 사용할 DAO : 의존 객체(dependency)
  BoardDao boardDao;

  // DAO 객체는 이 클래스가 작업하는데 필수 객체이기 때문에
  // 생성자를 통해 반드시 주입 받도록 한다.
  public BoardUpdateHandler(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 변경]");

    int no = Prompt.inputInt("번호? ");

    // Mybatis 해당 번호의 게시글 데이터를 가져와서 Board 객체를 만든 후 리턴한다.
    // 만약 이전에 조회할 게시글의 PK 값과 같은 값을 갖는 객체가 있다면,
    // 새로 Board 객체를 만들지 않고 기존 객체를 리턴한다.
    // => 이유?
    //    동일한 객체를 계속해서 만들지 않기 위해서이다.
    // => 동일한 객체인지 어떻게 아는가?
    //    PK에 해당하는 프로퍼티 값이 같을 경우 같은 객체로 간주한다.
    Board oldBoard = boardDao.findByNo(no);
    if (oldBoard == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    Board board = new Board();
    board.setNo(oldBoard.getNo());
    board.setTitle(Prompt.inputString(String.format("제목(%s)? ", oldBoard.getTitle())));
    board.setContent(Prompt.inputString(String.format("내용(%s)? ", oldBoard.getContent())));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("게시글 변경을 취소하였습니다.");
      return;
    }

    boardDao.update(board);

    System.out.println("게시글을 변경하였습니다.");
  }
}






