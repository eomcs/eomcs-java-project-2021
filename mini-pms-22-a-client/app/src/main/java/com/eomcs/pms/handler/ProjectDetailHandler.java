package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.util.Prompt;

public class ProjectDetailHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 상세보기]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection( //
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement( //
            "select * from pms_project where no=?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 프로젝트가 없습니다.");
          return;
        }

        System.out.printf("프로젝트명: %s\n", rs.getString("title"));
        System.out.printf("내용: %s\n", rs.getString("content"));
        System.out.printf("시작일: %s\n", rs.getDate("sdt"));
        System.out.printf("종료일: %s\n", rs.getDate("edt"));
        System.out.printf("관리자: %s\n", rs.getString("owner"));
        System.out.printf("팀원: %s\n", rs.getString("members"));
      }
    }
  }
}








