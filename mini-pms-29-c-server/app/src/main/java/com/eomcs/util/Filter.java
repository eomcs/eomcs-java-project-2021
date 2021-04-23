package com.eomcs.util;

// 개발자가 삽입한 기능을 실행할 때 
// 호출하는 메서드의 규칙을 정의한다.
// => 호출자는 FilterChain 구현체다.
// => 즉 필터 체인이 호출하는 메서드의 규칙을 정의한다.
// => 개발자는 이 규칙에 따라 삽입할 기능을 만든다.
//
public interface Filter {

  // FilterChain 객체가 개발자가 삽입한 기능을 실행하기 위해 이 메서드를 호출한다.
  void doFilter(CommandRequest request, CommandResponse response, FilterChain nextChain) throws Exception;
}
