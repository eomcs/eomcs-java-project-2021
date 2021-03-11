package com.eomcs.pms.handler;

import com.eomcs.driver.Statement;
import com.eomcs.util.Prompt;

public class BoardUpdateHandler implements Command {

  @Override
  public void service(Statement stmt) throws Exception {
    System.out.println("[게시글 변경]");

    int no = Prompt.inputInt("번호? ");

    String[] fields = stmt.executeQuery("board/select", Integer.toString(no)).next().split(",");

    String title = Prompt.inputString(String.format("제목(%s)? ", fields[1]));
    String content = Prompt.inputString(String.format("내용(%s)? ", fields[2]));

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("게시글 변경을 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("board/update", String.format("%d,%s,%s", no, title, content));

    System.out.println("게시글을 변경하였습니다.");
  }
}






