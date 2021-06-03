package com.eomcs.pms.service.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

@Service
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
  public List<Board> list(int pageNo, int pageSize) throws Exception {
    HashMap<String,Object> params = new HashMap<>();
    params.put("offset", pageSize * (pageNo - 1));
    params.put("length", pageSize);

    return boardDao.findByKeyword(params);
  }

  @Override
  public int count(String keyword) throws Exception {
    return boardDao.countByKeyword(keyword);
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
  public List<Board> search(String keyword, int pageNo, int pageSize) throws Exception {
    HashMap<String,Object> params = new HashMap<>();
    params.put("offset", pageSize * (pageNo - 1));
    params.put("length", pageSize);
    params.put("keyword", keyword);

    return boardDao.findByKeyword(params);
  }
}







