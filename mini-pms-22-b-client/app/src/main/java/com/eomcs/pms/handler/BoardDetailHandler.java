package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.util.Prompt;

public class BoardDetailHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 상세보기]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection( //
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement( //
            "select * from pms_board where no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 게시글이 없습니다.");
          return;
        }

        System.out.printf("제목: %s\n", rs.getString("title"));
        System.out.printf("내용: %s\n", rs.getString("content"));
        System.out.printf("작성자: %s\n", rs.getString("writer"));
        System.out.printf("등록일: %s %s\n", rs.getDate("cdt"), rs.getTime("cdt"));
        System.out.printf("조회수: %s\n", rs.getString("vw_cnt"));
        System.out.printf("좋아요: %s\n", rs.getString("like_cnt"));
      }
    }

  }
}






