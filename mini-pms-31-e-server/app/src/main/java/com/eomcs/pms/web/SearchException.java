package com.eomcs.pms.web;

// 이 예외 클래스는 검색 결과의 예외 상태를 구분할 목적으로 정의하였다.
// 
@SuppressWarnings("serial")
public class SearchException extends RuntimeException {

  public SearchException() {
    super();
  }

  public SearchException(String message) {
    super(message);
  }
}
