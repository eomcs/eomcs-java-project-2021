package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Board;

public class BoardList {
  static final int DEFAULT_CAPACITY = 100;

  Board[] boards = new Board[DEFAULT_CAPACITY];   
  int size = 0;

  void add(Board b) {
    this.boards[this.size++] = b;
  }

  Board[] toArray() {
    // 현재까지 저장된 게시글 목록을 리턴하기 위해 새 배열을 준비 한다.
    Board[] arr = new Board[this.size];
    for (int i = 0; i < this.size; i++) {
      arr[i] = this.boards[i];
    }
    return arr;
  }

  Board get(int boardNo) {
    // 해당 번호의 게시글을 찾는다. 
    int index = indexOf(boardNo);
    if (index != -1) {
      return this.boards[index];
    } 
    return null;
  }

  void delete(int boardNo) {
    // 해당 번호의 게시글을 찾는다. 
    int index = indexOf(boardNo);

    if (index == -1)
      return;

    // 배열에서 뒷 번호의 게시글을 한 칸씩 앞으로 당긴다. 
    for (int x = index + 1; x < this.size; x++) {
      this.boards[x-1] = this.boards[x];
    }
    this.boards[--this.size] = null; // 앞으로 당긴 후 맨 뒤의 항목은 null로 설정한다.
  }

  int indexOf(int boardNo) {
    for (int i = 0; i < this.size; i++) {
      Board board = this.boards[i];
      if (board.no == boardNo) {
        return i;
      }
    }
    return -1;
  }
}
