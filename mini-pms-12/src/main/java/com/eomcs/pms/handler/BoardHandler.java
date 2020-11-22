package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.util.Prompt;

public class BoardHandler {

  // 게시물 데이터
  static class Board {
    int no;
    String title;
    String content;
    String writer;
    Date registeredDate;
    int viewCount;
  }
  // 공통으로 사용할 값을 보관하는 변수는 스태틱 멤버(클래스 멤버)로 만든다.
  static final int LENGTH = 100;

  // 개별적으로 값을 보관해야 하는 변수는 인스턴스 멤버(non-static 멤버)로 만든다.
  Board[] list = new Board[LENGTH];
  int size = 0;

  // 인스턴스 메서드
  // => 인스턴스가 있어야만 호출할 수 있는 메서드이다.
  // => 인스턴스를 사용하는 메서드인 경우 인스턴스 메서드로 선언하라.
  // => 호출할 때는 반드시 인스턴스 주소를 줘야 한다.
  //      인스턴스주소.메서드명();
  // => 이렇게 인스턴스의 변수 값을 다루는 메서드는
  //    "연산자(operation)"라 부를 수 있다.
  //
  public void add() {
    System.out.println("[게시물 등록]");

    Board board = new Board();
    board.no = Prompt.inputInt("번호? ");
    board.title = Prompt.inputString("제목? ");
    board.content = Prompt.inputString("내용? ");
    board.writer = Prompt.inputString("작성자? ");
    board.registeredDate = new Date(System.currentTimeMillis());
    board.viewCount = 0;

    this.list[this.size++] = board;

    System.out.println("게시글을 등록하였습니다.");
  }

  public void list() {
    System.out.println("[게시물 목록]");

    for (int i = 0; i < this.size; i++) {
      Board board = this.list[i];
      System.out.printf("%d, %s, %s, %s, %d\n",
          board.no,
          board.title,
          board.writer,
          board.registeredDate,
          board.viewCount);
    }
  }
}
