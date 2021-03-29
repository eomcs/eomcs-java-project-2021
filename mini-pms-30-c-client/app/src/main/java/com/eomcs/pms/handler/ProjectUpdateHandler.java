package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.pms.domain.Member;
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
                + "     mp.project_no=?");
        PreparedStatement stmt3 = con.prepareStatement(
            "update pms_project set"
                + " title=?,"
                + " content=?,"
                + " sdt=?,"
                + " edt=?,"
                + " owner=?"
                + " where no=?");
        PreparedStatement stmt4 = con.prepareStatement( 
            "delete from pms_member_project where project_no=?");
        PreparedStatement stmt5 = con.prepareStatement(
            "insert into pms_member_project(member_no,project_no) values(?,?)")) { 

      con.setAutoCommit(false);

      Project project = new Project();

      // 1) 기존 데이터 조회
      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 프로젝트가 없습니다.");
          return;
        }
        project.setNo(no); 

        // 2) 사용자에게서 변경할 데이터를 입력 받는다.
        project.setTitle(Prompt.inputString(
            String.format("프로젝트명(%s)? ", rs.getString("title"))));
        project.setContent(Prompt.inputString(
            String.format("내용(%s)? ", rs.getString("content"))));
        project.setStartDate(Prompt.inputDate(
            String.format("시작일(%s)? ", rs.getDate("sdt"))));
        project.setEndDate(Prompt.inputDate(
            String.format("종료일(%s)? ", rs.getDate("edt"))));
        project.setOwner(memberValidator.inputMember(
            String.format("만든이(%s)?(취소: 빈 문자열) ", rs.getString("owner_name"))));

        if (project.getOwner() == null) {
          System.out.println("프로젝트 변경을 취소합니다.");
          return;
        }

        // 3) 프로젝트 팀원 정보를 입력 받는다.
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
        project.setMembers(memberValidator.inputMembers(
            String.format("팀원(%s)?(완료: 빈 문자열) ", strings)));

        String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
        if (!input.equalsIgnoreCase("Y")) {
          System.out.println("프로젝트 변경을 취소하였습니다.");
          return;
        }

        // 4) DBMS에게 프로젝트 변경을 요청한다.
        stmt3.setString(1, project.getTitle());
        stmt3.setString(2, project.getContent());
        stmt3.setDate(3, project.getStartDate());
        stmt3.setDate(4, project.getEndDate());
        stmt3.setInt(5, project.getOwner().getNo());
        stmt3.setInt(6, project.getNo());
        stmt3.executeUpdate();

        // 5) 프로젝트의 기존 멤버를 삭제한다.
        stmt4.setInt(1, project.getNo());
        stmt4.executeUpdate();

        // 6) 사용자가 선택한 프로젝트 멤버들을 추가한다.
        for (Member member : project.getMembers()) {
          stmt5.setInt(1, member.getNo());
          stmt5.setInt(2, project.getNo());
          stmt5.executeUpdate();
        }

        con.commit();

        System.out.println("프로젝트을 변경하였습니다.");
      }
    }
  }
}






