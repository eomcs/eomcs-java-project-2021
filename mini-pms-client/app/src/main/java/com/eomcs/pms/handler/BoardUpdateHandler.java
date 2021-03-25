package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardUpdateHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 변경]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select no,title,content from pms_board where no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "update pms_board set title=?, content=? where no=?")) {

      Board board = new Board();

      // 1) 기존 데이터 조회
      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 게시글이 없습니다.");
          return;
        }

        board.setNo(no); 
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("content"));
      }

      // 2) 사용자에게서 변경할 데이터를 입력 받는다.
      board.setTitle(Prompt.inputString(String.format("제목(%s)? ", board.getTitle())));
      board.setContent(Prompt.inputString(String.format("내용(%s)? ", board.getContent())));

      String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("게시글 변경을 취소하였습니다.");
        return;
      }

      // 3) DBMS에게 게시글 변경을 요청한다.
      stmt2.setString(1, board.getTitle());
      stmt2.setString(2, board.getContent());
      stmt2.setInt(3, board.getNo());
      stmt2.executeUpdate();

      System.out.println("게시글을 변경하였습니다.");
    }
  }
}






