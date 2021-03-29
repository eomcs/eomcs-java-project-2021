package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class BoardAddHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 등록]");

    Board b = new Board();

    b.setTitle(Prompt.inputString("제목? "));
    b.setContent(Prompt.inputString("내용? "));

    Member writer = new Member();
    writer.setNo(Prompt.inputInt("작성자 번호? "));
    b.setWriter(writer);

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt =
            con.prepareStatement("insert into pms_board(title, content, writer) values(?,?,?)");) {

      stmt.setString(1, b.getTitle());
      stmt.setString(2, b.getContent());
      stmt.setInt(3, b.getWriter().getNo());

      stmt.executeUpdate();

      System.out.println("게시글을 등록하였습니다.");
    }
  }
}






