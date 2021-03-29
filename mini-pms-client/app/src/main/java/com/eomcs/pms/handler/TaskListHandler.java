package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskListHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[작업 목록]");

    String input = Prompt.inputString("프로젝트 번호? ");

    // 1) 사용자가 입력한 문자열을 프로젝트 번호로 바꾼다.
    int projectNo = 0;
    try {
      projectNo = Integer.parseInt(input);
    }catch (Exception e) {
      System.out.println("프로젝트 번호를 입력하세요.");
      return;
    }

    // 2) 프로젝트 정보를 가져온다.
    String projectTitle = null;
    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select no,title from pms_project where no=?")) {   

      stmt.setInt(1, projectNo);
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 프로젝트가 존재하지 않습니다.");
          return;
        }
        projectTitle = rs.getString("title");
      }

      // 3) 해당 프로젝트에 소속된 작업 목록을 가져온다.
      try (
          PreparedStatement stmt2 = con.prepareStatement(
              "select "
                  + "   t.no,"
                  + "   t.content,"
                  + "   t.deadline,"
                  + "   t.status,"
                  + "   m.no as owner_no,"
                  + "   m.name as owner_name"
                  + " from pms_task t "
                  + "   inner join pms_member m on t.owner=m.no"
                  + " where "
                  + "   t.project_no=?"
                  + " order by t.content asc")) {

        stmt2.setInt(1, projectNo);

        try (ResultSet rs = stmt2.executeQuery()) {
          System.out.printf("'%s' 작업 목록: \n", projectTitle);
          while (rs.next()) {
            System.out.printf("%d, %s, %s, %s, %s\n", 
                rs.getInt("no"), 
                rs.getString("content"), 
                rs.getDate("deadline"),
                rs.getString("owner_name"),
                Task.getStatusLabel(rs.getInt("status")));
          }
        }
      }
    }
  }
}
