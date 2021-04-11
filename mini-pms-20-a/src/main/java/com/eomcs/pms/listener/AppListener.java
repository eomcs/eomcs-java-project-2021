package com.eomcs.pms.listener;

import com.eomcs.context.ApplicationContextListener;

public class AppListener implements ApplicationContextListener {

  @Override
  public void contextInitialized() {
    System.out.println("미니 프로젝트 관리시스템에 오신 걸 환영합니다!");
  }

  @Override
  public void contextDestroyed() {
    System.out.println("미니 프로젝트 관리시스템 종료!");
  }
}
