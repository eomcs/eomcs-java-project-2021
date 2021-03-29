package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskDetailHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[작업 상세보기]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select "
                + "   t.no,"
                + "   t.content,"
                + "   t.deadline,"
                + "   t.status,"
                + "   m.no as owner_no,"
                + "   m.name as owner_name"
                + " from pms_task t "
                + "   inner join pms_member m on t.owner=m.no"
                + " where t.no=?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 작업이 없습니다.");
          return;
        }

        System.out.printf("내용: %s\n", rs.getString("content"));
        System.out.printf("마감일: %s\n", rs.getDate("deadline"));
        System.out.printf("상태: %s\n", Task.getStatusLabel(rs.getInt("status")));
        System.out.printf("담당자: %s\n", rs.getString("owner_name"));
      }
    }
  }
}
