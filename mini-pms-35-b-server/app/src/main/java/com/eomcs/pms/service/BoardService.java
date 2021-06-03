package com.eomcs.pms.service;

import java.util.List;
import com.eomcs.pms.domain.Board;

public interface BoardService {

  int add(Board board) throws Exception;

  List<Board> list(int pageNo, int pageSize) throws Exception;

  int count(String keyword) throws Exception;

  Board get(int no) throws Exception;

  int update(Board board) throws Exception;

  int delete(int no) throws Exception;

  List<Board> search(String keyword, int pageNo, int pageSize) throws Exception;
}







