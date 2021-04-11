package com.eomcs.pms.service.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

public class DefaultBoardService implements BoardService {

  SqlSession sqlSession;

  BoardDao boardDao; 

  public DefaultBoardService(SqlSession sqlSession, BoardDao boardDao) {
    this.sqlSession = sqlSession;
    this.boardDao = boardDao;
  }

  // 게시글 등록 업무
  @Override
  public int add(Board board) throws Exception {
    int count = boardDao.insert(board);
    sqlSession.commit();
    return count;
  }

  // 게시글 목록 조회 업무
  @Override
  public List<Board> list() throws Exception {
    return boardDao.findByKeyword(null);
  }

  // 게시글 상세 조회 업무
  @Override
  public Board get(int no) throws Exception {
    Board board = boardDao.findByNo(no);
    if (board != null) {
      boardDao.updateViewCount(no);
      sqlSession.commit();
    }
    return board; 
  }

  // 게시글 변경 업무
  @Override
  public int update(Board board) throws Exception {
    int count = boardDao.update(board);
    sqlSession.commit();
    return count;
  }

  // 게시글 삭제 업무
  @Override
  public int delete(int no) throws Exception {
    int count = boardDao.delete(no);
    sqlSession.commit();
    return count;
  }

  // 게시글 검색 업무
  @Override
  public List<Board> search(String keyword) throws Exception {
    return boardDao.findByKeyword(keyword);
  }
}







