package com.eomcs.context;

public interface ApplicationContextListener {
  // 애플리케이션이 시작될 때 호출하는 메서드
  void contextInitialized();

  // 애플리케이션이 종료될 때 호출하는 메서드
  void contextDestroyed();
}
