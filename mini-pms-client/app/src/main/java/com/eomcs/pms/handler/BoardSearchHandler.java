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
            "select no,title,writer,cdt,vw_cnt"
                + " from pms_board"
                + " where title like concat('%',?,'%')"
                + " or content like concat('%',?,'%')"
                + " or writer like concat('%',?,'%')"
                + " order by no desc")) {

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
              rs.getString("writer"),
              rs.getDate("cdt"),
              rs.getInt("vw_cnt"));
        } while (rs.next());
      }
    }
  }
}






