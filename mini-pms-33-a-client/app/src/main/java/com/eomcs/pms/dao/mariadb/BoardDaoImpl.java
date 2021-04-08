package com.eomcs.pms.dao.mariadb;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.domain.Board;

public class BoardDaoImpl implements BoardDao {

  // auto commit 객체 받기
  SqlSession sqlSession;

  public BoardDaoImpl(SqlSession sqlSession) throws Exception {
    this.sqlSession = sqlSession;
  }

  @Override
  public int insert(Board board) throws Exception {
    int count = sqlSession.insert("BoardMapper.insert", board);
    sqlSession.commit();
    return count;
  }

  @Override
  public Board findByNo(int no) throws Exception {
    return sqlSession.selectOne("BoardMapper.findByNo", no);
  }

  @Override
  public int update(Board board) throws Exception {
    int count = sqlSession.update("BoardMapper.update", board);
    sqlSession.commit();
    return count;
  }

  @Override
  public int updateViewCount(int no) throws Exception {
    int count = sqlSession.update("BoardMapper.updateViewCount", no);
    sqlSession.commit();
    return count;
  }

  @Override
  public int delete(int no) throws Exception {
    int count = sqlSession.delete("BoardMapper.delete", no);
    sqlSession.commit();
    return count;
  }

  @Override
  public List<Board> findByKeyword(String keyword) throws Exception {
    return sqlSession.selectList("BoardMapper.findByKeyword", keyword);
  }

}












