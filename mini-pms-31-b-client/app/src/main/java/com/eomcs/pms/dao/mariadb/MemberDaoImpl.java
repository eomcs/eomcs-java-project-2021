package com.eomcs.pms.dao.mariadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.domain.Member;

public class MemberDaoImpl implements MemberDao {

  Connection con;

  public MemberDaoImpl() throws Exception {
    this.con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
  }

  @Override
  public int insert(Member member) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into pms_member(name,email,password,photo,tel) values(?,?,password(?),?,?)");) {

      stmt.setString(1, member.getName());
      stmt.setString(2, member.getEmail());
      stmt.setString(3, member.getPassword());
      stmt.setString(4, member.getPhoto());
      stmt.setString(5, member.getTel());
      return stmt.executeUpdate();
    }
  }

  @Override
  public List<Member> findAll() throws Exception {
    ArrayList<Member> list = new ArrayList<>();

    try (PreparedStatement stmt = con.prepareStatement(
        "select no,name,email,photo,tel from pms_member order by name asc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Member m = new Member();
        m.setNo(rs.getInt("no"));
        m.setName(rs.getString("name"));
        m.setEmail(rs.getString("email"));
        m.setPhoto(rs.getString("photo"));
        m.setTel(rs.getString("tel"));

        list.add(m);
      }
    }

    return list;
  }

  @Override
  public Member findByNo(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "select * from pms_member where no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          return null;
        }

        Member m = new Member();
        m.setNo(no);
        m.setName(rs.getString("name"));
        m.setEmail(rs.getString("email"));
        m.setPhoto(rs.getString("photo"));
        m.setTel(rs.getString("tel"));
        m.setRegisteredDate(rs.getDate("cdt"));

        return m;
      }
    }
  }

  @Override
  public int update(Member member) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "update pms_member set name=?,email=?,password=password(?),photo=?,tel=? where no=?")) {
      stmt.setString(1, member.getName());
      stmt.setString(2, member.getEmail());
      stmt.setString(3, member.getPassword());
      stmt.setString(4, member.getPhoto());
      stmt.setString(5, member.getTel());
      stmt.setInt(6, member.getNo());
      return stmt.executeUpdate();
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "delete from pms_member where no=?")) {
      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }

  @Override
  public Member findByName(String name) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "select * from pms_member where name=?")) {

      stmt.setString(1, name);

      ResultSet rs = stmt.executeQuery();

      if (!rs.next()) {
        return null;
      }

      Member m = new Member();
      m.setNo(rs.getInt("no"));
      m.setName(rs.getString("name"));
      m.setEmail(rs.getString("email"));
      m.setPhoto(rs.getString("photo"));
      m.setTel(rs.getString("tel"));
      m.setRegisteredDate(rs.getDate("cdt"));

      return m;
    }
  }
}












