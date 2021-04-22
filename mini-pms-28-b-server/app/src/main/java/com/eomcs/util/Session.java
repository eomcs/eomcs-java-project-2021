package com.eomcs.util;

import java.util.HashMap;
import java.util.Map;

// 클라이언트가 접속해 있는 동안 사용하는 객체다.
// 핸들러(Command 구현체)가 같은 클라이언트의 요청을 처리하는 동안 
// 객체를 보관하는 용도로 사용할 것이다.   
public class Session {

  private Map<String,Object> map = new HashMap<>();

  // 보관소에 값 저장
  public void setAttribute(String name, Object value) {
    map.put(name, value);
  }

  // 보관소에 저장된 값 꺼내기
  public Object getAttribute(String name) {
    return map.get(name);
  }

  // 보관소를 초기화시키기
  public void invalidate() {
    map.clear();
  }
}
