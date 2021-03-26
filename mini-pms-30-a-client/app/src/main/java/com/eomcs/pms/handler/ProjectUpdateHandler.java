package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectUpdateHandler implements Command {

  MemberValidator memberValidator;

  public ProjectUpdateHandler(MemberValidator memberValidator) {
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 변경]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select * from pms_project where no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "update pms_project set title=?,content=?,sdt=?,edt=?,owner=?,members=? where no=?")) {

      Project project = new Project();

      // 1) 기존 데이터 조회
      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 프로젝트가 없습니다.");
          return;
        }

        project.setNo(no); 
        project.setTitle(rs.getString("title"));
        project.setContent(rs.getString("content"));
        project.setStartDate(rs.getDate("sdt"));
        project.setEndDate(rs.getDate("edt"));
        project.setOwner(rs.getString("owner"));
        project.setMembers(rs.getString("members"));
      }

      // 2) 사용자에게서 변경할 데이터를 입력 받는다.
      project.setTitle(Prompt.inputString(String.format("프로젝트명(%s)? ", project.getTitle())));
      project.setContent(Prompt.inputString(String.format("내용(%s)? ", project.getContent())));
      project.setStartDate(Prompt.inputDate(String.format("시작일(%s)? ", project.getStartDate())));
      project.setEndDate(Prompt.inputDate(String.format("종료일(%s)? ", project.getEndDate())));

      project.setOwner(memberValidator.inputMember(
          String.format("만든이(%s)?(취소: 빈 문자열) ", project.getOwner())));
      if (project.getOwner() == null) {
        System.out.println("프로젝트 변경을 취소합니다.");
        return;
      }

      project.setMembers(memberValidator.inputMembers(
          String.format("팀원(%s)?(완료: 빈 문자열) ", project.getMembers())));

      String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("프로젝트 변경을 취소하였습니다.");
        return;
      }

      // 3) DBMS에게 게시글 변경을 요청한다.
      stmt2.setString(1, project.getTitle());
      stmt2.setString(2, project.getContent());
      stmt2.setDate(3, project.getStartDate());
      stmt2.setDate(4, project.getEndDate());
      stmt2.setString(5, project.getOwner());
      stmt2.setString(6, project.getMembers());
      stmt2.setInt(7, project.getNo());
      stmt2.executeUpdate();

      System.out.println("프로젝트을 변경하였습니다.");
    }
  }

}








