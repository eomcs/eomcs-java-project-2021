package com.eomcs.pms.service.impl;

import java.util.List;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

public class DefaultBoardService implements BoardService {

  BoardDao boardDao; 

  public DefaultBoardService(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  // 게시글 등록 업무
  @Override
  public int add(Board board) throws Exception {
    return boardDao.insert(board);
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
    }
    return board; 
  }

  // 게시글 변경 업무
  @Override
  public int update(Board board) throws Exception {
    return boardDao.update(board);
  }

  // 게시글 삭제 업무
  @Override
  public int delete(int no) throws Exception {
    return boardDao.delete(no);
  }

  // 게시글 검색 업무
  @Override
  public List<Board> search(String keyword) throws Exception {
    return boardDao.findByKeyword(keyword);
  }
}







