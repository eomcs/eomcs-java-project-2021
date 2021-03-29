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

    String input = Prompt.inputString("프로젝트 번호?(전체: 빈 문자열 또는 0) ");

    // 1) 사용자가 입력한 문자열을 프로젝트 번호로 바꾼다.
    int projectNo = 0;
    try {
      if (input.length() != 0) {
        projectNo = Integer.parseInt(input);
      }
    }catch (Exception e) {
      System.out.println("프로젝트 번호를 입력하세요.");
      return;
    }

    // 2) 해당 프로젝트에 소속된 작업 목록을 가져온다.
    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt2 = con.prepareStatement(
            "select "
                + "   t.no,"
                + "   t.content,"
                + "   t.deadline,"
                + "   t.status,"
                + "   m.no as owner_no,"
                + "   m.name as owner_name,"
                + "   p.no as project_no,"
                + "   p.title as project_title"
                + " from pms_task t "
                + "   inner join pms_member m on t.owner=m.no"
                + "   inner join pms_project p on t.project_no=p.no"
                + " where "
                + "   t.project_no=? or 0=?"
                + " order by p.no desc, t.content asc")) {

      stmt2.setInt(1, projectNo);
      stmt2.setInt(2, projectNo);

      try (ResultSet rs = stmt2.executeQuery()) {
        int count = 0;
        while (rs.next()) {
          if (projectNo != rs.getInt("project_no")) {
            System.out.printf("'%s' 작업 목록: \n", rs.getString("project_title"));
            projectNo = rs.getInt("project_no");
          }
          System.out.printf("%d, %s, %s, %s, %s\n", 
              rs.getInt("no"), 
              rs.getString("content"), 
              rs.getDate("deadline"),
              rs.getString("owner_name"),
              Task.getStatusLabel(rs.getInt("status")));
          count++;
        }
        if (count == 0) {
          System.out.println("해당 번호의 프로젝트가 없거나 또는 등록된 작업이 없습니다.");
        }
      }
    }
  }
}
