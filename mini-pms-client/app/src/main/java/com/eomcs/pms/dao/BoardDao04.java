package com.eomcs.pms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;

//1) 메서드를 호출 할 때 마다 Connection 객체 생성
//- 즉 DBMS에 연결
//2) 클래스가 로딩될 때 미리 Connection 객체 생성
//- DAO 당 한 번만 DBMS에 연결
//3) 여러 DAO가 Connection 객체를 공유할 수 있도록 외부에서 생성한 후 주입한다.
//4) DAO에 대해 각 인스턴스 마다 Connection 객체를 구분해서 사용할 수 있도록 
//   Connection 필드를 인스턴스 멤버로 선언한다.
public class BoardDao04 {

  // 이제 Connection 객체는 BoardDao 마다 다를 수 있다.
  Connection con;

  // 이렇게 생성자에서 Connection 객체를 파라미터로 요구하면
  // Connection 객체를 필수 항목이 된다.
  // 스태틱 필드로는 필수항목/선택항목을 제어할 수 었다.
  public BoardDao04(Connection con) {
    this.con = con;
  }

  // 이제 메서드들은 인스턴스 필드에 들어있는 Connection 객체를 사용해야 하기 때문에
  // 스태틱 메서드가 아닌 인스턴스 메서드로 선언해야 한다.
  public int insert(Board board) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into pms_board(title, content, writer) values(?,?,?)");) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getWriter().getNo());

      return stmt.executeUpdate();
    } 
  }

  public List<Board> findAll() throws Exception {
    ArrayList<Board> list = new ArrayList<>();

    try (PreparedStatement stmt = con.prepareStatement(
        "select"
            + " b.no,"
            + " b.title,"
            + " b.cdt,"
            + " b.vw_cnt,"
            + " b.like_cnt,"
            + " m.no as writer_no,"
            + " m.name as writer_name"
            + " from pms_board b"
            + "   inner join pms_member m on m.no=b.writer"
            + " order by b.no desc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Board board = new Board();
        board.setNo(rs.getInt("no"));
        board.setTitle(rs.getString("title"));
        board.setRegisteredDate(rs.getDate("cdt"));
        board.setViewCount(rs.getInt("vw_cnt"));

        Member writer = new Member();
        writer.setNo(rs.getInt("writer_no"));
        writer.setName(rs.getString("writer_name"));
        board.setWriter(writer);

        list.add(board);
      }
    }

    return list;
  }

  public Board findByNo(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "select"
            + " b.no,"
            + " b.title,"
            + " b.content,"
            + " b.cdt,"
            + " b.vw_cnt,"
            + " b.like_cnt,"
            + " m.no as writer_no,"
            + " m.name as writer_name"
            + " from pms_board b"
            + "   inner join pms_member m on m.no=b.writer"
            + " where b.no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          return null;
        }

        Board board = new Board();
        board.setNo(rs.getInt("no"));
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("content"));
        board.setRegisteredDate(new Date(rs.getTimestamp("cdt").getTime()));
        board.setViewCount(rs.getInt("vw_cnt"));
        board.setLike(rs.getInt("like_cnt"));

        Member writer = new Member();
        writer.setNo(rs.getInt("writer_no"));
        writer.setName(rs.getString("writer_name"));
        board.setWriter(writer);

        return board;
      }
    }
  }

  public int update(Board board) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "update pms_board set title=?, content=? where no=?")) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getNo());
      return stmt.executeUpdate();
    }
  }

  public int updateViewCount(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "update pms_board set vw_cnt=vw_cnt + 1 where no=?")) {
      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }

  public int delete(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "delete from pms_board where no=?")) {
      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }

  public List<Board> findByKeyword(String keyword) throws Exception {
    ArrayList<Board> list = new ArrayList<>();

    try (PreparedStatement stmt = con.prepareStatement(
        "select"
            + " b.no,"
            + " b.title,"
            + " b.cdt,"
            + " b.vw_cnt,"
            + " b.like_cnt,"
            + " m.no as writer_no,"
            + " m.name as writer_name"
            + " from pms_board b"
            + "   inner join pms_member m on m.no=b.writer"
            + " where b.title like concat('%',?,'%')"
            + "   or b.content like concat('%',?,'%')"
            + "   or m.name like concat('%',?,'%')"
            + " order by b.no desc")) {

      stmt.setString(1, keyword);
      stmt.setString(2, keyword);
      stmt.setString(3, keyword);

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Board board = new Board();
        board.setNo(rs.getInt("no"));
        board.setTitle(rs.getString("title"));
        board.setRegisteredDate(rs.getDate("cdt"));
        board.setViewCount(rs.getInt("vw_cnt"));

        Member writer = new Member();
        writer.setNo(rs.getInt("writer_no"));
        writer.setName(rs.getString("writer_name"));
        board.setWriter(writer);

        list.add(board);
      }
    }

    return list;
  }

}












