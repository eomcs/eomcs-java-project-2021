package com.eomcs.pms.handler;

import com.eomcs.util.Prompt;

public class BoardHandler_a {

  static class Board {
    int no;
    String title;
    String content;
    String writer;
  }

  static final int LENGTH = 100;
  static Board[] boards = new Board[LENGTH];   
  static int size = 0;

  public static void add() {
    System.out.println("[게시글 등록]");

    Board b = new Board();

    b.no = Prompt.inputInt("번호? ");
    b.title = Prompt.inputString("제목? ");
    b.content = Prompt.inputString("내용? ");
    b.writer = Prompt.inputString("작성자? ");

    boards[size++] = b;

    System.out.println("게시글을 등록하였습니다.");
  }
}






