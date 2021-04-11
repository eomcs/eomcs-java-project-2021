package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.List;
import com.eomcs.util.Prompt;

public class BoardHandler {

  private List boardList = new List();

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

  public void list() {
    System.out.println("[게시글 목록]");

    Object[] list = boardList.toArray();

    for (Object obj : list) {
      Board b = (Board) obj;
      // 번호, 제목, 등록일, 작성자, 조회수, 좋아요
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

    int index = indexOf(no);
    if (index == -1) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");

    if (input.equalsIgnoreCase("Y")) {
      boardList.delete(index);

      System.out.println("게시글을 삭제하였습니다.");

    } else {
      System.out.println("게시글 삭제를 취소하였습니다.");
    }

  }

  private int indexOf(int boardNo) {
    Object[] list = boardList.toArray();
    for (int i = 0; i < list.length; i++) {
      Board b = (Board) list[i];
      if (b.getNo() == boardNo) {
        return i;
      }
    }
    return -1;
  }

  private Board findByNo(int boardNo) {
    Object[] list = boardList.toArray();
    for (Object obj : list) {
      Board b = (Board) obj;
      if (b.getNo() == boardNo) {
        return b;
      }
    }
    return null;
  }

}






