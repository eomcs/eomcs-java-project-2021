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

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select"
                + "    p.no,"
                + "    p.title,"
                + "    p.content,"
                + "    p.sdt,"
                + "    p.edt,"
                + "    m.no as owner_no,"
                + "    m.name as owner_name"
                + "  from pms_project p"
                + "    inner join pms_member m on p.owner=m.no"
                + " where p.no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "select" 
                + "    m.no,"
                + "    m.name"
                + " from pms_member_project mp"
                + "     inner join pms_member m on mp.member_no=m.no"
                + " where "
                + "     mp.project_no=?")) {

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
        System.out.printf("관리자: %s\n", rs.getString("owner_name"));

        StringBuilder strings = new StringBuilder();

        stmt2.setInt(1, no);
        try (ResultSet membersRs = stmt2.executeQuery()) {
          while (membersRs.next()) {
            if (strings.length() > 0) {
              strings.append(",");
            }
            strings.append(membersRs.getString("name"));
          }
        }
        System.out.printf("팀원: %s\n", strings);
      }
    }
  }
}








