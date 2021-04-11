package com.eomcs.pms.handler;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardHandler {

  private ArrayList<Board> boardList = new ArrayList<>();

  public void add() {
    System.out.println("[게시글 등록]");

    Board b = new Board();

    b.setNo(Prompt.inputInt("번호? "));
    b.setTitle(Prompt.inputString("제목? "));
    b.setContent(Prompt.inputString("내용? "));
    b.setWriter(Prompt.inputString("작성자? "));
    b.setRegisteredDate(new Date(System.currentTimeMillis()));

    boardList.add(b);

    System.out.println("게시글을 등록하였습니다.");
  }

  public void list() throws CloneNotSupportedException {
    System.out.println("[게시글 목록]");

    // 방법1) 
    //    Board[] arr = new Board[boardList.size()];
    //    boardList.toArray(arr);

    // 방법2)
    //    Board[] arr = boardList.toArray(new Board[boardList.size()]);
    //
    //    for (Board b : arr) {
    //      System.out.printf("%d, %s, %s, %s, %d, %d\n", 
    //          b.getNo(), 
    //          b.getTitle(), 
    //          b.getRegisteredDate(), 
    //          b.getWriter(), 
    //          b.getViewCount(),
    //          b.getLike());
    //    }

    // Iterator 사용하여 데이터 조회하기
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

  public void detail() {
    System.out.println("[게시글 상세보기]");

    int no = Prompt.inputInt("번호? ");

    Board board = findByNo(no);
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    board.setViewCount(board.getViewCount() + 1);

    System.out.printf("제목: %s\n", board.getTitle());
    System.out.printf("내용: %s\n", board.getContent());
    System.out.printf("작성자: %s\n", board.getWriter());
    System.out.printf("등록일: %s\n", board.getRegisteredDate());
    System.out.printf("조회수: %d\n", board.getViewCount());

  }

  public void update() {
    System.out.println("[게시글 변경]");

    int no = Prompt.inputInt("번호? ");

    Board board = findByNo(no);
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    String title = Prompt.inputString(String.format("제목(%s)? ", board.getTitle()));
    String content = Prompt.inputString(String.format("내용(%s)? ", board.getContent()));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      board.setTitle(title);
      board.setContent(content);
      System.out.println("게시글을 변경하였습니다.");

    } else {
      System.out.println("게시글 변경을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[게시글 삭제]");

    int no = Prompt.inputInt("번호? ");

    Board board = findByNo(no);
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      boardList.remove(board); 

      System.out.println("게시글을 삭제하였습니다.");

    } else {
      System.out.println("게시글 삭제를 취소하였습니다.");
    }

  }

  private Board findByNo(int boardNo) {
    Board[] list = boardList.toArray(new Board[0]);
    for (Board b : list) {
      if (b.getNo() == boardNo) {
        return b;
      }
    }
    return null;
  }

}






