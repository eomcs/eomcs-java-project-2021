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

      // 수동 커밋으로 설정한다.
      // - pms_project 테이블과 pms_member_project 테이블에 모두 성공적으로 데이터를 저장했을 때
      //   작업을 완료한다.
      con.setAutoCommit(false); // 의미 => 트랜잭션 시작

      // 1) 프로젝트를 추가한다.
      stmt.setString(1, project.getTitle());
      stmt.setString(2, project.getContent());
      stmt.setDate(3, project.getStartDate());
      stmt.setDate(4, project.getEndDate());
      stmt.setInt(5, project.getOwner().getNo());
      int count = stmt.executeUpdate();

      // 위의 입력을 수행한 후 일부로 다음 입력을 60초 정도 지연시킨다.
      Thread.sleep(60000);

      // 프로젝트 데이터의 PK 값 알아내기
      try (ResultSet keyRs = stmt.getGeneratedKeys()) {
        keyRs.next();
        project.setNo(keyRs.getInt(1));
      }

      // 2) 프로젝트에 팀원들을 추가한다.
      for (Member member : project.getMembers()) {
        insertMember(project.getNo(), member.getNo());
      }

      // 프로젝트 정보 뿐만 아니라 팀원 정보도 정상적으로 입력되었다면,
      // 실제 테이블에 데이터를 적용한다.
      con.commit(); // 의미 : 트랜잭션 종료

      return count;

    } catch (Exception e) {
      con.rollback();

      // 이 catch 블록의 목적은 예외를 처리 하는 것이 아니라,
      // rollback을 실행하는 것이다.
      // 따라서 예외가 발생한 사실은 이전처럼 호출자에게 그대로 보고해야 한다.
      throw e;

    } finally {
      // 트랜잭션 종료 후 auto commit 을 원래 상태로 설정한다.
      con.setAutoCommit(true);
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

      con.setAutoCommit(false);

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

      con.commit();

      return count;

    } catch (Exception e) {
      con.rollback();

      // 이 catch 블록의 목적은 예외를 처리 하는 것이 아니라,
      // rollback을 실행하는 것이다.
      // 따라서 예외가 발생한 사실은 이전처럼 호출자에게 그대로 보고해야 한다.
      throw e;

    } finally {
      // 트랜잭션 종료 후 auto commit 을 원래 상태로 설정한다.
      con.setAutoCommit(true);
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "delete from pms_project where no=?")) {

      con.setAutoCommit(false);

      // 프로젝트에 소속된 팀원 정보 삭제
      deleteMembers(no);

      // 프로젝트 정보 삭제
      stmt.setInt(1, no);
      int count = stmt.executeUpdate();
      con.commit();

      return count;

    } catch (Exception e) {
      con.rollback();

      // 이 catch 블록의 목적은 예외를 처리 하는 것이 아니라,
      // rollback을 실행하는 것이다.
      // 따라서 예외가 발생한 사실은 이전처럼 호출자에게 그대로 보고해야 한다.
      throw e;

    } finally {
      // 트랜잭션 종료 후 auto commit 을 원래 상태로 설정한다.
      con.setAutoCommit(true);
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












