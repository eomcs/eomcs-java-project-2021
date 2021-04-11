package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BoardListHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 목록]");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select"
                + " b.no,"
                + " b.title,"
                + " b.cdt,"
                + " b.vw_cnt,"
                + " b.like_cnt,"
                + " m.name as writer_name"
                + " from pms_board b"
                + "   inner join pms_member m on m.no=b.writer"
                + " order by b.no desc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        System.out.printf("%d, %s, %s, %s, %d\n", 
            rs.getInt("no"), 
            rs.getString("title"), 
            rs.getString("writer_name"),
            rs.getDate("cdt"),
            rs.getInt("vw_cnt"));
      }
    }

  }
}






