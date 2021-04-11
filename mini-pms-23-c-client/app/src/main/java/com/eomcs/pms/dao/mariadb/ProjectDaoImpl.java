package com.eomcs.pms.dao.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;

public class ProjectDaoImpl implements ProjectDao {

  Connection con;

  // Connection 객체를 자체적으로 생성하지 않고 외부에서 주입받는다.
  // - Connection 객체를 여러 DAO가 공유할 수 있다.
  // - 교체하기도 쉽다.
  public ProjectDaoImpl(Connection con) throws Exception {
    this.con = con;
  }

  @Override
  public int insert(Project project) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into pms_project(title,content,sdt,edt,owner) values(?,?,?,?,?)",
        Statement.RETURN_GENERATED_KEYS)) {

      // 1) 프로젝트를 추가한다.
      stmt.setString(1, project.getTitle());
      stmt.setString(2, project.getContent());
      stmt.setDate(3, project.getStartDate());
      stmt.setDate(4, project.getEndDate());
      stmt.setInt(5, project.getOwner().getNo());
      int count = stmt.executeUpdate();

      // 프로젝트 데이터의 PK 값 알아내기
      try (ResultSet keyRs = stmt.getGeneratedKeys()) {
        keyRs.next();
        project.setNo(keyRs.getInt(1));
      }

      // 2) 프로젝트에 팀원들을 추가한다.
      for (Member member : project.getMembers()) {
        insertMember(project.getNo(), member.getNo());
      }

      return count;
    }
  }

  @Override
  public List<Project> findAll() throws Exception {
    ArrayList<Project> list = new ArrayList<>();

    try (PreparedStatement stmt = con.prepareStatement(
        "select"
            + "    p.no,"
            + "    p.title,"
            + "    p.sdt,"
            + "    p.edt,"
            + "    m.no as owner_no,"
            + "    m.name as owner_name"
            + "  from pms_project p"
            + "    inner join pms_member m on p.owner=m.no"
            + "  order by title asc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Project project = new Project();
        project.setNo(rs.getInt("no"));
        project.setTitle(rs.getString("title"));
        project.setStartDate(rs.getDate("sdt"));
        project.setEndDate(rs.getDate("edt"));

        Member owner = new Member();
        owner.setNo(rs.getInt("owner_no"));
        owner.setName(rs.getString("owner_name"));
        project.setOwner(owner);

        project.setMembers(findAllMembers(project.getNo()));

        list.add(project);
      }
    }
    return list;
  }

  @Override
  public Project findByNo(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
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
            + " where p.no=?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          return null;
        }

        Project project = new Project();
        project.setNo(rs.getInt("no"));
        project.setTitle(rs.getString("title"));
        project.setContent(rs.getString("content"));
        project.setStartDate(rs.getDate("sdt"));
        project.setEndDate(rs.getDate("edt"));

        Member owner = new Member();
        owner.setNo(rs.getInt("owner_no"));
        owner.setName(rs.getString("owner_name"));
        project.setOwner(owner);

        project.setMembers(findAllMembers(project.getNo()));

        return project;
      }
    }
  }

  @Override
  public int update(Project project) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "update pms_project set"
            + " title=?,"
            + " content=?,"
            + " sdt=?,"
            + " edt=?,"
            + " owner=?"
            + " where no=?")) {

      stmt.setString(1, project.getTitle());
      stmt.setString(2, project.getContent());
      stmt.setDate(3, project.getStartDate());
      stmt.setDate(4, project.getEndDate());
      stmt.setInt(5, project.getOwner().getNo());
      stmt.setInt(6, project.getNo());
      int count = stmt.executeUpdate();

      // 기존 프로젝트의 모든 멤버를 삭제한다.
      deleteMembers(project.getNo());

      // 프로젝트 멤버를 추가한다.
      for (Member member : project.getMembers()) {
        insertMember(project.getNo(), member.getNo());
      }

      return count;
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "delete from pms_project where no=?")) {

      // 프로젝트에 소속된 팀원 정보 삭제
      deleteMembers(no);

      // 프로젝트 정보 삭제
      stmt.setInt(1, no);
      int count = stmt.executeUpdate();

      return count;
    }
  }

  @Override
  public int insertMember(int projectNo, int memberNo) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into pms_member_project(member_no,project_no) values(?,?)")) {

      stmt.setInt(1, memberNo);
      stmt.setInt(2, projectNo);
      return stmt.executeUpdate();
    }
  }

  @Override
  public List<Member> findAllMembers(int projectNo) throws Exception {
    ArrayList<Member> list = new ArrayList<>();

    try (PreparedStatement stmt = con.prepareStatement(
        "select"
            + "    m.no,"
            + "    m.name"
            + " from pms_member_project mp"
            + "     inner join pms_member m on mp.member_no=m.no"
            + " where "
            + "     mp.project_no=?")) {

      stmt.setInt(1, projectNo);

      try (ResultSet memberRs = stmt.executeQuery()) {
        while (memberRs.next()) {
          Member m = new Member();
          m.setNo(memberRs.getInt("no"));
          m.setName(memberRs.getString("name"));
          list.add(m);
        }
      }
    }

    return list;
  }

  @Override
  public int deleteMembers(int projectNo) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "delete from pms_member_project where project_no=?")) {
      stmt.setInt(1, projectNo);
      return stmt.executeUpdate();
    }
  }
}












