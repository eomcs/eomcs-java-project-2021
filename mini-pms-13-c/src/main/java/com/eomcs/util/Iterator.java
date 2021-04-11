package com.eomcs.util;

public interface Iterator {
  // 인터페이스의 메서드,
  // - 호출 규칙이기 때문에 무조건 public 으로 공개된다.
  // - 기본이 추상 메서드이다.
  // - 그래서 public과 abstract는 생략 가능하다.
  boolean hasNext();
  Object next();
}
