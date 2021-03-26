package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.util.Prompt;

public class MemberDetailHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[회원 상세보기]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection( //
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement( //
            "select * from pms_member where no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 회원이 없습니다.");
          return;
        }

        System.out.printf("이름: %s\n", rs.getString("name"));
        System.out.printf("이메일: %s\n", rs.getString("email"));
        System.out.printf("사진: %s\n", rs.getString("photo"));
        System.out.printf("전화: %s\n", rs.getString("tel"));
        System.out.printf("가입일: %s\n", rs.getDate("cdt"));
      }
    }
  }
}






