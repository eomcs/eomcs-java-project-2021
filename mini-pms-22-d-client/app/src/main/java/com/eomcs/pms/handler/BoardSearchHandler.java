package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.util.Prompt;

public class BoardSearchHandler implements Command {

  @Override
  public void service() throws Exception {
    String keyword = Prompt.inputString("검색어? ");

    if (keyword.length() == 0) {
      System.out.println("검색어를 입력하세요.");
      return;
    }

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
                + " where b.title like concat('%',?,'%')"
                + " or b.content like concat('%',?,'%')"
                + " or m.name like concat('%',?,'%')"
                + " order by b.no desc")) {

      stmt.setString(1, keyword);
      stmt.setString(2, keyword);
      stmt.setString(3, keyword);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("검색어에 해당하는 게시글이 없습니다.");
          return;
        }

        do {
          System.out.printf("%d, %s, %s, %s, %d\n", 
              rs.getInt("no"), 
              rs.getString("title"), 
              rs.getString("writer_name"),
              rs.getDate("cdt"),
              rs.getInt("vw_cnt"));
        } while (rs.next());
      }
    }
  }
}






