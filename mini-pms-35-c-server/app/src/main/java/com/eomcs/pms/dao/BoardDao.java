package com.eomcs.pms.dao;

import java.util.List;
import java.util.Map;
import com.eomcs.pms.domain.Board;

// BoardDao 의 규칙 정의
public interface BoardDao {

  int insert(Board board) throws Exception;

  List<Board> findByKeyword(Map<String,Object> params) throws Exception;

  int countByKeyword(String keyword) throws Exception;

  Board findByNo(int no) throws Exception;

  int update(Board board) throws Exception;

  int updateViewCount(int no) throws Exception;

  int delete(int no) throws Exception;
}












