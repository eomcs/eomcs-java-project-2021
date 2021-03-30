package com.eomcs.pms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Task;

// 한 번에 4번째 단계까지 가지말고 일단 3번째와 4번째 단계 사이에 있는 정도로 구현을 해보자.
// - 각 DAO 클래스는 Connection 객체를 공유하기 위해 인스턴스 필드로 선언한다.
// - 각 DAO 클래스는 DAO 인스턴스가 생성될 때 Connection 객체를 만든다.
public class TaskDao {

  Connection con;

  public TaskDao() throws Exception {
    this.con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
  }

  // 이제 메서드들은 인스턴스 필드에 들어있는 Connection 객체를 사용해야 하기 때문에
  // 스태틱 메서드가 아닌 인스턴스 메서드로 선언해야 한다.
  public int insert(Task task) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into pms_task(content,deadline,owner,status,project_no) values(?,?,?,?,?)");) {

      stmt.setString(1, task.getContent());
      stmt.setDate(2, task.getDeadline());
      stmt.setInt(3, task.getOwner().getNo());
      stmt.setInt(4, task.getStatus());
      stmt.setInt(5, task.getProjectNo());
      return stmt.executeUpdate();
    }
  }

  public List<Task> findAll() throws Exception {
    ArrayList<Task> list = new ArrayList<>();

    try (PreparedStatement stmt = con.prepareStatement(
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
            + " order by p.no desc, t.content asc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Task task = new Task();
        task.setNo(rs.getInt("no"));
        task.setContent(rs.getString("content"));
        task.setDeadline(rs.getDate("deadline"));

        Member owner = new Member();
        owner.setNo(rs.getInt("owner_no"));
        owner.setName(rs.getString("owner_name"));
        task.setOwner(owner);

        task.setStatus(rs.getInt("status"));

        task.setProjectNo(rs.getInt("project_no"));
        task.setProjectTitle(rs.getString("project_title"));

        list.add(task);
      }
      return list;
    }
  }

  public List<Task> findByProjectNo(int projectNo) throws Exception {
    ArrayList<Task> list = new ArrayList<>();

    try (PreparedStatement stmt = con.prepareStatement(
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
            + " where t.project_no=?"
            + " order by p.no desc, t.content asc")) {

      stmt.setInt(1, projectNo);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Task task = new Task();
        task.setNo(rs.getInt("no"));
        task.setContent(rs.getString("content"));
        task.setDeadline(rs.getDate("deadline"));

        Member owner = new Member();
        owner.setNo(rs.getInt("owner_no"));
        owner.setName(rs.getString("owner_name"));
        task.setOwner(owner);

        task.setStatus(rs.getInt("status"));

        task.setProjectNo(rs.getInt("project_no"));
        task.setProjectTitle(rs.getString("project_title"));

        list.add(task);
      }
      return list;
    }
  }

  /*
  public Task findByNo(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "select"
            + "    p.no,"
            + "    p.title,"
            + "    p.content,"
            + "    p.sdt,"
            + "    p.edt,"
            + "    m.no as owner_no,"
            + "    m.name as owner_name"
            + "  from pms_task p"
            + "    inner join pms_member m on p.owner=m.no"
            + " where p.no=?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          return null;
        }

        Task task = new Task();
        task.setNo(rs.getInt("no"));
        task.setTitle(rs.getString("title"));
        task.setContent(rs.getString("content"));
        task.setStartDate(rs.getDate("sdt"));
        task.setEndDate(rs.getDate("edt"));

        Member owner = new Member();
        owner.setNo(rs.getInt("owner_no"));
        owner.setName(rs.getString("owner_name"));
        task.setOwner(owner);

        task.setMembers(findAllMembers(task.getNo()));

        return task;
      }
    }
  }

  public int update(Task task) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "update pms_task set"
            + " title=?,"
            + " content=?,"
            + " sdt=?,"
            + " edt=?,"
            + " owner=?"
            + " where no=?")) { 

      con.setAutoCommit(false);

      stmt.setString(1, task.getTitle());
      stmt.setString(2, task.getContent());
      stmt.setDate(3, task.getStartDate());
      stmt.setDate(4, task.getEndDate());
      stmt.setInt(5, task.getOwner().getNo());
      stmt.setInt(6, task.getNo());
      int count = stmt.executeUpdate();

      // 기존 프로젝트의 모든 멤버를 삭제한다.
      deleteMembers(task.getNo());

      // 프로젝트 멤버를 추가한다.
      for (Member member : task.getMembers()) {
        insertMember(task.getNo(), member.getNo());
      }

      con.commit();

      return count;

    } finally {
      // 트랜잭션 종료 후 auto commit 을 원래 상태로 설정한다.
      con.setAutoCommit(true);
    }
  }

  public int delete(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "delete from pms_task where no=?")) {

      con.setAutoCommit(false);

      // 프로젝트에 소속된 팀원 정보 삭제
      deleteMembers(no);

      // 프로젝트 정보 삭제
      stmt.setInt(1, no);
      int count = stmt.executeUpdate();
      con.commit();

      return count;

    } finally {
      // 트랜잭션 종료 후 auto commit 을 원래 상태로 설정한다.
      con.setAutoCommit(true);
    }
  }
   */
}












