package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskUpdateHandler implements Command {

  MemberValidator memberValidator;

  public TaskUpdateHandler(MemberValidator memberValidator) {
    this.memberValidator = memberValidator;
  }


  @Override
  public void service() throws Exception {
    System.out.println("[작업 변경]");

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
                + "   m.name as owner_name,"
                + "   p.no as project_no,"
                + "   p.title as project_title"
                + " from pms_task t "
                + "   inner join pms_member m on t.owner=m.no"
                + "   inner join pms_project p on t.project_no=p.no"
                + " where t.no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "update pms_task set content=?,deadline=?,owner=?,status=? where no=?")) {

      Task task = new Task();

      // 1) 기존 데이터 조회
      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 작업이 없습니다.");
          return;
        }

        task.setNo(no); 
        task.setContent(rs.getString("content"));
        task.setDeadline(rs.getDate("deadline"));
        task.setStatus(rs.getInt("status"));

        Member owner = new Member();
        owner.setNo(rs.getInt("owner_no"));
        owner.setName(rs.getString("owner_name"));
        task.setOwner(owner);

        task.setProjectNo(rs.getInt("project_no"));
        task.setProjectTitle(rs.getString("project_title"));
      }

      // 2) 프로젝트 제목 출력
      System.out.printf("현재 프로젝트: %s\n", task.getProjectTitle());

      // 3) 현재 프로젝트 목록을 가져온다.
      List<Project> projects = new ArrayList<>();
      try (PreparedStatement stmt3 = con.prepareStatement(
          "select no,title from pms_project order by title asc");
          ResultSet rs = stmt3.executeQuery()) {

        while (rs.next()) {
          Project p = new Project();
          p.setNo(rs.getInt("no"));
          p.setTitle(rs.getString("title"));
          projects.add(p);
        }
      }

      // 4) 프로젝트 목록을 출력한다.
      System.out.println("프로젝트들:");
      if (projects.size() == 0) {
        System.out.println("현재 등록된 프로젝트가 없습니다!");
        return;
      }
      for (Project p : projects) {
        System.out.printf("  %d, %s\n", p.getNo(), p.getTitle());
      }

      // 5) 현재 작업이 소속된 프로젝트를 변경한다.
      int selectedProjectNo = 0;
      loop: while (true) {
        try {
          selectedProjectNo = Prompt.inputInt("변경할 프로젝트 번호?(취소: 0) ");
          if (selectedProjectNo == 0) {
            System.out.println("기존 프로젝트를 유지합니다.");
            break loop;
          }
          for (Project p : projects) {
            if (p.getNo() == selectedProjectNo) {
              break loop;
            }
          }
          System.out.println("유효하지 않은 프로젝트 번호 입니다.");

        } catch (Exception e) {
          System.out.println("숫자를 입력하세요!");
        }
      }


      // 2) 사용자에게서 변경할 데이터를 입력 받는다.
      task.setContent(Prompt.inputString(String.format("내용(%s)? ", task.getContent())));
      task.setDeadline(Prompt.inputDate(String.format("마감일(%s)? ", task.getDeadline())));
      task.setStatus(Prompt.inputInt(String.format(
          "상태(%s)?\n0: 신규\n1: 진행중\n2: 완료\n> ", 
          Task.getStatusLabel(task.getStatus()))));
      task.setOwner(memberValidator.inputMember(
          String.format("담당자(%s)?(취소: 빈 문자열) ", task.getOwner().getName())));

      if(task.getOwner() == null) {
        System.out.println("작업 변경을 취소합니다.");
        return;
      }

      String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("작업 변경을 취소하였습니다.");
        return;
      }

      // 3) DBMS에게 게시글 변경을 요청한다.
      stmt2.setString(1, task.getContent());
      stmt2.setDate(2, task.getDeadline());
      stmt2.setInt(3, task.getOwner().getNo());
      stmt2.setInt(4, task.getStatus());
      stmt2.setInt(5, task.getNo());
      stmt2.executeUpdate();

      System.out.println("작업을 변경하였습니다.");
    }
  }

}
