package com.eomcs.util;

// 이 인터페이스의 구현체는  
// => 개발자가 기능을 삽입하기 위해 작성한 필터 객체를 보관하고
// => 다음 체인에 대한 정보를 관리하는
// 일을 한다.
// 
public interface FilterChain {

  // 필터 체인이 보관하고 있는 필터(개발자가 추가한 기능)를 실행시킬 때 호출하는 메서드이다.
  void doFilter(CommandRequest request, CommandResponse response) throws Exception;
}
